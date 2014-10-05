package app.main;

import main.MainWindow;
import table.MasterTableModel;
import tree.AbstractTreeCommand;
import tree.AbstractTreeItem;
import tree.NavigationTree;
import action.CommandAction;
import app.detail.CarDetail;

import com.trolltech.qt.core.QObject;
import com.trolltech.qt.gui.QKeySequence;
import com.trolltech.qt.gui.QMenu;
import com.trolltech.qt.gui.QMenuBar;
import component.AbstractMasterNDetailComponent;

import config.ComponentConfiguration;
import db.CarDao;
import detail.BrowserDetail;
import detail.VideoPlayerDetail;

public class App extends QObject
{
	private final QMenuBar menuBar;
	private final NavigationTree navigationTree;
	private final MainWindow mainWindow;
	private QMenu menu;

	public App()
	{
		menuBar = new QMenuBar();
		navigationTree = new NavigationTree();

		mainWindow = new MainWindow(menuBar, navigationTree);
		mainWindow.setGeometry(200, 50, 1280, 900);

		createActions();
		createDetails();
	}

	private void createActions()
	{
		menu = new QMenu(navigationTree);
		navigationTree.setContextMenu(menu);

		final CommandAction quitAction = new CommandAction(tr("Quit the Application"), menu);
		quitAction.setText(tr("Quit"));
		quitAction.setShortcut(new QKeySequence(tr("Ctrl+Q")));
		quitAction.setWhatsThis(tr("Quit the Application if possible"));

		final AbstractTreeCommand quitCommand = new AbstractTreeCommand(navigationTree, null);
		quitAction.setCommand(quitCommand.getCommand());

		menu.addAction(quitAction);
		menuBar.addAction(quitAction);
	}

	private void createDetails()
	{
		final AbstractMasterNDetailComponent carComponent = new AbstractMasterNDetailComponent(new ComponentConfiguration("Car Component"), "Car Component");
		carComponent.setTableModel(new MasterTableModel(
				new String[] {"Id", "Brand", "Class", "Type", "Year of construction", "Number of doors", "Color", "Ps"}, new CarDao()));
		final AbstractTreeItem item1 = new AbstractTreeItem(carComponent);

		final BrowserDetail browserDetail = new BrowserDetail(new ComponentConfiguration("Browser Detail"));
		carComponent.addDetail(browserDetail);

		final VideoPlayerDetail playerDetail = new VideoPlayerDetail(null, mainWindow.getToolbar(), menuBar);
		carComponent.addDetail(playerDetail);

		final CarDetail detail = new CarDetail(new ComponentConfiguration("Auto Detail"));
		carComponent.addDetail(detail);

		final AbstractMasterDetailComponent motorcycleComponent = new AbstractMasterDetailComponent(new ComponentConfiguration("Motorcycle Component"), "Motorcycle Component");
		motorcycleComponent.setTableModel(new MasterTableModel(
				new String[] {"Id", "Producer", "Property", "Ps", "Capacity", "Model", "Color"}, new MotorcycleDao()));
		final AbstractTreeItem item2 = new AbstractTreeItem(motorcycleComponent);

		final MotorradDetail motorradDetail = new MotorradDetail(null);
		motorcycleComponent.setDetailComponent(motorradDetail);

		final AbstractTreeItem item = new AbstractTreeItem(new AbstractMasterNDetailComponent(new ComponentConfiguration("masterDetail2"), "Component1"));
		item1.addChild(item2);

		navigationTree.addTopLevelItem(item);
		navigationTree.addTopLevelItem(item1);
	}

	public void display()
	{
		mainWindow.show();
	}
}
