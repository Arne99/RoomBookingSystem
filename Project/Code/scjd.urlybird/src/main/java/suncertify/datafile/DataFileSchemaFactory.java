package suncertify.datafile;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 */
final class DataFileSchemaFactory {

    private static final String READ_MODE = "r";

    private static final int SUPPORTED_FORMAT = 257;

    private static final int START_INDEX = 0;

    private static final int DELETED_FLAG_INDEX = START_INDEX;

    private static final byte DELETED_FLAG_VALUE = 1;

    private static final byte DELETED_FLAG_LENGTH = 1;

    private static final DeletedFlag DELETED_FLAG = new DeletedFlag(
	    DELETED_FLAG_INDEX, DELETED_FLAG_VALUE);

    private final BytesToStringDecoder decoder;

    DataFileSchemaFactory(final BytesToStringDecoder decoder) {
	super();
	this.decoder = decoder;
    }

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
    DataFileMetadata createSchemaForDataFile(final File file) throws IOException,
	    UnsupportedDataFileFormatException {

	final RandomAccessFile reader = new RandomAccessFile(file, READ_MODE);
	DataFileMetadata schema = null;
	try {
	    schema = extractSchemaWithReader(reader);
	} finally {
	    reader.close();
	}

	return schema;
    }

    private DataFileMetadata extractSchemaWithReader(final RandomAccessFile reader)
	    throws IOException, UnsupportedDataFileFormatException {

	final int dataFileFormatIdentifier = reader.readInt();
	if (SUPPORTED_FORMAT != dataFileFormatIdentifier) {
	    throw new UnsupportedDataFileFormatException(
		    "Wrong Data Source Identifier: " + dataFileFormatIdentifier
			    + " Ð Supported identifier " + SUPPORTED_FORMAT);
	}

	reader.readInt();
	final int numberOfColumns = reader.readShort();

	final ArrayList<DataFileColumn> columns = new ArrayList<DataFileColumn>();
	int startPositionNextColumn = DELETED_FLAG_LENGTH;
	for (int i = 0; i < numberOfColumns; i++) {

	    final int columnNameLengthInByte = reader.readShort();
	    final byte[] nameBuffer = new byte[columnNameLengthInByte];
	    reader.read(nameBuffer);
	    final String columnName = decoder.decodeBytesToString(nameBuffer);

	    final int columnSize = reader.readShort();
	    final DataFileColumn newColumn = DataFileColumn.create(columnName,
		    startPositionNextColumn, columnSize);
	    columns.add(newColumn);
	    startPositionNextColumn += newColumn.size();
	}

	return buildSchema(
		new DataFileHeader(SUPPORTED_FORMAT,
			(int) reader.getFilePointer()), columns, DELETED_FLAG);
    }

    private DataFileMetadata buildSchema(final DataFileHeader header,
	    final List<DataFileColumn> columnsInDbOrder,
	    final DeletedFlag deletedFlag) {

	if (columnsInDbOrder.isEmpty()) {
	    return new UrlyBirdSchema(header,
		    Collections.<DataFileColumn> emptyList(), deletedFlag, 0);
	}

	final int recordLength = columnsInDbOrder.get(
		columnsInDbOrder.size() - 1).getEndIndex() + 1;
	return new UrlyBirdSchema(header, columnsInDbOrder, deletedFlag,
		recordLength);
    }

}
