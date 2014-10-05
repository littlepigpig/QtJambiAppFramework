package component;

import table.TableDockWidget;

import com.trolltech.qt.core.QAbstractItemModel;

import config.ComponentConfiguration;
import config.IComponentConfiguration;
import detail.IChild;

public class AbstractMasterNDetailComponent extends AbstractComponent
{
	/***/
	private static final long serialVersionUID = 706676028290048452L;

	private final TableDockWidget tableDockWidget;
	private final String navigationName;

	public AbstractMasterNDetailComponent(final IComponentConfiguration config, final String navigationName)
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

	public final void addDetail(final AbstractDockWidget widget)
	{
		if(widget instanceof IChild) {
			tableDockWidget.addChild((IChild) widget);
		}
		addBottomWidget(widget, true);
	}

	public final void setTableModel(final QAbstractItemModel model)
	{
		tableDockWidget.setTableModel(model);
	}
}
