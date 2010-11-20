package suncertify.datafile;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import suncertify.db.DatabaseHandler;
import suncertify.db.Record;
import suncertify.db.RecordNotFoundException;

import org.junit.After;
import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * Test for the class {@link DataFileAccess}.
 */
public final class DataFileAccessTest {

    /** The data file. */
    private final File dataFile = new File(
	    "/Users/arnelandwehr/Coden/Sun Certified Java Developer/Project/Code/scjd.urlybird/src/test/ressources/db-1x1.db");

    /** The any file. */
    private File anyFile;

    /**
     * Tear down.
     */
    @After
    public void tearDown() {
	if (anyFile != null && anyFile.exists()) {
	    anyFile.delete();
	}
    }

    /**
     * Should create a database handler that could read the first record from an
     * data file.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws UnsupportedDataFileFormatException
     *             the unsupported data file format exception
     * @throws RecordNotFoundException
     *             the record not found exception
     */
    @Test
    public void shouldCreateADatabaseHandlerThatCouldReadTheFirstRecordFromAnDataFile()
	    throws IOException, UnsupportedDataFileFormatException,
	    RecordNotFoundException {

	final DatabaseHandler handler = DataFileAccess.instance()
		.getDatabaseHandler(dataFile);
	final Record record = handler.readValidRecord(0);

	final List<String> expectedReccord = Lists.newArrayList("Palace",
		"Smallville", "2", "Y", "$150.00", "2005/07/27", "");

	assertThat(record.getValues(), is(equalTo(expectedReccord)));
    }

    /**
     * Should throw an record not found exception the given record index is
     * greater than the number of available records.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws UnsupportedDataFileFormatException
     *             the unsupported data file format exception
     * @throws RecordNotFoundException
     *             the record not found exception
     */
    @Test(expected = RecordNotFoundException.class)
    public void shouldThrowAnRecordNotFoundExceptionTheGivenRecordIndexIsGreaterThanTheNumberOfAvailableRecords()
	    throws IOException, UnsupportedDataFileFormatException,
	    RecordNotFoundException {

	final DatabaseHandler handler = DataFileAccess.instance()
		.getDatabaseHandler(dataFile);
	handler.readValidRecord(5000);
    }

    /**
     * Should create a database handler that could read the10th record from an
     * data file.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws UnsupportedDataFileFormatException
     *             the unsupported data file format exception
     * @throws RecordNotFoundException
     *             the record not found exception
     */
    @Test
    public void shouldCreateADatabaseHandlerThatCouldReadThe10thRecordFromAnDataFile()
	    throws IOException, UnsupportedDataFileFormatException,
	    RecordNotFoundException {

	final DatabaseHandler handler = DataFileAccess.instance()
		.getDatabaseHandler(dataFile);
	final Record record = handler.readValidRecord(9);

	final List<String> expectedReccord = Lists.newArrayList("Dew Drop Inn",
		"Pleasantville", "6", "N", "$160.00", "2005/03/04", "");
	assertThat(record.getValues(), is(equalTo(expectedReccord)));
    }

    /**
     * Schould throw an io exception if the data file location is not valid.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws UnsupportedDataFileFormatException
     *             the unsupported data file format exception
     */
    @Test(expected = IOException.class)
    public void schouldThrowAnIOExceptionIfTheDataFileLocationIsNotValid()
	    throws IOException, UnsupportedDataFileFormatException {

	final File wrongDataFile = new File("/this/path/does/not/exist");
	DataFileAccess.instance().getDatabaseHandler(wrongDataFile);
    }

    /**
     * Should throw an exception if the data file is invalid.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws UnsupportedDataFileFormatException
     *             the unsupported data file format exception
     */
    @Test(expected = UnsupportedDataFileFormatException.class)
    public void shouldThrowAnExceptionIfTheDataFileIsInvalid()
	    throws IOException, UnsupportedDataFileFormatException {

	anyFile = File.createTempFile("test", "DataFileAccessServiceTest");
	final FileWriter writer = new FileWriter(anyFile);
	try {
	    writer.write("This is just a random file with text");
	} finally {
	    writer.close();
	}

	DataFileAccess.instance().getDatabaseHandler(anyFile);
    }
}
