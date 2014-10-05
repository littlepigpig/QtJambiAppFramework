package table;

import com.trolltech.qt.core.QAbstractItemModel;
import com.trolltech.qt.core.QModelIndex;
import com.trolltech.qt.core.QSize;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.core.Qt.BGMode;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QColorDialog;
import com.trolltech.qt.gui.QItemDelegate;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QStyleOptionViewItem;
import com.trolltech.qt.gui.QWidget;

public class ItemDelegate extends QItemDelegate
{
	private QColorDialog dialog;

	@Override
	public void paint(final QPainter painter, final QStyleOptionViewItem option,
			final QModelIndex index) {

		if(index.row()%2 == 0) {
			painter.setBackgroundMode(BGMode.TransparentMode);
			painter.fillRect(option.rect(), QColor.fromRgb(212, 230, 230));
		}

		super.paint(painter, option, index);
	}

	@Override
	public QSize sizeHint(final QStyleOptionViewItem option, final QModelIndex index)
	{
		 final QSize s = super.sizeHint(option, index);
		 if( s.isValid() ) {
		    s.setHeight(16);
		 }
		 return s;
	}

	@Override
	public QWidget createEditor(final QWidget parent, final QStyleOptionViewItem option,
			final QModelIndex index)
	{
		final Object data = index.data();
		if(data instanceof QColor) {
			dialog = new QColorDialog();
			return dialog;
		}
		return super.createEditor(parent, option, index);
	}

	@Override
	public void setModelData(final QWidget editor, final QAbstractItemModel model,
			final QModelIndex index)
	{

		if (model.data(index) instanceof QColor) {
			model.setData(index, dialog.currentColor(), Qt.ItemDataRole.EditRole);
		}
		else {
			super.setModelData(editor, model, index);
		}
	}

	@Override
	public void setEditorData(final QWidget editor, final QModelIndex index)
	{
		final Object data = index.data();
		if(data instanceof QColor) {
			dialog.setCurrentColor((QColor) data);
		}
		super.setEditorData(editor, index);
	}
}
