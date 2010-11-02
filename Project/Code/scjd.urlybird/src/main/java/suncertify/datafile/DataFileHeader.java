package suncertify.datafile;

final class DataFileHeader {
    private final int key;
    private final int headerLength;
    private final int recordLength;

    DataFileHeader(final int key, final int headerLength, final int recordLength) {
	this.key = key;
	this.headerLength = headerLength;
	this.recordLength = recordLength;
    }

    @Override
    public boolean equals(final Object object) {
	if (object == this) {
	    return true;
	}
	if (!(object instanceof DataFileHeader)) {
	    return false;
	}
	final DataFileHeader header = (DataFileHeader) object;
	return this.key == header.key
		&& this.headerLength == header.headerLength
		&& this.recordLength == header.recordLength;
    }

    @Override
    public String toString() {
	return "DataFileHeader" + " [ " + "key = " + key + "; headerLength = "
		+ headerLength + "; recordLength = " + recordLength + " ] ";
    }

    @Override
    public int hashCode() {
	int result = 17;
	result = 31 * result + this.key;
	result = 31 * result + this.headerLength;
	result = 31 * result + this.recordLength;
	return result;
    }

    int getKey() {
	return key;
    }

    int getHeaderLength() {
	return headerLength;
    }

    int getRecordLength() {
	return recordLength;
    }
}