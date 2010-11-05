package suncertify.datafile;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import suncertify.db.RecordNotFoundException;

import com.google.common.collect.Lists;

/**
 * Test for the Class {@link DataFileHandler}.
 */
public final class DataFileHandlerTest {

    /** The Constant STRING_OF_SIZE_20. */
    private static final String VALID_RECORD_OF_SIZE_20 = "01234567890123456789";

    private static final int ENAOUGH_BYTES_AVAILABLE = 50000;

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

    /** The handler. */
    private DataFileHandler handler;

    /** The reader. */
    private ByteFileReader reader;

    /** The Constant COLUMN_WITH_FROM_0_TO_19. */
    private static final DataFileColumn COLUMN_FROM_0_TO_19 = DataFileColumn
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
     * @throws RecordNotFoundException
     */
    @Test
    public void shouldSkipTheHeaderLengthIfItReadsTheFirstRecord()
	    throws IOException, RecordNotFoundException {

	final int headerLength = 100;
	final DataFileSchema schema = DataFileSchema.create(new DataFileHeader(
		ANY_IDENTIFIER, headerLength), Lists
		.newArrayList(COLUMN_FROM_0_TO_19), DELETED_FLAG_INDEX_0);

	when(reader.availableBytes()).thenReturn(
		headerLength + VALID_RECORD_OF_SIZE_20.length());
	when(reader.readString(schema.getRecordLength())).thenReturn(
		VALID_RECORD_OF_SIZE_20);
	handler = new DataFileHandler(schema, reader, null);
	handler.readRecord(0);

	verify(reader, times(1)).skipFully(headerLength);
    }

    /**
     * Should skip the header length plus the first record length if it reads
     * the second record.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws RecordNotFoundException
     */
    @Test
    public void shouldSkipTheHeaderLengthPlusTheFirstRecordLengthIfItReadsTheSecondRecord()
	    throws IOException, RecordNotFoundException {

	final int headerLength = 100;
	final DataFileSchema schema = DataFileSchema.create(new DataFileHeader(
		ANY_IDENTIFIER, headerLength), Lists
		.newArrayList(COLUMN_FROM_0_TO_19), DELETED_FLAG_INDEX_0);

	when(reader.availableBytes()).thenReturn(ENAOUGH_BYTES_AVAILABLE);
	when(reader.readString(schema.getRecordLength())).thenReturn(
		VALID_RECORD_OF_SIZE_20);

	handler = new DataFileHandler(schema, reader, null);
	handler.readRecord(1);

	verify(reader, times(1)).skipFully(
		headerLength + schema.getRecordLength());
    }

    /**
     * Should skip the header length plus the length of n records if it reads
     * the n plus one record.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws RecordNotFoundException
     */
    @Test
    public void shouldSkipTheHeaderLengthPlusTheLengthOfNRecordsIfItReadsTheNPlusOneRecord()
	    throws IOException, RecordNotFoundException {

	final int headerLength = 80;
	final int fourtyOneRecordIndex = 40;

	final DataFileSchema schema = DataFileSchema.create(new DataFileHeader(
		ANY_IDENTIFIER, headerLength), Lists
		.newArrayList(COLUMN_FROM_0_TO_19), DELETED_FLAG_INDEX_0);

	when(reader.availableBytes()).thenReturn(ENAOUGH_BYTES_AVAILABLE);
	when(reader.readString(schema.getRecordLength())).thenReturn(
		VALID_RECORD_OF_SIZE_20);

	handler = new DataFileHandler(schema, reader, null);
	handler.readRecord(fourtyOneRecordIndex);

	verify(reader, times(1)).skipFully(
		headerLength
			+ (schema.getRecordLength() * fourtyOneRecordIndex));
    }

    /**
     * Should open the reader before it calls the first read operation.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws RecordNotFoundException
     */
    @Test
    public void shouldOpenTheReaderBeforeItCallsTheFirstReadOperation()
	    throws IOException, RecordNotFoundException {

	final DataFileSchema schema = DataFileSchema.create(new DataFileHeader(
		ANY_IDENTIFIER, ANY_OFFSET), Lists
		.newArrayList(COLUMN_FROM_0_TO_19), DELETED_FLAG_INDEX_0);

	when(reader.availableBytes()).thenReturn(ENAOUGH_BYTES_AVAILABLE);
	when(reader.readString(schema.getRecordLength())).thenReturn(
		VALID_RECORD_OF_SIZE_20);

	handler = new DataFileHandler(schema, reader, null);
	handler.readRecord(0);

	verify(reader, times(1)).openStream();
	verify(reader, atLeastOnce()).skipFully(anyInt());
    }

