package suncertify.datafile;

import java.io.DataInput;
import java.io.IOException;

import suncertify.db.UnsupportedDataSourceException;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating DataFileFormat objects.
 */
public class DataFileFormatFactory {

    /**
     * Gets the single instance of DataFileFormatFactory.
     * 
     * @param supportedIdentifier
     *            the supported identifier
     * @param headerLengthInBytes
     *            the header length in bytes
     * @return single instance of DataFileFormatFactory
     */
    public static DataFileFormatFactory getInstance(
	    final int supportedIdentifier, final int headerLengthInBytes) {
	return new DataFileFormatFactory(supportedIdentifier,
		headerLengthInBytes);
    }

    /** The supported identifier. */
    private final int supportedIdentifier;

    /** The header length. */
    private final int headerLength;

    /**
     * Instantiates a new data file format factory.
     * 
     * @param supportedIdentifier
     *            the supported identifier
     * @param headerLengthInBytes
     *            the header length in bytes
     */
    public DataFileFormatFactory(final int supportedIdentifier,
	    final int headerLengthInBytes) {
	this.supportedIdentifier = supportedIdentifier;
	this.headerLength = headerLengthInBytes;
    }

    /**
     * Creates a new DataFileFormat object.
     * 
     * @param input
     *            the input
     * @return the data file format
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws UnsupportedDataSourceException
     *             the unsupported data source exception
     */
    public DataFileFormat createFormat(final DataInput input)
	    throws IOException, UnsupportedDataSourceException {

	final int identifier = input.readInt();
	if (supportedIdentifier != identifier) {
	    throw new UnsupportedDataSourceException();
	}

	final int recordLength = input.readInt();
	final int numberOfColumns = input.readShort();
	return new DataFileFormat(identifier, recordLength,
		numberOfColumns, headerLength);
    }

}
