package suncertify.datafile;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

final class UrlyBirdSchema implements DataFileMetadata {

    private final static class ColumnStartIndexComparator implements
	    Comparator<DataFileColumn> {
	@Override
	public int compare(final DataFileColumn columnOne,
		final DataFileColumn columnTwo) {
	    return columnOne.getStartIndex() - columnTwo.getStartIndex();
	}
    }

    private final DataFileHeader header;
    private final SortedSet<DataFileColumn> columns;
    private final int recordLength;
    private final DeletedFlag deletedFlag;

    UrlyBirdSchema(final DataFileHeader header,
	    final Collection<DataFileColumn> columns,
	    final DeletedFlag deletedFlag, final int recordLength) {
	this.header = header;
	this.recordLength = recordLength;
	this.deletedFlag = deletedFlag;
	this.columns = new TreeSet<DataFileColumn>(
		new ColumnStartIndexComparator());
	this.columns.addAll(columns);
    }

    @Override
    public String toString() {
	return "DataFileSchema" + " [ " + "header = " + header + "; columns = "
		+ columns + "; deletedFlag = " + deletedFlag + " ] ";
    }

    @Override
    public boolean equals(final Object object) {
	if (object == this) {
	    return true;
	}
	if (!(object instanceof UrlyBirdSchema)) {
	    return false;
	}
	final UrlyBirdSchema schema = (UrlyBirdSchema) object;
	return header.equals(schema.header)
		&& this.deletedFlag == schema.deletedFlag
		&& this.columns.equals(schema.columns)
		&& this.recordLength == schema.recordLength;

    };

    @Override
    public int hashCode() {
	int result = 17;
	result = 31 * result + this.deletedFlag.hashCode();
	result = 31 * result + this.header.hashCode();
	result = 31 * result + this.columns.hashCode();
	result = 31 * result + this.recordLength;
	return result;
    }

    @Override
    public int getOffset() {
	return header.getHeaderLength();
    }

    @Override
    public int getRecordLength() {
	return recordLength;
    }

    @Override
    public boolean isValidRecord(final byte[] buffer) {

	return buffer.length == recordLength
		&& buffer[deletedFlag.getIndex()] != deletedFlag
			.getIdentifier();
    }

    @Override
    public Iterator<DataFileColumn> iterator() {
	final TreeSet<DataFileColumn> copy = new TreeSet<DataFileColumn>(
		columns);
	return copy.iterator();
    }

    @Override
    public boolean isValidDataFile(final File file) throws IOException {

	boolean isValid = true;

	isValid &= file.exists() && file.isFile();
	isValid &= fileHasValidIdentifier(file);
	isValid &= fileContainsOnlyCompleteRecords(file);

	return isValid;
    }

    private boolean fileContainsOnlyCompleteRecords(final File file) {

	return (file.length() - getOffset()) % getRecordLength() == 0;
    }

    private boolean fileHasValidIdentifier(final File file) throws IOException {

	boolean hasValidIdentifier = false;
	final DataInputStream reader = new DataInputStream(new FileInputStream(
		file));

	try {
	    final int fileIdentifier = reader.readInt();
	    hasValidIdentifier = fileIdentifier == header.getKey();

	} finally {
	    reader.close();
	}

	return hasValidIdentifier;
    }
}