    /**
     * Should always close the reader at the end.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws RecordNotFoundException
     */
    @Test
    public void shouldAlwaysCloseTheReaderAtTheEnd() throws IOException,
	    RecordNotFoundException {

	final DataFileSchema schema = DataFileSchema
		.create(new DataFileHeader(ANY_IDENTIFIER, ANY_OFFSET),
			NONE_COLUMNS, DELETED_FLAG_INDEX_0);

	handler = new DataFileHandler(schema, reader, null);
	doThrow(new IOException()).when(reader).skipFully(anyInt());

	try {
	    handler.readRecord(0);
	} catch (final IOException e) {
	    System.out.println();
	}
	verify(reader, atLeastOnce()).skipFully(anyInt());
	verify(reader, times(1)).closeStream();
    }

    /**
     * Should read a data file record and convert it to an list of strings in
     * database ordering.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws RecordNotFoundException
     */
    @Test
    public void shouldReadADataFileRecordAndConvertItToAnListOfStringsInDatabaseOrdering()
	    throws IOException, RecordNotFoundException {

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

	when(reader.availableBytes()).thenReturn(ENAOUGH_BYTES_AVAILABLE);
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
     * @throws RecordNotFoundException
     */
    @Test(expected = RecordNotFoundException.class)
    public void shouldThrowARecordNotFoundExceptionIfTheRecordToReadIsNotValid()
	    throws IOException, RecordNotFoundException {

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
	handler.readRecord(0);
    }

    @Test(expected = RecordNotFoundException.class)
    public void shouldThrowARecordNotFoundExceptionIfTheIndexToReadIsBiggerThanTheNumberOfAvailableRecords()
	    throws IOException, RecordNotFoundException {

	final int firstColumnSize = 46;
	final int headerLength = 200;

	final DataFileColumn singleColumn = DataFileColumn.create("ID", 1,
		firstColumnSize);
	final DataFileSchema schema = DataFileSchema.create(new DataFileHeader(
		ANY_IDENTIFIER, headerLength),
		Lists.newArrayList(singleColumn), 0);

	when(reader.availableBytes()).thenReturn(
		firstColumnSize + headerLength + 1);

	handler = new DataFileHandler(schema, reader, null);
	handler.readRecord(1);
    }

    @Test
    public void shouldNotThrowARecordNotFoundExceptionIfTheIndexToReadIsEqualThanTheNumberOfAvailableRecords()
	    throws IOException, RecordNotFoundException {

	final int headerLength = 200;

	final DataFileColumn singleColumn = COLUMN_FROM_0_TO_19;
	final DataFileSchema schema = DataFileSchema.create(new DataFileHeader(
		ANY_IDENTIFIER, headerLength),
		Lists.newArrayList(singleColumn), 0);

	final int availableBytes = COLUMN_FROM_0_TO_19.size() + headerLength
		+ 1;
	when(reader.availableBytes()).thenReturn(availableBytes);
	when(reader.readString(schema.getRecordLength())).thenReturn(
		VALID_RECORD_OF_SIZE_20);

	handler = new DataFileHandler(schema, reader, null);

	handler.readRecord(0);
    }

    @Test(expected = RecordNotFoundException.class)
    public void shouldThrowARecordNotFoundExceptionIfThereIsNoRecordAtTheSpezifiedIndex()
	    throws IOException, RecordNotFoundException {

	final String firstColumnName = "ID";
	final int firstColumnSize = 5;

	final DataFileColumn firstColumn = DataFileColumn.create(
		firstColumnName, 1, firstColumnSize);
	final DataFileSchema schema = DataFileSchema.create(new DataFileHeader(
		ANY_IDENTIFIER, 0), Lists.newArrayList(firstColumn), 0);

	when(reader.readString(schema.getRecordLength())).thenReturn("");

	handler = new DataFileHandler(schema, reader, null);
	handler.readRecord(0);
    }

    @Test(expected = RecordNotFoundException.class)
    public void shouldThrowARecordNotFoundExceptionIfTheSpezifiedIndexIsGreaterThanTheNumberOfAvailableRecords()
	    throws IOException, RecordNotFoundException {

	final String firstColumnName = "ID";
	final int firstColumnSize = 5;

	final DataFileColumn firstColumn = DataFileColumn.create(
		firstColumnName, 1, firstColumnSize);
	final DataFileSchema schema = DataFileSchema.create(new DataFileHeader(
		ANY_IDENTIFIER, 0), Lists.newArrayList(firstColumn), 0);

	when(reader.readString(schema.getRecordLength())).thenThrow(
		new EOFException());

	handler = new DataFileHandler(schema, reader, null);
	handler.readRecord(0);
    }

    /**
     * Should trim the return values.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws RecordNotFoundException
     */
    @Test
    public void shouldTrimTheReturnValues() throws IOException,
	    RecordNotFoundException {

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

	when(reader.availableBytes()).thenReturn(ENAOUGH_BYTES_AVAILABLE);
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
