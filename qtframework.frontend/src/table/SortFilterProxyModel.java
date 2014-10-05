package table;

import com.trolltech.qt.core.QAbstractItemModel;
import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.core.QRegExp;
import com.trolltech.qt.gui.QSortFilterProxyModel;

public class SortFilterProxyModel extends QSortFilterProxyModel
{
	private final QAbstractItemModel model;
	private boolean inclusive = true;
	private int column;

	public SortFilterProxyModel(final QAbstractItemModel model) {
		this.model = model;
		setSourceModel(model);
	}

	@Override
	protected boolean filterAcceptsRow(final int sourceRow, final QModelIndex sourceParent)
	{
		QModelIndex index;
		final QRegExp filter = filterRegExp();

		index = model.index(sourceRow, column, sourceParent);
		final Object data = model.data(index);

		if(data != null)
		{
			if(filter.indexIn(data.toString()) != -1) {
				if(inclusive) {
					return true;
				}
			}
			else {
				if(!inclusive) {
					return true;
				}
			}
		}
		return false;
	}

	public void setFilter(final QRegExp regExp, final boolean inclusive, final int column) {
		this.inclusive = inclusive;
		this.column = column;
		setFilterRegExp(regExp);
	}
}
