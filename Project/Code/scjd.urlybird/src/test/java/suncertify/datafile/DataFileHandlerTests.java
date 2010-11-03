package suncertify.datafile;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * Test for the Class {@link DataFileHandler}.
 */
public final class DataFileHandlerTests {

    /** The Constant STRING_OF_SIZE_20. */
    private static final String STRING_OF_SIZE_20 = "12345678901234567890";

    /** The Constant DELETED. */
    private static final byte DELETED = 1;

    /** The Constant VALID. */
    private static final byte VALID = (byte) 0;

    /** The Constant ANY_IDENTIFIER. */
    private static final int ANY_IDENTIFIER = 0;

    /** The Constant NONE_COLUMNS. */
    private static final ArrayList<DataFileColumn> NONE_COLUMNS = new ArrayList<DataFileColumn>();

    /** The Constant DELETED_FLAG_INDEX_0. */
    private static final int DELETED_FLAG_INDEX_0 = 0;

    /** The Constant ANY_OFFSET. */
    private static final int ANY_OFFSET = 0;

    /** The Constant RECORD_LENGTH_20. */
    private static final int RECORD_LENGTH_20 = 20;

    /** The handler. */
    private DataFileHandler handler;

    /** The reader. */
    private ByteFileReader reader;

    /** The Constant COLUMN_WITH_FROM_0_TO_19. */
    private static final DataFileColumn COLUMN_WITH_FROM_0_TO_19 = DataFileColumn
	    .create("Name", 0, 20);

    /**
     * Sets the up.
     */
    @Before
    public void setUp() {
	reader = mock(ByteFileReader.class);
    }

