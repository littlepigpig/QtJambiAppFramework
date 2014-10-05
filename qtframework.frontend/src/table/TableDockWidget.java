package table;

import java.util.HashSet;
import java.util.Set;

import com.trolltech.qt.core.QAbstractItemModel;
import com.trolltech.qt.core.QPoint;
import com.trolltech.qt.core.QRect;
import com.trolltech.qt.core.QRegExp;
import com.trolltech.qt.core.QSize;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.core.Qt.SortOrder;
import com.trolltech.qt.core.Qt.ToolBarArea;
import com.trolltech.qt.core.Qt.WindowType;
import com.trolltech.qt.gui.QAction;
import com.trolltech.qt.gui.QContextMenuEvent;
import com.trolltech.qt.gui.QHeaderView;
import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QItemSelection;
import com.trolltech.qt.gui.QItemSelectionModel;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QMenu;
import com.trolltech.qt.gui.QTableView;
import com.trolltech.qt.gui.QToolBar;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.gui.QAbstractItemView.SelectionBehavior;
import com.trolltech.qt.gui.QAbstractItemView.SelectionMode;
import com.trolltech.qt.gui.QHeaderView.ResizeMode;
import component.AbstractDockWidget;

import config.IComponentConfiguration;
import detail.IChild;

public class TableDockWidget extends AbstractDockWidget
{
	private QTableView tableView;

	private SortFilterProxyModel proxyModel;

	private final Set<IChild> setOfChilds = new HashSet<IChild>();

	private QItemSelectionModel selectionModel;

	private final QMenu menu = new QMenu(this);

	private final QMenu filterMenu = new QMenu(this);

	private QToolBar toolBar;

	private QMainWindow innerWidget;

	public TableDockWidget(final IComponentConfiguration config)
	{
		super(config);

		setWindowTitle("Master Tabelle");
		setWhatsThis("The Master Table");

		menu.addAction(new QAction("Action", menu));

		createToolBar();
		createMenu();
	}

	@Override
	protected void contextMenuEvent(final QContextMenuEvent arg)
	{
		// the size of the dock titlebar is 23
		final QRect rect = new QRect(new QPoint(5, 23), tableView.horizontalHeader().rect().size());
		if(rect.intersects(new QRect(arg.pos(), new QSize(1, 1)))) {
			menu.exec(arg.globalPos());
		}
		else {
			filterMenu.exec(arg.globalPos());
		}
	}

	private void createMenu()
	{
//		final QHeaderView horizontalHeader = tableView.horizontalHeader();
//
//		final String[] header = (String[]) proxyModel.headerData(0, null, MasterTableModel.GetHeader);
//
//		for(final String string : header)
//		{
//			final QAction action = new QAction(menu);
//			action.setCheckable(true);
//			action.setChecked(true);
//
//		}
//
//		final QAction action = new QAction("Action2", menu);
//		menu.addAction(action);

		final QAction inclusiveFilterAction = new QAction(filterMenu);
		inclusiveFilterAction.setText("Inclusive Filter");
		inclusiveFilterAction.triggered.connect(this, "setFilter()");

		filterMenu.addAction(inclusiveFilterAction);

		final QAction exclusiveFilterAction = new QAction(filterMenu);
		exclusiveFilterAction.setText("Exclusive Filter");
		exclusiveFilterAction.triggered.connect(this, "setExclusiveFilter()");

		filterMenu.addAction(exclusiveFilterAction);
	}

	public final void setTableModel(final QAbstractItemModel model)
	{
		proxyModel = new SortFilterProxyModel(model);
		proxyModel.setDynamicSortFilter(true);
		proxyModel.sort(0, SortOrder.AscendingOrder);

		tableView.setModel(proxyModel);

		selectionModel = tableView.selectionModel();
		selectionModel.selectionChanged.connect(this, "selectionChanged(QItemSelection, QItemSelection)");
	}

	@Override
	public QWidget getContent()
	{
		tableView = new QTableView();
		tableView.setItemDelegate(new ItemDelegate());

		final QHeaderView horizontalHeader = tableView.horizontalHeader();
		horizontalHeader.setMovable(true);
		horizontalHeader.setResizeMode(ResizeMode.Stretch);
		horizontalHeader.setHighlightSections(false);

		tableView.setSortingEnabled(true);
		tableView.setSelectionBehavior(SelectionBehavior.SelectRows);
		tableView.setSelectionMode(SelectionMode.SingleSelection);
		tableView.verticalHeader().setDefaultSectionSize(20);

		innerWidget = new QMainWindow(this);
		innerWidget.setCentralWidget(tableView);
		innerWidget.setWindowFlags(WindowType.Widget);

		return innerWidget;
	}

	private void createToolBar()
	{
		toolBar = new QToolBar();

		final QAction deleteFilterAction = new QAction(toolBar);
		deleteFilterAction.setIcon(new QIcon("classpath:icon/delete2.png"));
		deleteFilterAction.setText(tr("Filter löschen"));
		deleteFilterAction.triggered.connect(this, "removeFilter()");

		toolBar.addAction(deleteFilterAction);
		innerWidget.addToolBar(ToolBarArea.LeftToolBarArea, toolBar);
		toolBar.setVisible(false);
	}

	public void addChild(final IChild child) {
		setOfChilds.add(child);
	}

	public void removeChild(final IChild child) {
		setOfChilds.remove(child);
	}

	@Override
	protected void loadData() {
		//nothing todo, table should always be visible
	}

	/**
	 * SLOTS
	 */

	@SuppressWarnings("unused")
	private void setFilter()
	{
		final Object data = proxyModel.data(selectionModel.currentIndex());

		if(data != null) {
			final QRegExp regExp = new QRegExp(data.toString(),
					Qt.CaseSensitivity.CaseSensitive, QRegExp.PatternSyntax.FixedString);
			proxyModel.setFilter(regExp, true, selectionModel.currentIndex().column());

			toolBar.setVisible(true);
		}
	}

	@SuppressWarnings("unused")
	private void setExclusiveFilter()
	{
		final Object data = proxyModel.data(selectionModel.currentIndex());

		if(data != null) {
			final QRegExp regExp = new QRegExp(data.toString(),
					Qt.CaseSensitivity.CaseSensitive, QRegExp.PatternSyntax.FixedString);
			proxyModel.setFilter(regExp, false, selectionModel.currentIndex().column());

			toolBar.setVisible(true);
		}
	}

	@SuppressWarnings("unused")
	private void removeFilter()
	{
		final QRegExp regExp = new QRegExp("",
				Qt.CaseSensitivity.CaseSensitive, QRegExp.PatternSyntax.FixedString);
		proxyModel.setFilter(regExp, true, 0);

		toolBar.setVisible(false);
	}

	@SuppressWarnings("unused")
	private final void selectionChanged(final QItemSelection selected, final QItemSelection deselected)
	{
		for(final IChild child : setOfChilds) {
			if(selectionModel.selectedRows().size() > 0) {
				child.parentSelectionChanged(selectionModel.selectedRows().get(0).data(MasterTableModel.GetWholeRow));
			}
		}
	}
}
