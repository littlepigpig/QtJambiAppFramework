package tree;

import com.trolltech.qt.gui.QTreeWidgetItem;
import component.IComponent;

public class AbstractTreeItem extends QTreeWidgetItem
{
	private final IComponent detailWindow;

	public AbstractTreeItem(final IComponent detailWindow)
	{
		super();
		this.detailWindow = detailWindow;

		setText(0, detailWindow.getNavigationName());
	}

    public IComponent getDetailWindow() {
		return detailWindow;
	}
}
