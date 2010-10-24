package suncertify.datafile;

import java.io.IOException;
import java.util.ArrayList;

import suncertify.db.InvalidDataFileFormatException;

class DataFileSchemaBuilder {

    private static final int supportedFormat = 257;

    private static final DataFileSchemaBuilder INSTANCE = new DataFileSchemaBuilder();

    static final DataFileSchemaBuilder instance() {
	return INSTANCE;
    }

    private DataFileSchemaBuilder() {
	super();
    }

    private DataFileSchema createSchema(final Utf8ByteCountingReader reader)
	    throws IOException, InvalidDataFileFormatException {

	final int dataFileFormatIdentifier = reader.readInt();
	if (supportedFormat != dataFileFormatIdentifier) {
	    throw new InvalidDataFileFormatException(
		    "Wrong Data Source Identifier: " + dataFileFormatIdentifier
			    + " Ð Supported identifier " + supportedFormat);
	}
	final int recordLength = reader.readInt();
	final int numberOfColumns = reader.readShort();

	final ArrayList<DataFileColumn> columns = new ArrayList<DataFileColumn>();
	final DataFileColumn deletedColumn = new DataFileColumn("deleted", 0, 1);
	int startPositionNextColumn = deletedColumn.size();
	columns.add(deletedColumn);
	for (int i = 1; i <= numberOfColumns; i++) {

	    final int columnNameLengthInByte = reader.readShort();
	    final String columnName = reader.readString(columnNameLengthInByte);

	    final int columnSize = reader.readShort();
	    final DataFileColumn newColumn = new DataFileColumn(columnName,
		    startPositionNextColumn, columnSize);
	    columns.add(newColumn);
	    startPositionNextColumn += newColumn.size();
	}

	return new DataFileSchema(supportedFormat, reader.getCount(),
		recordLength + deletedColumn.size(), columns);
    }

    /**
     * Creates a new DataFileSchema object.
     * 
     * @param input
     *            the input
     * @return the data schema
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws InvalidDataFileFormatException
     *             the unsupported data source exception
     */
    DataFileSchema buildSchemaForDataFile(final Utf8ByteCountingReader reader)
	    throws IOException, InvalidDataFileFormatException {

	reader.open();
	DataFileSchema schema = null;
	try {
	    schema = createSchema(reader);
	} finally {
	    reader.closeQuietly();
	}

	return schema;
    }

}
