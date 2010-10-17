package suncertify.datafile;

public class SimpleDataFileFormat implements DataFileFormat {

    private final int identifier;
    private final int numberOfColumns;
    private final int headerLength;
    private final int recordLength;

    public SimpleDataFileFormat(final int identifier, final int recordLength,
	    final int numberOfColumns, final int headerLength) {
	this.identifier = identifier;
	this.recordLength = recordLength;
	this.numberOfColumns = numberOfColumns;
	this.headerLength = headerLength;
    }

    @Override
    public int getHeaderLengthInBytes() {
	return headerLength;
    }

    @Override
    public int getNumberOfColumns() {
	return numberOfColumns;
    }

    @Override
    public int getRecordLength() {
	return recordLength;
    }

    @Override
    public boolean supports(final int identifier) {
	return this.identifier == identifier;
    }

}
