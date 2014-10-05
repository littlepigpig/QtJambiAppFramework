package tree;

import com.trolltech.qt.gui.QTreeWidgetItem;
import command.ICommandExecuter;

public class DeleteTreeItem implements ICommandExecuter
{
	private final NavigationTree tree;
	private QTreeWidgetItem item;

	public DeleteTreeItem(final NavigationTree tree) {
		this.tree = tree;
		tree.itemClicked.connect(this, "itemClicked(QTreeWidgetItem, Integer)");
	}

	public void itemClicked(final QTreeWidgetItem item, final Integer column) {
		this.item = item;

	}

	@Override
	public void execute()
	{
		final QTreeWidgetItem parent = item.parent();
		if(parent != null) {
			parent.removeChild(item);
		} else {
			tree.invisibleRootItem().removeChild(item);
		}
	}
}
