package table;

import java.lang.reflect.Method;

import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.core.Qt.ItemFlags;
import com.trolltech.qt.core.Qt.Orientation;
import com.trolltech.qt.gui.QAbstractTableModel;
import com.trolltech.qt.gui.QColor;

import database.dao.IGeneralDao;

public class MasterTableModel extends QAbstractTableModel
{
	private final IGeneralDao dao;

	private final String[] fields;

	public static final int GetWholeRow = 0x752F;
	public static final int GetHeader = 0x752E;

	public MasterTableModel(final String[] fields, final IGeneralDao dao) {
		this.fields = fields;
		this.dao = dao;
	}

	@Override
	public int columnCount(final QModelIndex arg0) {
		return fields.length;
	}

	@Override
	public Object data(final QModelIndex arg0, final int role)
	{
		switch (role)
		{
		case Qt.ItemDataRole.DisplayRole:
		case Qt.ItemDataRole.EditRole:
			// do the same on both roles.
			Object object = dao.findAll().get(arg0.row());

			Class<? extends Object> class1 = object.getClass();
			try {
				final Method method = class1.getMethod("get" + fields[arg0.column()]);
				final Object obj = method.invoke(object);

				return obj;

			} catch (final Exception e1) {
				e1.printStackTrace();
			}
			break;

		case Qt.ItemDataRole.DecorationRole:
			// there is only for colors a decoration needed
			object = dao.findAll().get(arg0.row());
			class1 = object.getClass();

			try {
				final Method method = class1.getMethod("get" + fields[arg0.column()]);
				final Object obj = method.invoke(object);

				if(obj instanceof QColor) {
					return obj;
				}
			} catch (final Exception e1) {
				e1.printStackTrace();
			}
			break;
		case Qt.ItemDataRole.ToolTipRole:
			return "Farbe";

		case GetWholeRow:
			// will be called, if a row was selected
			return dao.findAll().get(arg0.row());

		case Qt.ItemDataRole.CheckStateRole:
			// to remove checkbox
			break;

		case Qt.ItemDataRole.TextAlignmentRole:
			return Qt.AlignmentFlag.AlignRight;

		default:
			break;
		}
		return null;
	}

	@Override
	public boolean setData(final QModelIndex index, final Object value, final int role)
	{
		if(role == Qt.ItemDataRole.EditRole)
		{
			final Object object = dao.findAll().get(index.row());
			final Class<? extends Object> class1 = object.getClass();
			try {
				final Method method = class1.getMethod("set" + fields[index.column()], value.getClass());
				method.invoke(object, value);

				return true;

			} catch (final Exception e1) {
				e1.printStackTrace();
			}

			dao.persist(value);
		}

		return false;
	}

	@Override
	public int rowCount(final QModelIndex arg0) {
		return dao.findAll().size();
	}

	@Override
	public Object headerData(final int section, final Orientation orientation, final int role)
	{
		if(Qt.ItemDataRole.DisplayRole == role)
		{
			if (orientation == Orientation.Horizontal) {
				return tr(fields[section]);
			}
		}

		if(GetHeader == role) {
			return fields;
		}
		return null;
	}

	@Override
	public ItemFlags flags(final QModelIndex index)
	{
		final ItemFlags flags = super.flags(index);
		flags.set(Qt.ItemFlag.ItemIsEnabled);
		flags.set(Qt.ItemFlag.ItemIsEditable);

		return flags;
	}
}
