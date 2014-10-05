package config;

import com.trolltech.qt.core.QRect;

public interface IComponentConfiguration
{
	public void saveGeometry(QRect rect);

	public QRect loadGeometry();

	public void setAttached(boolean atteched);

	public boolean isAttached();

	public void setFullScreen(boolean fullScreen);

	public boolean isFullScreen();
}
