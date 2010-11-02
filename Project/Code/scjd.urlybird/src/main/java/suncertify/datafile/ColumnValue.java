package suncertify.datafile;

public class ColumnValue implements Comparable<ColumnValue> {

    private final DataFileColumn column;

    private final String value;

    public ColumnValue(final DataFileColumn column, final String value) {
	super();
	this.column = column;
	this.value = value;
    }

    public DataFileColumn getColumn() {
	return column;
    }

    public String getValue() {
	return value;
    }

    @Override
    public boolean equals(final Object object) {
	if (object == this) {
	    return true;
	}
	if (!(object instanceof ColumnValue)) {
	    return false;
	}
	final ColumnValue columnValue = (ColumnValue) object;
	return this.column.equals(columnValue.column)
		&& this.value == columnValue.value;
    }

    @Override
    public int hashCode() {
	int result =17;
	result = 31 *result +  
	return result;
    }
}
