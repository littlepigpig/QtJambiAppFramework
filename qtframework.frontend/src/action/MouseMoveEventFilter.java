package action;

import com.trolltech.qt.core.QEvent;
import com.trolltech.qt.core.QObject;
import com.trolltech.qt.gui.QMenu;
import com.trolltech.qt.gui.QMenuBar;
import com.trolltech.qt.gui.QMouseEvent;
import com.trolltech.qt.gui.QTabBar;
import com.trolltech.qt.gui.QToolBar;

public class MouseMoveEventFilter extends QObject
{
	@Override
	public boolean eventFilter(final QObject arg, final QEvent arg2)
	{
		/**
		 * show tooltip on disabled actions
		 */
		if(arg2.type().equals(QEvent.Type.MouseMove) && arg2 instanceof QMouseEvent)
		{
			final QMouseEvent event = (QMouseEvent) arg2;

			if(arg instanceof QMenuBar) {
				showTooltip(((QMenuBar) arg).actionAt(event.pos()));
			}
			else if(arg instanceof QMenu) {
				showTooltip(((QMenu) arg).actionAt(event.pos()));
			}
			else if(arg instanceof QToolBar) {
				showTooltip(((QToolBar) arg).actionAt(event.pos()));
			}
			else if(arg instanceof QTabBar) {
				showTooltip(((QTabBar) arg).childAt(event.pos()));
			}
		}
		return super.eventFilter(arg, arg2);
	}

	private void showTooltip(final QObject object)
	{
		if(object instanceof CommandAction) {
			((CommandAction) object).displayTooltip();
		}
	}
}
