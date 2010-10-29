package suncertify.datafile;

import java.io.IOException;

abstract class SchemaExctractionStrategy {

    final DataFileSchema extractSchemaWithReader(final ByteCountingReader reader)
	    throws UnsupportedDataFileFormatException, IOException {

	reader.openStream();
	DataFileSchema schema = null;
	try {
	    schema = doExtractSchemaWithReader(reader);
	} finally {
	    reader.closeStream();
	}

	return schema;
    }

    abstract DataFileSchema doExtractSchemaWithReader(ByteCountingReader reader)
	    throws IOException, UnsupportedDataFileFormatException;

    abstract int getSupportedFormatIdentifier();
}
