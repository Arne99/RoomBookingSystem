package suncertify.datafile;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

final class DataFileSchema {

    private final DataFileHeader header;
    private final HashSet<DataFileColumn> valueColumns;
    private final int deletedFlagIndex;

    DataFileSchema(final DataFileHeader header,
	    final Collection<DataFileColumn> columns, final int deletedFlagIndex) {
	this.header = header;
	this.valueColumns = new HashSet<DataFileColumn>(columns);
	this.deletedFlagIndex = deletedFlagIndex;
    }

    @Override
    public String toString() {
	return "DataFileSchema" + " [ " + "header = " + header + "; columns = "
		+ valueColumns + "; deletedFlagIndex = " + deletedFlagIndex
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
		&& this.valueColumns.equals(schema.valueColumns);

    };

    @Override
    public int hashCode() {
	int result = 17;
	result = 31 * result + this.deletedFlagIndex;
	result = 31 * result + this.header.hashCode();
	result = 31 * result + this.valueColumns.hashCode();
	return result;
    }

    Set<DataFileColumn> getColumns() {
	return Collections.unmodifiableSet(valueColumns);
    }

    int getOffset() {
	return header.getHeaderLength();
    }

    int getRecordLength() {
	return header.getRecordLength();
    }

    int getDeletedFlagIndex() {
	return deletedFlagIndex;
    }
}
