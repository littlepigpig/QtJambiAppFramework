package app;

import app.main.App;

import com.trolltech.qt.gui.QApplication;

public class Main {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
        QApplication.initialize(args);

        final App app = new App();
        app.display();
        QApplication.exec();
	}
}
