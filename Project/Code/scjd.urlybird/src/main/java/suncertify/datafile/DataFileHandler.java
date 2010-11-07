package suncertify.datafile;

import static suncertify.util.DesignByContract.*;

import java.io.IOException;
import java.util.List;

import suncertify.db.DatabaseHandler;
import suncertify.db.Record;
import suncertify.db.RecordNotFoundException;

/**
 * The Class DataFileAccessHandler.
 */
class DataFileHandler implements DatabaseHandler {

    private final ReadableRecordSource recordSource;

    /**
     * Instantiates a new data file access handler.
     * 
     * @param schema
     *            the schema
     */
    DataFileHandler(final ReadableRecordSource recordSource) {
	this.recordSource = recordSource;
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
    public Record readValidRecord(final int index) throws IOException,
	    RecordNotFoundException {

	checkNotNegativ(index);
	final Record record = recordSource.getRecordAtIndex(index);
	if (record.isValid()) {
	    return record;
	}

	throw new RecordNotFoundException(
		"No valid Record could be found at the index: " + index);
    }

    @Override
    public void writeRecord(final Record record) throws IOException {

	throw new UnsupportedOperationException("not implemented!");
    }
}
