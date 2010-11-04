package suncertify.datafile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

final class DataFileSchema {

    static DataFileSchema create(final DataFileHeader header,
	    final List<DataFileColumn> columnsInDbOrder,
	    final int deletedFlagIndex) {

	if (columnsInDbOrder.isEmpty()) {
	    return new DataFileSchema(header,
		    Collections.<DataFileColumn> emptyList(), deletedFlagIndex,
		    0);
	}

	final int recordLength = columnsInDbOrder.get(
		columnsInDbOrder.size() - 1).getEndIndex() + 1;
	return new DataFileSchema(header, columnsInDbOrder, deletedFlagIndex,
		recordLength);
    }

    private final DataFileHeader header;
    private final List<DataFileColumn> columnsInOrder;
    private final int deletedFlagIndex;
    private final int recordLength;

    private DataFileSchema(final DataFileHeader header,
	    final List<DataFileColumn> columns, final int deletedFlagIndex,
	    final int recordLength) {
	this.header = header;
	this.recordLength = recordLength;
	this.columnsInOrder = new ArrayList<DataFileColumn>(columns);
	this.deletedFlagIndex = deletedFlagIndex;
    }

    @Override
    public String toString() {
	return "DataFileSchema" + " [ " + "header = " + header + "; columns = "
		+ columnsInOrder + "; deletedFlagIndex = " + deletedFlagIndex
		+ " ] ";
    }

    @Override
    public boolean equals(final Object object) {
	if (object == this) {
	    return true;
	}
	if (!(object instanceof DataFileSchema)) {
	    return false;
	}
	final DataFileSchema schema = (DataFileSchema) object;
	return this.header.equals(schema.header)
		&& this.deletedFlagIndex == schema.deletedFlagIndex
		&& this.columnsInOrder.equals(schema.columnsInOrder);

    };

    @Override
    public int hashCode() {
	int result = 17;
	result = 31 * result + this.deletedFlagIndex;
	result = 31 * result + this.header.hashCode();
	result = 31 * result + this.columnsInOrder.hashCode();
	return result;
    }

    List<DataFileColumn> getColumnsInDatabaseOrder() {

	return Collections.unmodifiableList(columnsInOrder);
    }

    int getOffset() {
	return header.getHeaderLength();
    }

    int getRecordLength() {
	return recordLength;
    }

    int getDeletedFlagIndex() {
	return deletedFlagIndex;
    }
}
