package suncertify.db;

import java.util.ArrayList;
import java.util.List;

public class DataFileSchema implements DataSchema {

    private final ArrayList<ColumnMetaData> columns;
    private final int recordLength;

    public DataFileSchema(final List<? extends ColumnMetaData> columnMetaData,
	    final int recordLength) {
	this.recordLength = recordLength;
	this.columns = new ArrayList<ColumnMetaData>(columnMetaData);
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
	final DataFileSchema other = (DataFileSchema) obj;
	if (columns == null) {
	    if (other.columns != null) {
		return false;
	    }
	} else if (!columns.equals(other.columns)) {
	    return false;
	}
	if (recordLength != other.recordLength) {
	    return false;
	}
	return true;
    }

    @Override
    public Object getNumberOfColumns() {
	return columns.size();
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((columns == null) ? 0 : columns.hashCode());
	result = prime * result + recordLength;
	return result;
    }

    @Override
    public String toString() {
	return "DataFileSchema [columns=" + columns + ", recordLength="
		+ recordLength + "]";
    }

}
