package persistence;

public interface IBaseProxy
{
	/**
	 * Adds a property change listener to the implementing proxy.
	 *
	 * @param listener
	 */
	public void addPropertyChangeListener(java.beans.PropertyChangeListener listener);

	/**
	 * Removes a property change listener from the implementing proxy.
	 *
	 * @param listener
	 */
	public void removePropertyChangeListener(java.beans.PropertyChangeListener listener);

	/**
	 * Returns the persistent ID of this object. If it was not persisted before, this method returns null.
	 *
	 * @return the id
	 */
	public Long getId();

	/**
	 * Returns the version of this object.
	 *
	 * @return the version
	 */
	public Integer getVersion();

	/**
	 * Returns the persistence class
	 *
	 * @return the persistence class
	 */
	public Class<?> persistenceClass();
}
