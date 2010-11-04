package suncertify.datafile;

import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
    public List<String> readRecord(final int index) throws IOException {

	reader.openStream();
	final ArrayList<String> values = new ArrayList<String>();

	try {
	    reader.skipBytes(schema.getOffset()
		    + (index * schema.getRecordLength()));

	    final String data = reader.readString(schema.getRecordLength());
	    final String validityIdentifier = data.substring(
		    schema.getDeletedFlagIndex(),
		    schema.getDeletedFlagIndex() + 1);
	    final boolean valid = "0".equals(validityIdentifier);
	    if (!valid) {
		return Collections.emptyList();
	    }

	    for (final DataFileColumn column : schema
		    .getColumnsInDatabaseOrder()) {
		final int startPosition = column.getStartIndex();
		final int endPosition = column.getEndIndex();
		final String value = data.substring(startPosition,
			endPosition + 1).trim();
		values.add(value);
	    }
	} finally {
	    reader.closeStream();
	}
	return Collections.unmodifiableList(values);
    }

    @Override
    public void writeRecord(final Record record) throws IOException {

	throw new UnsupportedOperationException("not implemented!");
    }
}
