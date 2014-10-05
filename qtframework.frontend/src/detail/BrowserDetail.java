package detail;

import com.trolltech.qt.core.QUrl;
import com.trolltech.qt.core.Qt.AlignmentFlag;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.webkit.QWebView;
import component.AbstractDockWidget;

import config.IComponentConfiguration;

public class BrowserDetail extends AbstractDockWidget implements IChild
{
	private QWebView browser;

	private Object selection;

	private QLineEdit field;

	public BrowserDetail(final IComponentConfiguration config)
	{
		super(config);

		setWindowTitle("Browser Detail");
		setWhatsThis("A Browser <i>Detail</i>");
		setMaximumSize(1200, 400);
	}

	@Override
	public QWidget getContent()
	{
		final QWidget widget = new QWidget();

		browser = new QWebView();

		field = new QLineEdit(this);
		field.returnPressed.connect(this, "loadUrl()");

		final QGridLayout layout = new QGridLayout(widget);
		layout.addWidget(field, 0, 0);
		layout.addWidget(browser, 1, 0);

		final QPushButton button = new QPushButton();
		button.setText("Go");
		button.setMaximumWidth(80);
		button.pressed.connect(this, "loadUrl()");
		layout.addWidget(button, 2, 0, AlignmentFlag.AlignRight);

		widget.setLayout(layout);
		return widget;
	}

	public void loadUrl() {
        String text = field.text();

        if (text.indexOf("://") < 0) {
        	if(text.indexOf("www.")< 0) {
				text = "http://www." + text;
			} else {
				text = "http://" + text;
			}
		}
		browser.load(new QUrl(text));
	}

	@Override
	public void parentSelectionChanged(final Object selection) {
		if(visible) {
			// do something
		} else {
			this.selection = selection;
		}
	}

	@Override
	protected void loadData() {
		if(selection != null) {
			parentSelectionChanged(selection);
		}
	}
}
