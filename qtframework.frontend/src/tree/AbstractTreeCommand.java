package tree;

import java.util.List;

import com.trolltech.qt.core.QCoreApplication;
import com.trolltech.qt.core.QObject;
import com.trolltech.qt.gui.QTreeWidget;
import com.trolltech.qt.gui.QTreeWidgetItem;
import command.BusinessCheckResult;
import command.Command;
import command.IBusinessStateChecker;
import command.ICommandExecuter;

public class AbstractTreeCommand extends QObject
{
	private final QTreeWidget tree;

	private final Command command;

	public AbstractTreeCommand(final QTreeWidget tree, final ICommandExecuter commandExecuter, final IBusinessStateChecker... businessChecker)
	{
		this.tree = tree;

		if(commandExecuter == null) {
			command = new Command(new CommandExecuter(), businessChecker);
		}
		else {
			command = new Command(commandExecuter, businessChecker);
		}

		final NodeSelectionBusinessStateChecker nodeSelectionBusinessStateChecker = new NodeSelectionBusinessStateChecker();
		command.addBusinessStateChecker(nodeSelectionBusinessStateChecker);
	}

	private class NodeSelectionBusinessStateChecker implements IBusinessStateChecker
    {
        public NodeSelectionBusinessStateChecker() {
        	tree.itemSelectionChanged.connect(command, "businessStateChanged()");
        }

		@Override
		public BusinessCheckResult checkBusinessState()
		{
			final List<QTreeWidgetItem> selectedItems = tree.selectedItems();

			if(selectedItems.isEmpty())
			{
				return new BusinessCheckResult(tr("No item selected"), false);
			}
			return BusinessCheckResult.POSSIBLE;
		}
    }

	private class CommandExecuter implements ICommandExecuter
	{
		@Override
		public void execute() {
			QCoreApplication.quit();
		}
	}

	public Command getCommand() {
		return command;
	}
}
