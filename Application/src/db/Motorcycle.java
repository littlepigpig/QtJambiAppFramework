package db;

import com.trolltech.qt.gui.QColor;

public class Motorcycle
{
	private Long id;

	private String property, producer, model;
	private Integer ps, capacity;
	private QColor color;

	public Motorcycle(final Long id, final String producer, final String property, final Integer ps, final Integer capacity,
			final String model, final QColor color) {
		super();

		this.id = id;
		this.ps = ps;
		this.capacity = capacity;
		this.property = property;
		this.producer = producer;
		this.model = model;
		this.color = color;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id)
	{
		this.id = id;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(final String producer) {
		this.producer = producer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(final String model) {
		this.model = model;
	}

	public Integer getPs() {
		return ps;
	}

	public void setPs(final Integer ps) {
		this.ps = ps;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(final Integer capacity) {
		this.capacity = capacity;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(final String property) {
		this.property = property;
	}

	public QColor getColor() {
		return color;
	}

	public void setColor(final QColor color) {
		this.color = color;
	}
}
