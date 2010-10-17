package suncertify.datafile;

import java.io.DataInput;
import java.io.IOException;

import suncertify.db.UnsupportedDataSourceException;

public class DataFileFormatFactory {

    public static DataFileFormatFactory getInstance(
	    final int supportedIdentifier, final int headerLengthInBytes) {
	return new DataFileFormatFactory(supportedIdentifier,
		headerLengthInBytes);
    }

    private final int supportedIdentifier;
    private final int headerLength;

    public DataFileFormatFactory(final int supportedIdentifier,
	    final int headerLengthInBytes) {
	this.supportedIdentifier = supportedIdentifier;
	this.headerLength = headerLengthInBytes;
    }

    public DataFileFormat createFormat(final DataInput input)
	    throws IOException, UnsupportedDataSourceException {

	final int identifier = input.readInt();
	if (supportedIdentifier != identifier) {
	    throw new UnsupportedDataSourceException();
	}

	final int recordLength = input.readInt();
	final int numberOfColumns = input.readShort();
	return new SimpleDataFileFormat(identifier, recordLength,
		numberOfColumns, headerLength);
    }

}
