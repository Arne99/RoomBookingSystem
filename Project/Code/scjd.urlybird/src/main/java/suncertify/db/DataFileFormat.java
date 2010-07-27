package suncertify.db;

/**
 * A {@code DataFileFormat} contains the relevant Information about the file
 * format of an DataFile. The relevant informations are:
 * <ul>
 * <li>the MagicCookieValue: Identifier for one specific database format</li>
 * <li>the RecordLength: The length in bytes of one single record.</li>
 * <li>the FormatInformationBlockLegth: The length in bytes of the DataFormat
 * information block.</li>
 * </ul>
 * 
 * @author arnelandwehr
 */
public class DataFileFormat {

    /** the length of one record. */
    private final int recordLength;

    /** the number of specified DataField in the DataFile. */
    private final int numberOfFields;

    /** the length in byte of the format description block. */
    private final int dataFormatHeaderLength;

    /**
     * Constructor for a new {@code DataFileFormat}.
     * 
     * @param recordLength
     *            the length of one record.
     * @param numberOfFields
     *            the number of specified DataField in the DataFile.
     * @param fileFormatBlockLength
     *            the length in byte of the format description block.
     */
    public DataFileFormat(final int recordLength, final int numberOfFields,
	    final int fileFormatBlockLength) {
	this.recordLength = recordLength;
	this.numberOfFields = numberOfFields;
	this.dataFormatHeaderLength = fileFormatBlockLength;
    }

    /**
     * Getter for the length of one record.
     * 
     * @return the length of one record in bytes.
     */
    public final int getRecordLength() {
	return recordLength;
    }

    /**
     * Getter for the number of fields in the database file.
     * 
     * @return the number of fields.
     */
    public final int getNumberOfFields() {
	return numberOfFields;
    }

    /**
     * Getter for the length of the format description block.
     * 
     * @return the length in byte of the format description block.
     */
    public final int getFileFormatBlockLength() {
	return dataFormatHeaderLength;
    }

    @Override
    public final int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + dataFormatHeaderLength;
	result = prime * result + numberOfFields;
	result = prime * result + recordLength;
	return result;
    }

    @Override
    public final boolean equals(final Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	DataFileFormat other = (DataFileFormat) obj;
	if (dataFormatHeaderLength != other.dataFormatHeaderLength)
	    return false;
	if (numberOfFields != other.numberOfFields)
	    return false;
	if (recordLength != other.recordLength)
	    return false;
	return true;
    }

    @Override
    public final String toString() {
	return "DataFileFormat [recordLength=" + recordLength
		+ ", numberOfFields=" + numberOfFields
		+ ", fileFormatBlockLength=" + dataFormatHeaderLength + "]";
    }

}
