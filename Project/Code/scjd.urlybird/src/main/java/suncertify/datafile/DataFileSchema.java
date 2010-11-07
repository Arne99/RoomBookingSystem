package suncertify.datafile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class DataFileSchema {

    private final DataFileHeader header;
    private final List<DataFileColumn> columnsInOrder;
    private final int recordLength;
    private final DeletedFlag deletedFlag;

    DataFileSchema(final DataFileHeader header,
	    final List<DataFileColumn> columns, final DeletedFlag deletedFlag,
	    final int recordLength) {
	this.header = header;
	this.recordLength = recordLength;
	this.deletedFlag = deletedFlag;
	this.columnsInOrder = new ArrayList<DataFileColumn>(columns);
    }

    @Override
    public String toString() {
	return "DataFileSchema" + " [ " + "header = " + header + "; columns = "
		+ columnsInOrder + "; deletedFlag = " + deletedFlag + " ] ";
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
		&& this.deletedFlag == schema.deletedFlag
		&& this.columnsInOrder.equals(schema.columnsInOrder);

    };

    @Override
    public int hashCode() {
	int result = 17;
	result = 31 * result + this.deletedFlag.hashCode();
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

    boolean isBufferAValidRecord(final byte[] buffer) {

	return buffer.length == recordLength
		&& buffer[deletedFlag.getIndex()] != deletedFlag
			.getIdentifier();
    }

}
