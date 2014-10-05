package component;

import table.TableDockWidget;

import com.trolltech.qt.core.QAbstractItemModel;

import config.ComponentConfiguration;
import config.IComponentConfiguration;
import detail.IChild;

public class MasterDetailComponent extends AbstractComponent
{
	/***/
	private static final long serialVersionUID = 6492770985280148794L;

	private final TableDockWidget tableDockWidget;
	private final String navigationName;

	public MasterDetailComponent(final IComponentConfiguration config, final String navigationName)
	{
		super(config);
		this.navigationName = navigationName;
		tableDockWidget = new TableDockWidget(new ComponentConfiguration("QTableWidget"));
		addCentralWidget(tableDockWidget);
	}

	@Override
	public String getNavigationName() {
		return navigationName;
	}

	public final void setDetailComponent(final AbstractDockWidget widget)
	{
		tableDockWidget.addChild((IChild) widget);
		addBottomWidget(widget, false);
	}

	public final void setTableModel(final QAbstractItemModel model)
	{
		tableDockWidget.setTableModel(model);
	}
}
