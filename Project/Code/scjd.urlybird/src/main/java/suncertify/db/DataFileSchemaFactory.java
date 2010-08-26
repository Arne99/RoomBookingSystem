package suncertify.db;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;

public class DataFileSchemaFactory {

    public static DataFileSchemaFactory getInstance(
	    final DataFileFormat supportedFormat) {
	return new DataFileSchemaFactory(supportedFormat);
    }

    private final DataFileFormat supportedFormat;

    private DataFileSchemaFactory(final DataFileFormat dataFormat) {
	super();
	this.supportedFormat = dataFormat;
    }

    public DataSchema createSchema(final DataInput input) throws IOException,
	    UnsupportedDataSourceException {

	if (!supportedFormat.supports(input.readInt())) {
	    throw new UnsupportedDataSourceException();
	}
	input.skipBytes(supportedFormat.getHeaderLengthInBytes());

	final ArrayList<ColumnMetaData> columns = new ArrayList<ColumnMetaData>();
	for (int i = 1; i <= supportedFormat.getNumberOfColumns(); i++) {

	    final int columnNameLengthInByte = input.readShort();
	    final StringBuilder sb = new StringBuilder();

	    for (int j = 1; j <= columnNameLengthInByte; j++) {
		sb.append((char) input.readByte());
	    }

	    final int columnSize = input.readShort();
	    columns.add(new RawColumnMetaData(sb.toString(), columnSize));
	}

	return new DataFileSchema(columns, supportedFormat.getRecordLength());
    }
}
