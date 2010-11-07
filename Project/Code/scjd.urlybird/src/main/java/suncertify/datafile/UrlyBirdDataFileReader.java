package suncertify.datafile;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;
import suncertify.db.Record;

/**
 * 
 * @author arnelandwehr
 * 
 */
final class UrlyBirdDataFileReader implements ReadableRecordSource {

    private static final String READ_MODE = "r";
    private final DataFileSchema schema;
    private final File file;
    private final RecordFactory recordFactory;

    UrlyBirdDataFileReader(final DataFileSchema schema, final File file,
	    final RecordFactory recordFactory) {
	super();
	this.recordFactory = recordFactory;
	this.schema = schema;
	this.file = file;
    }

    @Override
    public Record getRecordAtIndex(final int index) throws IOException {

	final RandomAccessFile reader = new RandomAccessFile(file, READ_MODE);

	byte[] recordBuffer = new byte[] {};
	try {
	    final int offset = schema.getOffset()
		    + (index * schema.getRecordLength()) + 1;
	    recordBuffer = new byte[schema.getRecordLength()];
	    reader.readFully(recordBuffer, offset, schema.getRecordLength());
	} finally {
	    reader.close();
	}
	return recordFactory.createRecordFrom(recordBuffer);
    }

    @Override
    public Iterator<Record> iterator() {
	return new RecordIterator(this);
    }

    @Override
    public int getIndexOfLastRecord() throws IOException {

	final RandomAccessFile reader = new RandomAccessFile(file, READ_MODE);
	int lastIndex = 0;
	try {
	    lastIndex = (int) (reader.length() - schema.getOffset())
		    / schema.getRecordLength();
	} finally {
	    reader.close();
	}
	return lastIndex;
    }

    private static class RecordIterator implements Iterator<Record> {

	private int currentIndex = 0;
	private final ReadableRecordSource reader;

	public RecordIterator(final ReadableRecordSource recordSource) {
	    super();
	    this.reader = recordSource;
	}

	@Override
	public boolean hasNext() {
	    try {
		return reader.getIndexOfLastRecord() <= currentIndex;
	    } catch (final IOException e) {
		throw new RuntimeException(e);
	    }
	}

	@Override
	public Record next() {
	    currentIndex += 1;
	    try {
		return reader.getRecordAtIndex(currentIndex);
	    } catch (final IOException e) {
		throw new RuntimeException(e);
	    }

	}

	@Override
	public void remove() {
	    throw new UnsupportedOperationException(
		    "the opperartion \"remove\" is not suppurted by this iterator!");
	}

    }

}