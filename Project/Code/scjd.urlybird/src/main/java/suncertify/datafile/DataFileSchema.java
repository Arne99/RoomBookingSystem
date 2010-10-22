package suncertify.datafile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class DataFileSchema {

    private final int key;
    private final int offsetSize;
    private final int recordLength;
    private final ArrayList<DataFileColumn> columns;

    DataFileSchema(final int key, final int offsetSize, final int recordLength,
	    final ArrayList<DataFileColumn> columns) {
	this.key = key;
	this.offsetSize = offsetSize;
	this.recordLength = recordLength;
	this.columns = new ArrayList<DataFileColumn>(columns);
    }

    @Override
    public String toString() {
	return "DataFileSchema" + " [ " + "key = " + key + "; offset = "
		+ offsetSize + "; recordLength = " + recordLength
		+ "; columns = " + columns + " ] ";
    }

    List<DataFileColumn> getColumns() {
	return Collections.unmodifiableList(columns);
    }

    int getOffsetSize() {
	return offsetSize;
    }

    int getRecordLength() {
	return recordLength;
    }

}
