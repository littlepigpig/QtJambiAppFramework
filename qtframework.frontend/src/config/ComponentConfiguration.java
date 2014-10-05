package config;

import com.trolltech.qt.core.QRect;

public class ComponentConfiguration implements IComponentConfiguration
{
	private final String id;

	public ComponentConfiguration(final String id) {
		this.id = id;
	}

	@Override
	public boolean isAttached() {
		return true;
	}

	@Override
	public boolean isFullScreen() {
		return false;
	}

	@Override
	public QRect loadGeometry() {
		return null;
	}

	@Override
	public void saveGeometry(final QRect rect) {
		System.out.println(id + ": " + rect.width() + ", " + rect.height());
	}

	@Override
	public void setAttached(final boolean attached) {
		System.out.println("attached: " + attached);

	}

	@Override
	public void setFullScreen(final boolean fullScreen) {
		System.out.println("fullscreen: " + fullScreen);
	}
}
