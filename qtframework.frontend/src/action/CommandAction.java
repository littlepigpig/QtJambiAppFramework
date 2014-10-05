package action;

import com.trolltech.qt.core.QObject;
import com.trolltech.qt.gui.QAction;
import com.trolltech.qt.gui.QCursor;
import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QToolTip;
import command.Command;

public class CommandAction extends QAction
{
	private Command command;
	private String tooltip;
	private final String description;

	public CommandAction(final QObject parent) {
		this(null, parent);
	}

	public CommandAction(final String description, final QObject parent) {
		this(null, description, parent);
	}

	public CommandAction(final QIcon icon, final String description, final QObject parent)
	{
		super(icon, description, parent);

		this.description = description;

		triggered.connect(this, "triggered(boolean)");
		hovered.connect(this, "displayTooltip()");

		parent.installEventFilter(new MouseMoveEventFilter());
	}

	/**
	 * execute the command
	 *
	 * @param checked
	 */
	public final void triggered(final boolean checked) {
		command.invoke();
	}

	public void displayTooltip() {
		QToolTip.showText(QCursor.pos(), tooltip);
	}

	/**
	 *
	 */
	public final void stateChanged()
	{
		if(command != null && command.isExecutionPossible()) {
			setEnabled(true);
			tooltip = description;
		}
		else {
			setEnabled(false);
			tooltip = command.getLastBusinessCheckReason();
		}
	}

	public final void setCommand(final Command command)
	{
		this.command = command;
		command.stateChanged.connect(this, "stateChanged()");

		stateChanged();
	}

	public final Command getCommand() {
		return command;
	}


	public final String getTooltip() {
		return tooltip;
	}

	public final void setTooltip(final String tooltip) {
		this.tooltip = tooltip;
	}
}
