package suncertify.datafile;

import java.io.DataOutput;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import suncertify.db.DatabaseHandler;
import suncertify.db.Record;
import suncertify.db.RecordNotFoundException;

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
    public List<String> readRecord(final int index) throws IOException,
	    RecordNotFoundException {

	reader.openStream();
	final ArrayList<String> values = new ArrayList<String>();

	try {
	    if (reader.availableBytes() < schema.getOffset()
		    + (index + 1 * schema.getRecordLength())) {
		throw new RecordNotFoundException(
			"The datafile does not contain any record at the index: "
				+ index);
	    }

	    reader.skipFully(schema.getOffset()
		    + (index * schema.getRecordLength()));

	    final String data = reader.readString(schema.getRecordLength());

	    if (data.isEmpty()) {

		throw new RecordNotFoundException(
			"The datafile does not contain any record at the index: "
				+ index);
	    }

	    final String validityIdentifier = data.substring(
		    schema.getDeletedFlagIndex(),
		    schema.getDeletedFlagIndex() + 1);
	    final boolean valid = "0".equals(validityIdentifier);
	    if (!valid) {
		throw new RecordNotFoundException(
			"The datafile does not contain any record at the index: "
				+ index);
	    }

	    for (final DataFileColumn column : schema
		    .getColumnsInDatabaseOrder()) {
		final int startPosition = column.getStartIndex();
		final int endPosition = column.getEndIndex();
		final String value = data.substring(startPosition,
			endPosition + 1).trim();
		values.add(value);
	    }
	} catch (final EOFException eof) {
	    throw new RecordNotFoundException(
		    "The datafile does not contain any record at the index: "
			    + index);
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
