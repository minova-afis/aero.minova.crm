package aero.minova.crm.main.nattable;

import org.eclipse.nebula.widgets.nattable.data.IColumnPropertyAccessor;

import aero.minova.crm.model.jpa.Group;

public class GroupColumnPropertyAccessor implements IColumnPropertyAccessor<Group> {

	@Override
	public Object getDataValue(Group g, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return g.getName();
		case 1:
			return "(" + g.getMembers().size() + ")";
		default:
			return "UNDEFINED";

		}
	}

	@Override
	public void setDataValue(Group g, int columnIndex, Object newValue) {
		switch (columnIndex) {
		case 0:
			g.setName(String.valueOf(newValue));
		case 1:
			return;
		default:
			throw new IllegalArgumentException("column number out of range");
		}

	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public String getColumnProperty(int columnIndex) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public int getColumnIndex(String propertyName) {
		// TODO Auto-generated method stub
		return 0;
	}

}
