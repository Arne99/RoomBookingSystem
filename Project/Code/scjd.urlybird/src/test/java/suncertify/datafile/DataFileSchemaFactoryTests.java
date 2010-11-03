package suncertify.datafile;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.mockito.InOrder;

/**
 * The Tests for the Class DataFileSchemaFactory.
 */
public final class DataFileSchemaFactoryTests {

    /** The Constant SUPPORTED_IDENTIFIER. */
    private static final int SUPPORTED_IDENTIFIER = 257;

    /** The schema factory. */
    private final DataFileSchemaFactory schemaFactory = DataFileSchemaFactory
	    .instance();

    /**
     * Should throw an exception if it gets a file with a not supported
     * identifier.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws UnsupportedDataFileFormatException
     *             the unsupported data file format exception
     */
    @Test(expected = UnsupportedDataFileFormatException.class)
    public void shouldThrowAnExceptionIfItGetsAFileWithANotSupportedIdentifier()
	    throws IOException, UnsupportedDataFileFormatException {

	final int unsupportedIdentifier = -14;
	final ByteCountingReader reader = SchemaReaderTestBuilder.instance()
		.createHeader(unsupportedIdentifier, 100, 100).build();
	schemaFactory.createSchemaForDataFile(reader);
    }

    /**
     * Should open the readers stream before any interactions happens.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws UnsupportedDataFileFormatException
     *             the unsupported data file format exception
     */
    @Test
    public void shouldOpenTheReadersStreamBeforeAnyInteractionsHappens()
	    throws IOException, UnsupportedDataFileFormatException {

	final ByteCountingReader reader = mock(ByteCountingReader.class);
	when(reader.readInt()).thenReturn(SUPPORTED_IDENTIFIER);
	final InOrder inOrder = inOrder(reader);

	schemaFactory.createSchemaForDataFile(reader);
	inOrder.verify(reader, atLeastOnce()).openStream();
	inOrder.verify(reader, atLeastOnce()).readInt();
    }

    /**
     * Should close the readers stream after reading.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws UnsupportedDataFileFormatException
     *             the unsupported data file format exception
     */
    @Test
    public void shouldCloseTheReadersStreamAfterReading() throws IOException,
	    UnsupportedDataFileFormatException {

	final ByteCountingReader reader = mock(ByteCountingReader.class);
	when(reader.readInt()).thenReturn(SUPPORTED_IDENTIFIER);
	final InOrder inOrder = inOrder(reader);

	schemaFactory.createSchemaForDataFile(reader);
	inOrder.verify(reader, atLeastOnce()).readInt();
	inOrder.verify(reader, atLeastOnce()).closeStream();
    }

    /**
     * Should create a schema with two columns if the data file contains two
     * field descriptions.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws UnsupportedDataFileFormatException
     *             the unsupported data file format exception
     */
    @Test
    public void shouldCreateASchemaWithTwoColumnsIfTheDataFileContainsTwoFieldDescriptions()
	    throws IOException, UnsupportedDataFileFormatException {

	final int recordLength = 12;
	final short firstColumnNameLength = (short) 5;
	final String firstColumnName = "Hello";
	final short firstColumnLength = (short) 10;
	final short secondColumnNameLength = (short) 4;
	final String secondColumnName = "Name";
	final short secondColumnLength = (short) 64;
	final int offset = 100;

	final ByteCountingReader reader = SchemaReaderTestBuilder
		.instance()
		.createHeader(SUPPORTED_IDENTIFIER, offset, recordLength)
		.addColumn(firstColumnNameLength, firstColumnName,
			firstColumnLength)
		.addColumn(secondColumnNameLength, secondColumnName,
			secondColumnLength).build();

	final DataFileSchema schema = schemaFactory
		.createSchemaForDataFile(reader);

	final List<DataFileColumn> expectedColumn = new ArrayList<DataFileColumn>(
		Arrays.asList(DataFileColumn.create(firstColumnName, 1,
			firstColumnLength), DataFileColumn.create(
			secondColumnName, firstColumnLength + 1,
			secondColumnLength)));

	final DataFileSchema expectedSchema = DataFileSchema.create(
		new DataFileHeader(SUPPORTED_IDENTIFIER, offset),
		expectedColumn, 0);

	assertEquals(expectedSchema, schema);
    }

    /**
     * Should create a schema without0 columns if the data file contains only a
     * header.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws UnsupportedDataFileFormatException
     *             the unsupported data file format exception
     */
    @Test
    public void shouldCreateASchemaWithout0ColumnsIfTheDataFileContainsOnlyAHeader()
	    throws IOException, UnsupportedDataFileFormatException {

	final int recordLength = 0;
	final int offset = 0;
	final ByteCountingReader reader = SchemaReaderTestBuilder.instance()
		.createHeader(SUPPORTED_IDENTIFIER, offset, recordLength)
		.build();
	final DataFileSchema schema = schemaFactory
		.createSchemaForDataFile(reader);

	final DataFileSchema expectedSchema = DataFileSchema.create(
		new DataFileHeader(SUPPORTED_IDENTIFIER, offset),
		Collections.<DataFileColumn> emptyList(), 0);
	assertEquals(expectedSchema, schema);
    }

    /**
     * Should create a schema with all columns in database order.
     */
    public void shouldCreateASchemaWithAllColumnsInDatabaseOrder() {
	fail();
    }
}
