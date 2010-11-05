package suncertify.datafile;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import suncertify.db.DatabaseHandler;

import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * Test for the class {@link DataFileAccessService}.
 */
public final class DataFileAccessServiceTest {

    /**
     * Should create a database handler that could read the first record from an
     * data file.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws UnsupportedDataFileFormatException
     *             the unsupported data file format exception
     */
    @Test
    public void shouldCreateADatabaseHandlerThatCouldReadTheFirstRecordFromAnDataFile()
	    throws IOException, UnsupportedDataFileFormatException {

	final File dataFile = new File(
		"/Users/arnelandwehr/Coden/Sun Certified Java Developer/Project/Code/scjd.urlybird/src/test/ressources/db-1x1.db");
	final DatabaseHandler handler = DataFileAccessService.instance()
		.getHandlerForDataFile(dataFile);
	final List<String> record = handler.readRecord(0);

	final List<String> expectedReccord = Lists.newArrayList("Palace",
		"Smallville", "2", "Y", "$150.00", "2005/07/27", "");
	assertThat(record, is(equalTo(expectedReccord)));
    }

    /**
     * Should create a database handler that could read the10th record from an
     * data file.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws UnsupportedDataFileFormatException
     *             the unsupported data file format exception
     */
    @Test
    public void shouldCreateADatabaseHandlerThatCouldReadThe10thRecordFromAnDataFile()
	    throws IOException, UnsupportedDataFileFormatException {

	final File dataFile = new File(
		"/Users/arnelandwehr/Coden/Sun Certified Java Developer/Project/Code/scjd.urlybird/src/test/ressources/db-1x1.db");
	final DatabaseHandler handler = DataFileAccessService.instance()
		.getHandlerForDataFile(dataFile);
	final List<String> record = handler.readRecord(9);

	final List<String> expectedReccord = Lists.newArrayList("Dew Drop Inn",
		"Pleasantville", "6", "N", "$160.00", "2005/03/04", "");
	assertThat(record, is(equalTo(expectedReccord)));
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
	DataFileAccessService.instance().getHandlerForDataFile(wrongDataFile);
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

	final File anyFile = File.createTempFile("test",
		"DataFileAccessServiceTest");
	final FileWriter writer = new FileWriter(anyFile);
	try {
	    writer.write("This is just a random file with text");
	} finally {
	    writer.close();
	}

	DataFileAccessService.instance().getHandlerForDataFile(anyFile);
    }
}
