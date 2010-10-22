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
    public DataFileSchema buildSchemaForDataFile(final DataFileReader input)
	    throws IOException, InvalidDataFileFormatException {

	final int dataFileFormatidentifier = input.readInt();
	if (supportedFormat != dataFileFormatidentifier) {
	    throw new InvalidDataFileFormatException(
		    "Wrong Data Source Identifier: " + dataFileFormatidentifier
			    + " Ð Supported identifier " + supportedFormat);
	}
	final int recordLength = input.readInt();
	final int numberOfColumns = input.readShort();

	final ArrayList<DataFileColumn> columns = new ArrayList<DataFileColumn>();
	final DataFileColumn deletedColumn = new DataFileColumn("deleted", 0, 1);
	int startPositionNextColumn = deletedColumn.size();
	columns.add(deletedColumn);
	for (int i = 1; i <= numberOfColumns; i++) {

	    final int columnNameLengthInByte = input.readShort();
	    final String columnName = input.readString(columnNameLengthInByte);

	    final int columnSize = input.readShort();
	    final DataFileColumn newColumn = new DataFileColumn(columnName,
		    startPositionNextColumn, columnSize);
	    columns.add(newColumn);
	    startPositionNextColumn += newColumn.size();
	}

	return new DataFileSchema(supportedFormat, input.getReadBytes(),
		recordLength + deletedColumn.size(), columns);
    }

}
