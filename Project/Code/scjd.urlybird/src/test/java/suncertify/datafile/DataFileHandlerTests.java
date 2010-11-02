package suncertify.datafile;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

public class DataFileHandlerTests {

    private static final byte VALID = (byte) 0;
    private static final int ANY_IDENTIFIER = 0;
    private static final ArrayList<DataFileColumn> NONE_COLUMNS = new ArrayList<DataFileColumn>();
    private static final int DELETED_FLAG_INDEX = 0;
    private static final int ANY_OFFSET = 0;
    private static final int ANY_RECORD_LENGTH = 0;
    private DataFileHandler handler;
    private ByteFileReader reader;

    @Before
    public void setUp() {
	reader = mock(ByteFileReader.class);
    }

    @Test
    public void shouldSkipTheHeaderLengthIfItReadsTheFirstRecord()
	    throws IOException {

	final int headerLength = 100;
	final DataFileSchema schema = new DataFileSchema(new DataFileHeader(
		ANY_IDENTIFIER, headerLength, ANY_RECORD_LENGTH), NONE_COLUMNS,
		DELETED_FLAG_INDEX);

	handler = new DataFileHandler(schema, reader, null);
	handler.readRecord(0);

	verify(reader, times(1)).skipBytes(headerLength);
    }

    @Test
    public void shouldSkipTheHeaderLengthPlusTheFirstRecordLengthIfItReadsTheSecondRecord()
	    throws IOException {

	final int headerLength = 100;
	final int recordLength = 69;
	final DataFileSchema schema = new DataFileSchema(new DataFileHeader(
		ANY_IDENTIFIER, headerLength, recordLength), NONE_COLUMNS,
		DELETED_FLAG_INDEX);

	handler = new DataFileHandler(schema, reader, null);
	handler.readRecord(1);

	verify(reader, times(1)).skipBytes(headerLength + recordLength);
    }

    @Test
    public void shouldSkipTheHeaderLengthPlusTheLengthOf40RecordsIfItReadsThe41thRecord()
	    throws IOException {

	final int headerLength = 80;
	final int recordLength = 120;
	final int fourtyOneRecordIndex = 40;

	final DataFileSchema schema = new DataFileSchema(new DataFileHeader(
		ANY_IDENTIFIER, headerLength, recordLength), NONE_COLUMNS,
		DELETED_FLAG_INDEX);

	handler = new DataFileHandler(schema, reader, null);
	handler.readRecord(fourtyOneRecordIndex);

	verify(reader, times(1)).skipBytes(
		headerLength + (recordLength * fourtyOneRecordIndex));
    }

    @Test
    public void shouldOpenTheReaderBeforeItCallsTheFirstReadOperation()
	    throws IOException {

	final DataFileSchema schema = new DataFileSchema(new DataFileHeader(
		ANY_IDENTIFIER, ANY_OFFSET, ANY_RECORD_LENGTH), NONE_COLUMNS,
		DELETED_FLAG_INDEX);

	handler = new DataFileHandler(schema, reader, null);
	handler.readRecord(0);

	verify(reader, times(1)).openStream();
	verify(reader, atLeastOnce()).skipBytes(anyInt());
    }

    @Test
    public void shouldAlwaysCloseTheReaderAtTheEnd() throws IOException {

	final DataFileSchema schema = new DataFileSchema(new DataFileHeader(
		ANY_IDENTIFIER, ANY_OFFSET, ANY_RECORD_LENGTH), NONE_COLUMNS,
		DELETED_FLAG_INDEX);

	handler = new DataFileHandler(schema, reader, null);
	doThrow(new IOException()).when(reader).skipBytes(anyInt());

	try {
	    handler.readRecord(0);
	} catch (final IOException e) {
	    // expected
	}
	verify(reader, atLeastOnce()).skipBytes(anyInt());
	verify(reader, times(1)).closeStream();
    }

    public void shouldReadTheStringThatRepresentsTheFirstRecordAndConvertItToADataFileRecord()
	    throws IOException {

	final DataFileSchema schema = new DataFileSchema(new DataFileHeader(
		ANY_IDENTIFIER, 0, 20),
		Collections.singleton(new DataFileColumn("Name", 0, 20)), 0);

	when(reader.readByte()).thenReturn(VALID);
	when(reader.readString(20)).thenReturn("test");

	handler = new DataFileHandler(schema, reader, null);
	final DataFileRecord record = handler.readRecord(0);

	final DataFileRecord expectedRecord = new DataFileRecord(columnValues);
    }
}
