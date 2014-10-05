package main;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import tree.AbstractTreeItem;
import tree.NavigationTree;

import com.trolltech.qt.core.QCoreApplication;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.core.Qt.ToolBarArea;
import com.trolltech.qt.gui.QCloseEvent;
import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QKeyEvent;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QMenuBar;
import com.trolltech.qt.gui.QStatusBar;
import com.trolltech.qt.gui.QToolBar;
import com.trolltech.qt.gui.QTreeWidgetItem;
import com.trolltech.qt.gui.QWhatsThis;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.gui.QDockWidget.DockWidgetFeature;
import component.AbstractComponent;
import component.AbstractDockWidget;

import config.ComponentConfiguration;
import config.IComponentConfiguration;

public class MainWindow extends QMainWindow
{
	private AbstractDockWidget navigationDockWidget, masterDetailDockWidget;

	private final NavigationTree tree;

	private final QMenuBar menuBar;

	private AbstractComponent newComponent, oldComponent = null;

	private final QStatusBar statusBar;

	private QToolBar toolbar;

	private final HelpWindow help = new HelpWindow();

	private final JFrame frame = new JFrame();

	public MainWindow(final QMenuBar menuBar, final NavigationTree tree)
	{
		setWindowFlags(Qt.WindowType.MacWindowToolBarButtonHint);

		this.menuBar = menuBar;
		this.tree = tree;

		createContent();
		navigationDockWidget.setWidget(tree);
		tree.itemSelectionChanged.connect(this, "changeDetail()");

		statusBar = new QStatusBar(this);
		statusBar.insertWidget(0, new QWidget(), 600);
		statusBar.insertWidget(1, new Clock());
		setStatusBar(statusBar);
		setWindowTitle(tr("Qt Jambi Example"));

		installEventFilter(this);

		createToolbar();

		frame.setMinimumSize(new Dimension(800, 600));
		frame.setTitle("Swing Frame");
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}

	private void createToolbar()
	{
		toolbar = new QToolBar(this);
		toolbar.addAction(new QIcon("classpath:icon/document_new.png"), "New");
		toolbar.addAction(new QIcon("classpath:icon/copy.png"), "Copy");
		toolbar.addAction(new QIcon("classpath:icon/paste.png"), "Paste");
		toolbar.addSeparator();
		toolbar.addAction(QWhatsThis.createAction());
		addToolBar(ToolBarArea.TopToolBarArea, toolbar);
	}

	private void createContent()
	{
		setMenuBar(menuBar);

		masterDetailDockWidget = new MasterDockWidget(new ComponentConfiguration("masterDock"));
		masterDetailDockWidget.setFeatures(DockWidgetFeature.NoDockWidgetFeatures);
		masterDetailDockWidget.setTitleBarWidget(new QWidget());
		masterDetailDockWidget.setMinimumSize(400, 300);

		navigationDockWidget = new NavigationDockWidget(new ComponentConfiguration("navigationDock"));
		navigationDockWidget.setAllowedAreas(Qt.DockWidgetArea.LeftDockWidgetArea,
				Qt.DockWidgetArea.RightDockWidgetArea);
		addDockWidget(Qt.DockWidgetArea.LeftDockWidgetArea, navigationDockWidget);

		setCentralWidget(masterDetailDockWidget);
	}

	public final void replaceComponent(final AbstractComponent newComponent)
	{
		this.newComponent = newComponent;
		if(oldComponent == null) {
			oldComponent = newComponent;
		}
		else {
			oldComponent.deactivate();
			oldComponent = newComponent;
		}

		this.newComponent.activate();
		masterDetailDockWidget.setWidget(newComponent);
	}

	@Override
	public void setVisible(final boolean visible)
	{
		super.setVisible(visible);

		if(!visible) {
			if(newComponent != null) {
				newComponent.deactivate();
			}
		}
	}

	@Override
	protected void keyReleaseEvent(final QKeyEvent arg) {
		super.keyReleaseEvent(arg);

		if(arg.key() == Qt.Key.Key_F1.value()) {
			help.setVisible(true);
			frame.setVisible(true);
		}
	}

	@Override
	protected void closeEvent(final QCloseEvent arg) {
		help.dispose();
		super.closeEvent(arg);
	}



	public QToolBar getToolbar() {
		return toolbar;
	}

	/**
	 * SLOTS
	 */

	@SuppressWarnings("unused")
	private final void changeDetail()
	{
		final List<QTreeWidgetItem> selectedItems = tree.selectedItems();

		if(selectedItems != null && selectedItems.size() > 0)
		{
			if(selectedItems.get(0) instanceof AbstractTreeItem) {
				final AbstractTreeItem treeItem = (AbstractTreeItem) selectedItems.get(0);

				final AbstractComponent detailComponent = (AbstractComponent) treeItem.getDetailWindow();
				replaceComponent(detailComponent);
			}
		}
	}

	@SuppressWarnings("unused")
	private void quit()
	{
		QCoreApplication.quit();
	}

	private class NavigationDockWidget extends AbstractDockWidget
	{
		public NavigationDockWidget(final IComponentConfiguration config) {
			super(config);
		}

		@Override
		public QWidget getContent() {
			return tree;
		}

		@Override
		protected void loadData() {
		}
	}

	private class MasterDockWidget extends AbstractDockWidget
	{
		public MasterDockWidget(final IComponentConfiguration config) {
			super(config);
		}

		@Override
		public QWidget getContent() {
			return oldComponent;
		}

		@Override
		protected void loadData() {
		}
	}
}
