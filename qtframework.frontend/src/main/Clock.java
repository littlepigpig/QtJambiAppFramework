package main;

import com.trolltech.qt.core.QDate;
import com.trolltech.qt.core.QTime;
import com.trolltech.qt.core.QTimer;
import com.trolltech.qt.gui.QLabel;

public class Clock extends QLabel
{
	private final StringBuffer buffer = new StringBuffer();

	public Clock()
	{
		setMaximumHeight(25);

		final QTimer timer = new QTimer(this);
		timer.timeout.connect(this, "showTime()");
		timer.start(1000);

		showTime();
	}

	public void showTime()
	{
		final QTime time = QTime.currentTime();
		final QDate date = QDate.currentDate();

		buffer.delete(0, buffer.length());
		buffer.append(time.toString("hh:mm"));
		buffer.append(date.toString(" dd.MM.yyyy"));

		if((time.second() % 2) == 0) {
			buffer.setCharAt(2, ' ');
		}
		setText(buffer.toString());
	}
}
