package suncertify.datafile;

import java.io.IOException;

interface SchemaExtractionStrategy {

    DataFileSchema extractSchemaWithReader(final ByteCountingReader reader)
	    throws UnsupportedDataFileFormatException, IOException;

    int getSupportedFormatIdentifier();
}
