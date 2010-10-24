package suncertify.datafile;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import suncertify.db.Record;

class DataFileRecord implements Record {

    private final TreeMap<DataFileColumn, String> columnValues;

    DataFileRecord(final Map<DataFileColumn, String> columnValues) {
	super();
	this.columnValues = new TreeMap<DataFileColumn, String>(
		new Comparator<DataFileColumn>() {

		    @Override
		    public int compare(final DataFileColumn column1,
			    final DataFileColumn column2) {
			return column2.getStartPosition()
				- column1.getStartPosition();
		    }
		});

	this.columnValues.putAll(columnValues);
    }

    @Override
    public String toString() {
	return columnValues.toString();
    }

    Collection<String> getValuesInDatabaseOrdering() {
	return columnValues.values();
    }

    String printRecordWithSchema() {

	final StringBuilder sb = new StringBuilder();
	for (final DataFileColumn column : columnValues.keySet()) {
	    sb.append(String.format("%" + column.size() + "s", column.getName()));
	}

	return sb.toString();
    }
}
