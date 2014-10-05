package component;

import com.trolltech.qt.gui.QDockWidget;
import com.trolltech.qt.gui.QWidget;

import config.IComponentConfiguration;

public abstract class AbstractDockWidget extends QDockWidget
{
	private final IComponentConfiguration config;
	protected boolean visible = false;

	public AbstractDockWidget(final IComponentConfiguration config)
	{
		this.config = config;
		buildContent();
		visibilityChanged.connect(this, "visibilityChanged()");
		setMinimumHeight(250);
	}

	private void buildContent()
	{
		setWidget(getContent());
	}

	public final IComponentConfiguration getConfig() {
		return config;
	}

	public final void saveToConfig()
	{
		if(config != null)
		{
			config.saveGeometry(geometry());
			config.setAttached(isFloating());
			config.setFullScreen(isFullScreen());
		}
	}

	public final void loadFromConfig() {
	}

	public void visibilityChanged()
	{
		final QWidget parentWidget = parentWidget();
		if(parentWidget instanceof AbstractComponent)
		{
			if(((AbstractComponent) parentWidget).isActive())
			{
				final AbstractComponent component = (AbstractComponent) parentWidget;

				final String activeTab = component.getActiveTab();
				if(activeTab != null) {
					visible = isFloating() || activeTab.equals(windowTitle()) ? true : false;
				}
				else {
					visible = true;
				}
			}
			else {
				visible = false;
			}
		}
		loadData();
	}

	public abstract QWidget getContent();
	protected abstract void loadData();
}
