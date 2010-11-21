package suncertify.datafile;

import static junit.framework.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import suncertify.db.Record;

/**
 * The Tests for the Class Utf8FileReader.
 */
public final class UrlyBirdDataFileReaderTest {

    /** The file reader. */
    private UrlyBirdDataFileReader fileReader;

    /** The any file. */
    private File anyFile;

    /**
     * Sets the up.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Before
    public void setUp() throws IOException {
	anyFile = createTempFile();
    }

    /**
     * Tear down.
     */
    @After
    public void tearDown() {
	anyFile.delete();
    }

    public final void shouldReturnAnNotValidRecordIfThereIsNoRecordAtTheSpecifiedIndex()
	    throws IOException {

	anyFile = createTempFile();

    }

    public final void shouldReturnAnNotValidRecordIfTheSpecifiedIndexIsGreaterThanTheHighestAvailableRecord() {
	fail();
    }

    /**
     * Creates the temp file.
     * 
     * @return the file
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private File createTempFile() throws IOException {
	return File.createTempFile("temp", getClass().getSimpleName());
    }

    /**
     * Write bytes to test file.
     * 
     * @param bytes
     *            the bytes
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private void writeBytesToTestFile(final byte... bytes) throws IOException {

	FileOutputStream fileOutputStream = null;
	try {
	    fileOutputStream = new FileOutputStream(anyFile);
	    fileOutputStream.write(bytes);
	} finally {
	    fileOutputStream.close();
	}
    }

}
