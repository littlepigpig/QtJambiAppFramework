package main;

import com.trolltech.qt.core.QUrl;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QPalette;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.gui.QPalette.ColorRole;
import com.trolltech.qt.webkit.QWebPage;
import com.trolltech.qt.webkit.QWebView;

public class HelpWindow extends QWidget
{
	private final QWebView webView;
	private final QLineEdit searchField;

	public HelpWindow()
	{
		setWindowTitle("Hilfe");

		webView = new QWebView();
		final QPalette palette = webView.palette();
		palette.setColor(ColorRole.Highlight, QColor.green);

		webView.page().setPalette(palette);
		webView.load(new QUrl("c:/help/ch01.html"));

		searchField = new QLineEdit();
		searchField.returnPressed.connect(this, "textChanged()");

		createContent();
	}

	private void createContent()
	{
		final QVBoxLayout layout = new QVBoxLayout();
		layout.addWidget(webView);
		layout.addWidget(searchField);

		setLayout(layout);
	}

	/**
	 * SLOTS
	 */

	@SuppressWarnings("unused")
	private void textChanged() {
		webView.findText(searchField.text(), QWebPage.FindFlag.FindWrapsAroundDocument);
		webView.setFocus();
	}
}
