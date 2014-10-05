package component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.trolltech.qt.core.QObject;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QTabBar;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.gui.QDockWidget.DockWidgetFeature;

import config.IComponentConfiguration;

public abstract class AbstractComponent extends QMainWindow implements IComponent, Serializable
{
	/***/
	private static final long serialVersionUID = 8195363570297454644L;

	private AbstractDockWidget topDockWidget = null;
	private AbstractDockWidget centralDockWidget = null;
	private AbstractDockWidget rightDockWidget = null;
	private AbstractDockWidget leftDockWidget = null;

	private final List<AbstractDockWidget> listOfBottomDockWidgets;

	private final Map<QWidget, AbstractDockWidget> mapOfDockWidget = new HashMap<QWidget, AbstractDockWidget>();
	private boolean componentActive;

	public AbstractComponent(final IComponentConfiguration config)
	{
		listOfBottomDockWidgets = new ArrayList<AbstractDockWidget>();
	}

	@Override
	public final void addBottomWidget(final AbstractDockWidget widget, final boolean tabify)
	{
		listOfBottomDockWidgets.add(widget);
		mapOfDockWidget.put(widget.widget(), widget);

		widget.setAllowedAreas(Qt.DockWidgetArea.BottomDockWidgetArea);
		widget.loadFromConfig();

		addDockWidget(Qt.DockWidgetArea.BottomDockWidgetArea, widget);

		if(tabify)
		{
			final int size = listOfBottomDockWidgets.size();
			for(int zaehler = 0; zaehler < size; zaehler++)
			{
				final AbstractDockWidget dockWidget = listOfBottomDockWidgets.get(zaehler);
				if(zaehler+1 < size)
				{
					final AbstractDockWidget dockWidget2 = listOfBottomDockWidgets.get(zaehler+1);
					tabifyDockWidget(dockWidget, dockWidget2);
				}
			}
		}
	}

	@Override
	public final void setTopWidget(final AbstractDockWidget widget)
	{
		topDockWidget = widget;
		topDockWidget.loadFromConfig();

		addDockWidget(Qt.DockWidgetArea.TopDockWidgetArea, topDockWidget);

	}

	@Override
	public final void addCentralWidget(final AbstractDockWidget widget)
	{
		centralDockWidget = widget;
		centralDockWidget.setFeatures(DockWidgetFeature.NoDockWidgetFeatures);
		centralDockWidget.loadFromConfig();
//		// to remove titlebar
//		centralDockWidget.setTitleBarWidget(new QWidget());

		setCentralWidget(centralDockWidget);
	}

	@Override
	public final void setRightWidget(final AbstractDockWidget widget, final String title)
	{
		rightDockWidget = widget;
		rightDockWidget.setWindowTitle(title);
		rightDockWidget.loadFromConfig();

		addDockWidget(Qt.DockWidgetArea.RightDockWidgetArea, rightDockWidget);
	}

	@Override
	public final void setLeftWidget(final AbstractDockWidget widget, final String title)
	{
		leftDockWidget = widget;
		leftDockWidget.setWindowTitle(title);
		leftDockWidget.loadFromConfig();

		addDockWidget(Qt.DockWidgetArea.LeftDockWidgetArea, leftDockWidget);
	}

	@Override
	public void saveToConfig()
	{
		if(topDockWidget != null) {
			topDockWidget.saveToConfig();
		}
		if(centralDockWidget != null) {
			centralDockWidget.saveToConfig();
		}
		if(rightDockWidget != null) {
			rightDockWidget.saveToConfig();
		}
		if(leftDockWidget != null) {
			leftDockWidget.saveToConfig();
		}
		if(!listOfBottomDockWidgets.isEmpty())
		{
			for(final AbstractDockWidget dockWidget : listOfBottomDockWidgets) {
				dockWidget.saveToConfig();
			}
		}
	}

	@Override
	public void loadConfig()
	{
		if(topDockWidget != null) {
			topDockWidget.loadFromConfig();
		}
		if(centralDockWidget != null) {
			centralDockWidget.loadFromConfig();
		}
		if(rightDockWidget != null) {
			rightDockWidget.loadFromConfig();
		}
		if(leftDockWidget != null) {
			leftDockWidget.loadFromConfig();
		}
		if(!listOfBottomDockWidgets.isEmpty())
		{
			for(final AbstractDockWidget dockWidget : listOfBottomDockWidgets) {
				dockWidget.loadFromConfig();
			}
		}
	}

	public void activate()
	{
		System.out.println("activate component: " + getNavigationName());
		loadConfig();

		componentActive = true;
	}

	public String getActiveTab()
	{
		final List<QObject> findChildren = findChildren(QTabBar.class);
		if(findChildren.size() > 0)
		{
			final QTabBar tabBar = (QTabBar) findChildren.get(0);

			return tabBar.tabText(tabBar.currentIndex());
		}
		return null;
	}

	public void deactivate()
	{
		System.out.println("deactivate component: " + getNavigationName());
		saveToConfig();

		componentActive = false;

		if(!listOfBottomDockWidgets.isEmpty())
		{
			for(final AbstractDockWidget dockWidget : listOfBottomDockWidgets) {
				if(dockWidget.isFloating()) {
					dockWidget.setFloating(false);
				}
			}
		}
	}

	public boolean isActive()
	{
		return componentActive;
	}
}
