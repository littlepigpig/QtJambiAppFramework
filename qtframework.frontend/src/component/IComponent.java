package component;

public interface IComponent
{
	public String getNavigationName();

	public void saveToConfig();
	public void loadConfig();

	public void setTopWidget(AbstractDockWidget widget);
	public void addCentralWidget(AbstractDockWidget widget);
	public void setLeftWidget(AbstractDockWidget widget, String title);
	public void setRightWidget(AbstractDockWidget widget, String title);

	/**
	 * Add Widgets on bottom of the window and tabify them if tabify=true
	 *
	 * @param widget to add
	 * @param title to display
	 * @param tabify if true tabs will be created
	 */
	public void addBottomWidget(AbstractDockWidget widget, boolean tabify);

	/**
	 * activate the component
	 */
	public void activate();

	/**
	 * deactivate the component
	 */
	public void deactivate();
}
