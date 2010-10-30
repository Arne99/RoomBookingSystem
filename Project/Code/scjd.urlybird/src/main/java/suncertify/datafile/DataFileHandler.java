package suncertify.datafile;

import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import suncertify.db.DatabaseHandler;
import suncertify.db.Record;

/**
 * The Class DataFileAccessHandler.
 */
class DataFileHandler implements DatabaseHandler {

    private final ByteFileReader reader;
    private final DataOutput writer;
    private final DataFileSchema schema;

    /**
     * Instantiates a new data file access handler.
     * 
     * @param schema
     *            the schema
     */
    DataFileHandler(final DataFileSchema schema, final ByteFileReader reader,
	    final DataOutput writer) {
	this.schema = schema;
	this.reader = reader;
	this.writer = writer;
    }

    @Override
    public void deleteRecord(final int index) throws IOException {

	throw new UnsupportedOperationException("not implemented!");
    }

    @Override
    public List<Record> findMatchingRecords(final Record queryRecord)
	    throws IOException {

	throw new UnsupportedOperationException("not implemented!");
    }

    @Override
    public DataFileRecord readRecord(final int index) throws IOException {

	reader.skipBytes(schema.getIndexValueColumns());
	reader.skipBytes(index * schema.getRecordLength());

	final String data = reader.readString(schema.getRecordLength());

	final HashMap<DataFileColumn, String> recordValues = new HashMap<DataFileColumn, String>();
	for (final DataFileColumn column : schema.getColumns()) {
	    final int startPosition = column.getStartPosition();
	    final int endPosition = column.getEndPosition();
	    final String valueForColumn = data.substring(startPosition,
		    endPosition);
	    recordValues.put(column, valueForColumn);
	}

	return new DataFileRecord(recordValues, false);
    }

    @Override
    public void writeRecord(final Record record) throws IOException {

	throw new UnsupportedOperationException("not implemented!");
    }
}
