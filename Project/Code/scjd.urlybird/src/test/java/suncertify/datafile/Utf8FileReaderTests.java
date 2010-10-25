package suncertify.datafile;

import static org.junit.Assert.*;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The Class Utf8FileReaderTests.
 */
public final class Utf8FileReaderTests {

    /** The file reader. */
    private Utf8FileReader fileReader;

    /** The any file. */
    private File anyFile;

    /**
     * Set up.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Before
    public void setUp() throws IOException {
	anyFile = File.createTempFile("temp", getClass().getSimpleName());
    }

    /**
     * Should allow any client to call open steam twice without calling close
     * stream between.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void shouldAllowAnyClientToCallOpenSteamTwiceWithoutCallingCloseStreamBetween()
	    throws IOException {
	fileReader = Utf8FileReader.create(anyFile);
	fileReader.openStream();
	fileReader.openStream();
    }

    /**
     * Should allow any read operation if open was called before.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test(expected = IllegalStateException.class)
    public void shouldAllowAnyReadOperationIfOpenWasCalledBefore()
	    throws IOException {
	fileReader = Utf8FileReader.create(anyFile);
	fileReader.readInt();
    }

    /**
     * Should close the underlying stream so the next read starts at the file
     * beginning.
     */
    @Test
    public void shouldCloseTheUnderlyingStreamSoTheNextReadStartsAtTheFileBeginning() {
	fail("Not yet implemented");
    }

    /**
     * Should return false for is closed if the underlying stream is open.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void shouldReturnFalseForIsClosedIfTheUnderlyingStreamIsOpen()
	    throws IOException {
	fileReader = Utf8FileReader.create(anyFile);
	fileReader.openStream();
	assertFalse(fileReader.isClosed());
    }

    /**
     * Should return false for is opened if the underlying stream is closed.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void shouldReturnFalseForIsOpenedIfTheUnderlyingStreamIsClosed()
	    throws IOException {
	fileReader = Utf8FileReader.create(anyFile);
	fileReader.closeStream();
	assertFalse(fileReader.isOpen());
    }

    /**
     * Should return true for is closed if it is closed.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void shouldReturnTrueForIsClosedIfItIsClosed() throws IOException {
	fileReader = Utf8FileReader.create(anyFile);
	fileReader.closeStream();
	assertTrue(fileReader.isClosed());
    }

    /**
     * Should return true for is opened if it the underlying stream is open.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void shouldReturnTrueForIsOpenedIfItTheUnderlyingStreamIsOpen()
	    throws IOException {
	fileReader = Utf8FileReader.create(anyFile);
	fileReader.openStream();
	assertTrue(fileReader.isOpen());
    }

    /**
     * Should throw an exception if a read method is called before open stream
     * was called.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test(expected = EOFException.class)
    public void shouldThrowAnExceptionIfAReadMethodIsCalledBeforeOpenStreamWasCalled()
	    throws IOException {
	fileReader = Utf8FileReader.create(anyFile);
	fileReader.openStream();
	fileReader.readInt();
    }

    /**
     * Tear down.
     */
    @After
    public void tearDown() {
	fileReader.closeStream();
	anyFile.delete();
    }

    /**
     * Test read byte.
     */
    public void testReadByte() {
	fail("Not yet implemented");
    }

    /**
     * Test read char.
     */
    public void testReadChar() {
	fail("Not yet implemented");
    }

    /**
     * Test read int.
     */
    public void testReadInt() {
	fail("Not yet implemented");
    }

    /**
     * Test read short.
     */
    public void testReadShort() {
	fail("Not yet implemented");
    }

    /**
     * Test read string.
     */
    public void testReadString() {
	fail("Not yet implemented");
    }

    /**
     * Test skip bytes.
     */
    public void testSkipBytes() {
	fail("Not yet implemented");
    }

}
