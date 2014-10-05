package db;

import java.util.ArrayList;
import java.util.List;

import com.trolltech.qt.gui.QColor;

import database.dao.AbstractGeneralDao;

public class MotorcycleDao extends AbstractGeneralDao
{
	List<Object> listOfMotorcycles = new ArrayList<Object>();

	public MotorcycleDao()
	{
		listOfMotorcycles.add(new Motorcycle(Long.valueOf(1), "BMW", "Chopper", 110, 1100, "R 1100S", QColor.white));

		listOfMotorcycles.add(new Motorcycle(Long.valueOf(2), "Ducati", "Supersportler", 61, 750, "750 SS Supersport", QColor.lightGray));

		listOfMotorcycles.add(new Motorcycle(Long.valueOf(3),"Honda", "Sportler", 95, 600, "Hornet 600", QColor.gray));

		listOfMotorcycles.add(new Motorcycle(Long.valueOf(4),"Kawasaki", "Supersportler", 141, 900, "Ninja ZX - 9R", QColor.darkGray));

		listOfMotorcycles.add(new Motorcycle(Long.valueOf(5),"BMW", "Chopper", 59, 998, "R 100 R", QColor.darkGray.darker()));

		listOfMotorcycles.add(new Motorcycle(Long.valueOf(6),"Yamaha", "Sportler", 136, 750, "YZF 750R", QColor.darkGray.darker().darker()));

		listOfMotorcycles.add(new Motorcycle(Long.valueOf(7),"Honda", "Enduro", 100, 600, "CBR 600 F", QColor.darkRed));

		listOfMotorcycles.add(new Motorcycle(Long.valueOf(8),"Suzuki", "Cruiser", 77, 600, "GSF 600 S Bandit", QColor.blue));

		listOfMotorcycles.add(new Motorcycle(Long.valueOf(9),"Kawasaki", "Reiseenduro", 65, 650, "KLR 650", QColor.darkBlue.lighter()));

		listOfMotorcycles.add(new Motorcycle(Long.valueOf(10),"BMW", "Tourensport", 89, 1100, "R 1100 RS", QColor.black));
	}

	@Override
	public List<Object> findAll() {
		return listOfMotorcycles;
	}

	@Override
	public Object findById(final Long id) {
		return listOfMotorcycles.get(id.intValue());
	}

	@Override
	public void persist(final Object entity) {
	}

	@Override
	public void remove(final Object entity) {
	}
}
