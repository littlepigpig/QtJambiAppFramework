package app.detail;

import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QWidget;
import component.AbstractDockWidget;

import config.IComponentConfiguration;
import db.Car;
import detail.IChild;

public class CarDetail extends AbstractDockWidget implements IChild
{
	QLineEdit brandText, classText, typText, yearText, doorsText, psText;
	QLabel brandLabel, classLabel, typLabel, yearLabel, doorsLabel, psLabel;
	private Object selection;

	public CarDetail(final IComponentConfiguration config) {
		super(config);

		setWindowTitle("Car Detail");
		setWhatsThis("A Car Detail");
	}

	@Override
	public void parentSelectionChanged(final Object selection)
	{
		if(visible && selection != null) {
			final Car car = (Car) selection;
			brandText.setText(car.getBrand());
			classText.setText(car.getCarClass());
			typText.setText(car.getType());
			yearText.setText(String.valueOf(car.getYear()));
			doorsText.setText(String.valueOf(car.getDoors()));
			psText.setText(String.valueOf(car.getPs()));
		}
		else {
			this.selection = selection;
		}
	}

	@Override
	public QWidget getContent()
	{
		final QWidget widget = new QWidget(this);

		brandLabel = new QLabel(tr("Brand"));
		classLabel = new QLabel(tr("Class"));
		typLabel = new QLabel(tr("Type"));
		yearLabel = new QLabel(tr("Year of construction"));
		doorsLabel = new QLabel(tr("Number of doors"));
		psLabel = new QLabel(tr("PS"));

		brandText = new QLineEdit();
		classText = new QLineEdit();
		typText = new QLineEdit();
		typText.setWhatsThis("type of the car type of the car type of the car type of the car ");
		yearText = new QLineEdit();
		doorsText = new QLineEdit();
		psText = new QLineEdit();

		final QGridLayout layout = new QGridLayout(widget);
		layout.addWidget(brandLabel, 0, 0);
		layout.addWidget(classLabel, 1, 0);
		layout.addWidget(typLabel, 2, 0);
		layout.addWidget(yearLabel, 3, 0);
		layout.addWidget(doorsLabel, 4, 0);
		layout.addWidget(psLabel, 0, 2);

		layout.addWidget(brandText, 0, 1);
		layout.addWidget(classText, 1, 1);
		layout.addWidget(typText, 2, 1);
		layout.addWidget(yearText, 3, 1);
		layout.addWidget(doorsText, 4, 1);
		layout.addWidget(psText, 0, 3);

		widget.setLayout(layout);

		return widget;
	}

	@Override
	protected void loadData() {
		parentSelectionChanged(selection);
	}
}
