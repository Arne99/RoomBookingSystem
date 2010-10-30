package suncertify.datafile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class DataFileSchema {

    private final int key;
    private final int startIndexValueColumns;
    private final int recordLength;
    private final ArrayList<DataFileColumn> valueColumns;
    private final int deletedFlagIndex;

    DataFileSchema(final int key, final int startIndexValueColumns, final int recordLength,
	    final ArrayList<DataFileColumn> columns,
	    final int deletedFlagIndex) {
	this.key = key;
	this.startIndexValueColumns = startIndexValueColumns;
	this.recordLength = recordLength;
	this.valueColumns = new ArrayList<DataFileColumn>(columns);
	this.deletedFlagIndex = deletedFlagIndex;
    }

    @Override
    public String toString() {
	return "DataFileSchema" + " [ " + "key = " + key + "; offset = "
		+ startIndexValueColumns + "; recordLength = " + recordLength
		+ "; columns = " + valueColumns + " ] ";
    }

    List<DataFileColumn> getColumns() {
	return Collections.unmodifiableList(valueColumns);
    }

    int getIndexValueColumns() {
	return startIndexValueColumns;
    }

    int getRecordLength() {
	return recordLength;
    }

    int getDeletedFlagIndex() {
	return deletedFlagIndex;
    }
}