    /**
     * Should skip the header length if it reads the first record.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void shouldSkipTheHeaderLengthIfItReadsTheFirstRecord()
	    throws IOException {

	final int headerLength = 100;
	final DataFileSchema schema = DataFileSchema.create(new DataFileHeader(
		ANY_IDENTIFIER, headerLength), Lists
		.newArrayList(COLUMN_WITH_FROM_0_TO_19), DELETED_FLAG_INDEX_0);

	when(reader.readString(schema.getRecordLength())).thenReturn(
		STRING_OF_SIZE_20);
	handler = new DataFileHandler(schema, reader, null);
	handler.readRecord(0);

	verify(reader, times(1)).skipBytes(headerLength);
    }

    /**
     * Should skip the header length plus the first record length if it reads
     * the second record.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void shouldSkipTheHeaderLengthPlusTheFirstRecordLengthIfItReadsTheSecondRecord()
	    throws IOException {

	final int headerLength = 100;
	final DataFileSchema schema = DataFileSchema.create(new DataFileHeader(
		ANY_IDENTIFIER, headerLength), Lists
		.newArrayList(COLUMN_WITH_FROM_0_TO_19), DELETED_FLAG_INDEX_0);

	when(reader.readString(schema.getRecordLength())).thenReturn(
		STRING_OF_SIZE_20);
	handler = new DataFileHandler(schema, reader, null);
	handler.readRecord(1);

	verify(reader, times(1)).skipBytes(
		headerLength + schema.getRecordLength());
    }

    /**
     * Should skip the header length plus the length of n records if it reads
     * the n plus one record.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void shouldSkipTheHeaderLengthPlusTheLengthOfNRecordsIfItReadsTheNPlusOneRecord()
	    throws IOException {

	final int headerLength = 80;
	final int fourtyOneRecordIndex = 40;

	final DataFileSchema schema = DataFileSchema.create(new DataFileHeader(
		ANY_IDENTIFIER, headerLength), Lists
		.newArrayList(COLUMN_WITH_FROM_0_TO_19), DELETED_FLAG_INDEX_0);

	when(reader.readString(schema.getRecordLength())).thenReturn(
		STRING_OF_SIZE_20);
	handler = new DataFileHandler(schema, reader, null);
	handler.readRecord(fourtyOneRecordIndex);

	verify(reader, times(1)).skipBytes(
		headerLength
			+ (schema.getRecordLength() * fourtyOneRecordIndex));
    }

    /**
     * Should open the reader before it calls the first read operation.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void shouldOpenTheReaderBeforeItCallsTheFirstReadOperation()
	    throws IOException {

	final DataFileSchema schema = DataFileSchema.create(new DataFileHeader(
		ANY_IDENTIFIER, ANY_OFFSET), Lists
		.newArrayList(COLUMN_WITH_FROM_0_TO_19), DELETED_FLAG_INDEX_0);

	when(reader.readString(schema.getRecordLength())).thenReturn(
		STRING_OF_SIZE_20);
	handler = new DataFileHandler(schema, reader, null);
	handler.readRecord(0);

	verify(reader, times(1)).openStream();
	verify(reader, atLeastOnce()).skipBytes(anyInt());
    }

    /**
     * Should always close the reader at the end.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void shouldAlwaysCloseTheReaderAtTheEnd() throws IOException {

	final DataFileSchema schema = DataFileSchema
		.create(new DataFileHeader(ANY_IDENTIFIER, ANY_OFFSET),
			NONE_COLUMNS, DELETED_FLAG_INDEX_0);

	handler = new DataFileHandler(schema, reader, null);
	doThrow(new IOException()).when(reader).skipBytes(anyInt());

	try {
	    handler.readRecord(0);
	} catch (final IOException e) {
	    System.out.println();
	}
	verify(reader, atLeastOnce()).skipBytes(anyInt());
	verify(reader, times(1)).closeStream();
    }

    /**
     * Should read a data file record and convert it to an list of strings in
     * database ordering.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void shouldReadADataFileRecordAndConvertItToAnListOfStringsInDatabaseOrdering()
	    throws IOException {

	final String firstColumnName = "ID";
	final String secondColumnName = "Alter";
	final String thirdColumnName = "Name";
	final int firstColumnSize = 5;
	final int secondColumnSize = 2;
	final int thirdColumnSize = 11;

	final DataFileColumn firstColumn = DataFileColumn.create(
		firstColumnName, 1, firstColumnSize);
	final DataFileColumn secondColumn = DataFileColumn.create(
		secondColumnName, 6, secondColumnSize);
	final DataFileColumn thirdColumn = DataFileColumn.create(
		thirdColumnName, 8, thirdColumnSize);
	final DataFileSchema schema = DataFileSchema.create(new DataFileHeader(
		ANY_IDENTIFIER, 0), Lists.newArrayList(firstColumn,
		secondColumn, thirdColumn), 0);

	final String firstColumnValue = "12345";
	final String secondColumnValue = "53";
	final String thirdColumnValue = "Hans MŸller";

	when(reader.readString(schema.getRecordLength()))
		.thenReturn(
			VALID + firstColumnValue + secondColumnValue
				+ thirdColumnValue);

	handler = new DataFileHandler(schema, reader, null);
	final List<String> record = handler.readRecord(0);

	final List<String> expectedRecord = Lists.newArrayList(
		firstColumnValue, secondColumnValue, thirdColumnValue);

	assertEquals(expectedRecord, record);
    }

    /**
     * Should read and return only valid records.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void shouldReadAndReturnOnlyValidRecords() throws IOException {

	final String firstColumnName = "ID";
	final int firstColumnSize = 5;

	final DataFileColumn firstColumn = DataFileColumn.create(
		firstColumnName, 1, firstColumnSize);
	final DataFileSchema schema = DataFileSchema.create(new DataFileHeader(
		ANY_IDENTIFIER, 0), Lists.newArrayList(firstColumn), 0);

	final String firstColumnValue = "12345";

	when(reader.readString(schema.getRecordLength())).thenReturn(
		DELETED + firstColumnValue);

	handler = new DataFileHandler(schema, reader, null);
	final List<String> record = handler.readRecord(0);

	final List<String> expectedRecord = Lists.newArrayList();

	assertEquals(expectedRecord, record);
    }

    /**
     * Should trim the return values.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void shouldTrimTheReturnValues() throws IOException {

	final String firstColumnName = "ID";
	final String secondColumnName = "Alter";
	final String thirdColumnName = "Name";
	final int firstColumnSize = 10;
	final int secondColumnSize = 10;
	final int thirdColumnSize = 20;

	final DataFileColumn firstColumn = DataFileColumn.create(
		firstColumnName, 1, firstColumnSize);
	final DataFileColumn secondColumn = DataFileColumn.create(
		secondColumnName, 11, secondColumnSize);
	final DataFileColumn thirdColumn = DataFileColumn.create(
		thirdColumnName, 21, thirdColumnSize);
	final DataFileSchema schema = DataFileSchema.create(new DataFileHeader(
		ANY_IDENTIFIER, 0), Lists.newArrayList(firstColumn,
		secondColumn, thirdColumn), 0);

	final String firstColumnValue = "12345";
	final String secondColumnValue = "53";
	final String thirdColumnValue = "Hans MŸller";

	when(reader.readString(schema.getRecordLength())).thenReturn(
		VALID + firstColumnValue + "     " + secondColumnValue
			+ "        " + thirdColumnValue + "         ");

	handler = new DataFileHandler(schema, reader, null);
	final List<String> record = handler.readRecord(0);

	final List<String> expectedRecord = Lists.newArrayList(
		firstColumnValue, secondColumnValue, thirdColumnValue);

	assertEquals(expectedRecord, record);
    }
}
