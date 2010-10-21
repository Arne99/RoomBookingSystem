package suncertify.datafile;


public class DataFileColumn  {

    private final String columnName;
    private final int columnLength;

    public DataFileColumn(final String columnName, final int columnLength) {
	this.columnName = columnName;
	this.columnLength = columnLength;
    }

    @Override
    public boolean equals(final Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final DataFileColumn other = (DataFileColumn) obj;
	if (columnLength != other.columnLength) {
	    return false;
	}
	if (columnName == null) {
	    if (other.columnName != null) {
		return false;
	    }
	} else if (!columnName.equals(other.columnName)) {
	    return false;
	}
	return true;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + columnLength;
	result = prime * result
		+ ((columnName == null) ? 0 : columnName.hashCode());
	return result;
    }

    @Override
    public String toString() {
	return "RawColumnMetaData [columnName=" + columnName
		+ ", columnLength=" + columnLength + "]";
    }

}
