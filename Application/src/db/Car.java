package db;

import com.trolltech.qt.gui.QColor;

public class Car extends Object
{
	private Long id;

	private String brand, carClass, type;
	private QColor color;
	private int year, doors, ps;

	public Car(final Long id, final String brand, final String carClass, final String type, final int year,
			final int doors, final QColor color, final int ps) {
		super();

		this.id = id;
		this.brand = brand;
		this.carClass = carClass;
		this.type = type;
		this.year = year;
		this.doors = doors;
		this.ps = ps;
		this.color = color;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(final String brand) {
		this.brand = brand;
	}

	public String getCarClass() {
		return carClass;
	}

	public void setCarClass(final String carClass) {
		this.carClass = carClass;
	}

	public String getType() {
		return type;
	}

	public void setTyp(final String type) {
		this.type = type;
	}

	public int getYear() {
		return year;
	}

	public void setYear(final int year) {
		this.year = year;
	}

	public int getDoors() {
		return doors;
	}

	public void setDoors(final int doors) {
		this.doors = doors;
	}

	public void setPs(final int ps) {
		this.ps = ps;
	}

	public int getPs() {
		return ps;
	}

	public void setColor(final QColor color) {
		this.color = color;
	}

	public QColor getColor() {
		return color;
	}
}
