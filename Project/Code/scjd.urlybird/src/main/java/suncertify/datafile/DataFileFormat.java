package suncertify.datafile;

// TODO: Auto-generated Javadoc
/**
 * The Class SimpleDataFileFormat.
 */
public class DataFileFormat {

    /** The identifier. */
    private final int identifier;

    /** The number of columns. */
    private final int numberOfColumns;

    /** The header length. */
    private final int headerLength;

    /** The record length. */
    private final int recordLength;

    /**
     * Instantiates a new simple data file format.
     * 
     * @param identifier
     *            the identifier
     * @param recordLength
     *            the record length
     * @param numberOfColumns
     *            the number of columns
     * @param headerLength
     *            the header length
     */
    public DataFileFormat(final int identifier, final int recordLength,
	    final int numberOfColumns, final int headerLength) {
	this.identifier = identifier;
	this.recordLength = recordLength;
	this.numberOfColumns = numberOfColumns;
	this.headerLength = headerLength;
    }

    /*
     * (non-Javadoc)
     * 
     * @see suncertify.datafile.DataFileFormat#getHeaderLengthInBytes()
     */
    public int getHeaderLengthInBytes() {
	return headerLength;
    }

    /*
     * (non-Javadoc)
     * 
     * @see suncertify.datafile.DataFileFormat#getNumberOfColumns()
     */
    public int getNumberOfColumns() {
	return numberOfColumns;
    }

    /*
     * (non-Javadoc)
     * 
     * @see suncertify.datafile.DataFileFormat#getRecordLength()
     */
    public int getRecordLength() {
	return recordLength;
    }

    /*
     * (non-Javadoc)
     * 
     * @see suncertify.datafile.DataFileFormat#supports(int)
     */
    public boolean supports(final int identifier) {
	return this.identifier == identifier;
    }

}
