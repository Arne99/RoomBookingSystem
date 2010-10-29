package suncertify.datafile;

import java.io.IOException;

class NotExistingSchemaExtractionStrategy extends SchemaExctractionStrategy {

    @Override
    public DataFileSchema doExtractSchemaWithReader(
	    final ByteCountingReader reader)
	    throws UnsupportedDataFileFormatException, IOException {

	final int identifier = reader.readInt();
	throw new UnsupportedDataFileFormatException(
		"The DataFileFormat with the Identifier: " + identifier
			+ " is not supported");
    }

    @Override
    int getSupportedFormatIdentifier() {
	throw new UnsupportedOperationException();
    }

}
