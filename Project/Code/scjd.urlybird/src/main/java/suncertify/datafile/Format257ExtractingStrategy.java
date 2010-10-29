package suncertify.datafile;

import java.io.IOException;
import java.util.ArrayList;

public class Format257ExtractingStrategy extends SchemaExctractionStrategy {

    private final static int SUPPORTED_FORMAT = 257;

    @Override
    public DataFileSchema doExtractSchemaWithReader(
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

	return new DataFileSchema(SUPPORTED_FORMAT, reader.getCount(),
		recordLength + deletedColumn.size(), columns);
    }

    @Override
    int getSupportedFormatIdentifier() {
	return SUPPORTED_FORMAT;
    }
}
