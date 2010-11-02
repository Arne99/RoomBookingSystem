package suncertify.datafile;

import java.io.IOException;
import java.util.ArrayList;

import suncertify.db.InvalidDataFileFormatException;

/**
 */
final class DataFileSchemaFactory {

    private static final DataFileSchemaFactory INSTANCE = new DataFileSchemaFactory();

    static DataFileSchemaFactory instance() {
	return INSTANCE;
    }

    private static final int SUPPORTED_FORMAT = 257;

    private static final int START_INDEX = 0;

    private static final int DELETED_FLAG_INDEX = START_INDEX;

    private static final int DELETED_FLAG_LENGTH = 1;

    /**
     * Creates a new DataFileSchema object.
     * 
     * @param input
     *            the input
     * @return the data schema
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws UnsupportedDataFileFormatException
     * @throws InvalidDataFileFormatException
     *             the unsupported data source exception
     */
    DataFileSchema createSchemaForDataFile(final ByteCountingReader reader)
	    throws IOException, UnsupportedDataFileFormatException {

	reader.openStream();
	DataFileSchema schema = null;
	try {
	    schema = extractSchemaWithReader(reader);
	} finally {
	    reader.closeStream();
	}

	return schema;
    }

    private DataFileSchema extractSchemaWithReader(
	    final ByteCountingReader reader) throws IOException,
	    UnsupportedDataFileFormatException {

	final int dataFileFormatIdentifier = reader.readInt();
	if (SUPPORTED_FORMAT != dataFileFormatIdentifier) {
	    throw new UnsupportedDataFileFormatException(
		    "Wrong Data Source Identifier: " + dataFileFormatIdentifier
			    + " Ð Supported identifier " + SUPPORTED_FORMAT);
	}
	final int recordLength = reader.readInt();
	final int numberOfColumns = reader.readShort();

	final ArrayList<DataFileColumn> columns = new ArrayList<DataFileColumn>();
	int startPositionNextColumn = DELETED_FLAG_LENGTH;
	for (int i = 0; i < numberOfColumns; i++) {

	    final int columnNameLengthInByte = reader.readShort();
	    final String columnName = reader.readString(columnNameLengthInByte);

	    final int columnSize = reader.readShort();
	    final DataFileColumn newColumn = new DataFileColumn(columnName,
		    startPositionNextColumn, columnSize);
	    columns.add(newColumn);
	    startPositionNextColumn += newColumn.size();
	}

	return new DataFileSchema(new DataFileHeader(SUPPORTED_FORMAT, reader.getCount(), recordLength), columns,
		DELETED_FLAG_INDEX);
    }

}
