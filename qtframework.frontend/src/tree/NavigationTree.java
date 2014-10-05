package tree;

import java.util.List;

import action.CommandAction;

import com.trolltech.qt.core.QByteArray;
import com.trolltech.qt.core.QDataStream;
import com.trolltech.qt.core.QIODevice;
import com.trolltech.qt.core.QMimeData;
import com.trolltech.qt.core.Qt.DropAction;
import com.trolltech.qt.gui.QContextMenuEvent;
import com.trolltech.qt.gui.QDragEnterEvent;
import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QKeySequence;
import com.trolltech.qt.gui.QMenu;
import com.trolltech.qt.gui.QTreeWidget;
import com.trolltech.qt.gui.QTreeWidgetItem;

public class NavigationTree extends QTreeWidget
{
	private QMenu contextMenu;

	public NavigationTree()
	{
		setWhatsThis("A <br>Navigation <br>Tree");

		setMinimumSize(200, 800);
		setDragEnabled(true);
		setAcceptDrops(true);
		setDropIndicatorShown(true);
		setAnimated(true);
		setSelectionMode(SelectionMode.SingleSelection);
	}

	public final void setContextMenu(final QMenu contextMenu)
	{
		this.contextMenu = contextMenu;

		appendActions();
	}

	private void appendActions()
	{
		final CommandAction action = new CommandAction(tr("Quit the Application"), contextMenu);
		action.setText(tr("Löschen"));
		action.setShortcut(new QKeySequence(tr("Ctrl+d")));
		action.setIcon(new QIcon("classpath:icon/delete2.png"));

		final AbstractTreeCommand deleteItemCommand = new AbstractTreeCommand(this, new DeleteTreeItem(this));
		action.setCommand(deleteItemCommand.getCommand());

		contextMenu.addSeparator();
		contextMenu.addAction(action);
	}

	@Override
	protected void contextMenuEvent(final QContextMenuEvent arg) {
		if(contextMenu != null) {
			contextMenu.exec(arg.globalPos());
		}
	}

	@Override
	protected void dragEnterEvent(final QDragEnterEvent event) {
		event.accept();
	}

	@Override
	protected boolean dropMimeData(final QTreeWidgetItem parent, final int index,
			final QMimeData data, final DropAction action)
	{
        if (parent != null)
        {
        	final QByteArray ba = data.data("foo/bar");
        	final QDataStream ds = new QDataStream(ba, QIODevice.OpenModeFlag.ReadOnly);

        	while(!ds.atEnd()) {
        		final String str = ds.readString();

        		final QTreeWidgetItem newItem = new QTreeWidgetItem(parent);
        		newItem.setText(0, str);
        	}
        }
        return true;
	}

	@Override
	protected QMimeData mimeData(final List<QTreeWidgetItem> items)
	{
        final QByteArray ba = new QByteArray();
        final QDataStream ds = new QDataStream(ba, QIODevice.OpenModeFlag.WriteOnly);

        for (int i=0;i<items.size();i++)
        {
        	if(items.get(i) instanceof AbstractTreeItem)
        	{
//        		final AbstractTreeItem treeItem = (AbstractTreeItem) items.get(i);

        		ds.writeString(items.get(i).text(0));
        	}

		}
        final QMimeData md = new QMimeData();

        md.setData("foo/bar", ba);

        return md;
	}
}
