package suncertify.datafile;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * The Tests for the Class DataFileSchemaFactory.
 */
public final class DataFileSchemaFactoryTest {

    /** The Constant SUPPORTED_IDENTIFIER. */
    private static final int SUPPORTED_IDENTIFIER = 257;

    private File testFile;

    private DataOutputStream writer;

    @Before
    public void setUp() throws IOException {
	testFile = File.createTempFile("test", "DataFileSchemaFactoryTest");
    }

    @After
    public void tearDown() throws IOException {
	testFile.delete();
    }

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

	final int invalidIdentifier = SUPPORTED_IDENTIFIER - 1;
	testFile = File.createTempFile("test", "DataFileSchemaFactoryTest");
	writer = new DataOutputStream(new FileOutputStream(testFile));
	writer.writeInt(invalidIdentifier);

	final DataFileSchemaFactory factory = new DataFileSchemaFactory(null);
	factory.createSchemaForDataFile(testFile);
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

	UrlyBirdSchemaWriter.writeSchema(SUPPORTED_IDENTIFIER, 200)
		.withColumn("name", 160).withColumn("age", 40).toFile(testFile);

	final BytesToStringDecoder decoder = mock(BytesToStringDecoder.class);
	when(decoder.decodeBytesToString((byte[]) anyVararg())).thenReturn(
		"name", "age");
	final DataFileSchemaFactory factory = new DataFileSchemaFactory(decoder);
	final DataFileSchema schema = factory.createSchemaForDataFile(testFile);

	final DataFileColumn expectedColumnOne = DataFileColumn.create("name",
		1, 160);
	final DataFileColumn expectedColumnTwo = DataFileColumn.create("age",
		1 + 160, 40);
	assertThat(schema.getColumnsInDatabaseOrder(),
		hasItems(expectedColumnOne, expectedColumnTwo));
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
    public void shouldCreateASchemaWithZeroColumnsIfTheDataFileContainsOnlyAHeader()
	    throws IOException, UnsupportedDataFileFormatException {

	UrlyBirdSchemaWriter.writeSchema(SUPPORTED_IDENTIFIER, 0).toFile(
		testFile);

	final DataFileSchemaFactory factory = new DataFileSchemaFactory(null);
	final DataFileSchema schema = factory.createSchemaForDataFile(testFile);

	assertThat(schema, is(not(nullValue())));
	assertThat(schema.getColumnsInDatabaseOrder(),
		is(equalTo(Collections.<DataFileColumn> emptyList())));
    }

}
