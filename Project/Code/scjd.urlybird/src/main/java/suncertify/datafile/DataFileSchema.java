package suncertify.datafile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

final class DataFileSchema {

    static DataFileSchema create(final DataFileHeader header,
	    final Collection<DataFileColumn> columns, final int deletedFlagIndex) {

	if (columns.isEmpty()) {
	    return new DataFileSchema(header,
		    Collections.<DataFileColumn> emptyList(), deletedFlagIndex,
		    0);
	}

	final ArrayList<DataFileColumn> columnsInOrder = new ArrayList<DataFileColumn>(
		columns);
	Collections.sort(columnsInOrder, new Comparator<DataFileColumn>() {

	    @Override
	    public int compare(final DataFileColumn column1,
		    final DataFileColumn column2) {
		return column1.getStartIndex() - column2.getStartIndex();
	    }
	});

	final int recordLength = columnsInOrder.get(columnsInOrder.size() - 1)
		.getEndIndex() + 1;
	return new DataFileSchema(header, columnsInOrder, deletedFlagIndex,
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
