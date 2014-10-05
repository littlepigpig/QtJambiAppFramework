package app.detail;

import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QWidget;
import component.AbstractDockWidget;

import config.IComponentConfiguration;
import db.Motorcycle;
import detail.IChild;

public class MotorcycleDetail extends AbstractDockWidget implements IChild
{
	QLineEdit producerText, modelText, propertyText, capacityText, psText;
	QLabel producerLabel, modelLabel, propertyLabel, capacityLabel, psLabel;
	private Object selection;

	public MotorcycleDetail(final IComponentConfiguration config) {
		super(config);

		setWindowTitle("Motorcycle Detail");
		setWhatsThis("A Motorcycle Detail");
	}

	@Override
	public void parentSelectionChanged(final Object selection)
	{
		if(visible && selection != null) {
			final Motorcycle motorcycle = (Motorcycle) selection;
			producerText.setText(motorcycle.getProducer());
			modelText.setText(motorcycle.getModel());
			propertyText.setText(motorcycle.getProperty());
			capacityText.setText(String.valueOf(motorcycle.getCapacity()));
			psText.setText(String.valueOf(motorcycle.getPs()));
		}
		else {
			this.selection = selection;
		}
	}

	@Override
	public QWidget getContent()
	{
		final QWidget widget = new QWidget(this);

		producerLabel = new QLabel(tr("Producer"));
		modelLabel = new QLabel(tr("Model"));
		propertyLabel = new QLabel(tr("Property"));
		capacityLabel = new QLabel(tr("Capacity"));
		psLabel = new QLabel(tr("PS"));

		producerText = new QLineEdit();
		modelText = new QLineEdit();
		propertyText = new QLineEdit();
		capacityText = new QLineEdit();
		psText = new QLineEdit();

		final QGridLayout layout = new QGridLayout(widget);
		layout.addWidget(producerLabel, 0, 0);
		layout.addWidget(modelLabel, 1, 0);
		layout.addWidget(propertyLabel, 2, 0);
		layout.addWidget(capacityLabel, 4, 0);
		layout.addWidget(psLabel, 0, 2);

		layout.addWidget(producerText, 0, 1);
		layout.addWidget(modelText, 1, 1);
		layout.addWidget(propertyText, 2, 1);
		layout.addWidget(capacityText, 4, 1);
		layout.addWidget(psText, 0, 3);

		widget.setLayout(layout);

		return widget;
	}

	@Override
	protected void loadData() {
		parentSelectionChanged(selection);
	}
}
