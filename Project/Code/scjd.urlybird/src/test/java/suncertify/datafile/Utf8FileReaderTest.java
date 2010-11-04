package suncertify.datafile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The Tests for the Class Utf8FileReader.
 */
public final class Utf8FileReaderTest {

    /** The file reader. */
    private Utf8FileReader fileReader;

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
     * Should allow any client to call open steam twice without calling close
     * stream between.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void shouldAllowAnyClientToOpenASteamTwiceWithoutCallingCloseStreamBetween()
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
    @Test(expected = EOFException.class)
    public void shouldAllowAnyReadOperationIfOpenWasCalledBefore()
	    throws IOException {

	fileReader = Utf8FileReader.create(anyFile);
	fileReader.openStream();
	fileReader.readInt();
    }

    /**
     * Should close the underlying stream so the next read starts at the file
     * beginning.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void shouldBeAbleToCloseTheUnderlyingStreamSoTheNextReadStartsAtTheFileBeginning()
	    throws IOException {

	final byte firstByte = 1;
	final byte secondByte = 3;
	writeBytesToTestFile(firstByte, secondByte);

	fileReader = Utf8FileReader.create(anyFile);
	fileReader.openStream();
	final byte firstReadByte = fileReader.readByte();
	fileReader.closeStream();

	fileReader.openStream();
	final byte secondReadByte = fileReader.readByte();

	assertEquals(firstByte, firstReadByte);
	assertEquals(firstByte, secondReadByte);
    }

    /**
     * Should be able to read bytes and convert them to a string.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void shouldBeAbleToReadBytesAndConvertThemToAString()
	    throws IOException {

	writeBytesToTestFile((byte) 'H', (byte) 'e', (byte) 'l', (byte) 'l',
		(byte) 'o');
	fileReader = Utf8FileReader.create(anyFile);
	fileReader.openStream();
	final String readString = fileReader.readString("Hello".length());

	assertEquals("Hello", readString);

    }

    /**
     * Should be able to read the next byte as a char.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void shouldBeAbleToReadTheNextByteAsAChar() throws IOException {

	final byte anyChar = 'z';
	writeBytesToTestFile(anyChar);
	fileReader = Utf8FileReader.create(anyFile);
	fileReader.openStream();
	final char readChar = fileReader.readChar();

	assertEquals(anyChar, readChar);
    }

    /**
     * Should be able to read the next four bytes and convert them to an int.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void shouldBeAbleToReadTheNextFourBytesAndConvertThemToAnInt()
	    throws IOException {

	writeBytesToTestFile((byte) 1, (byte) 2, (byte) 3, (byte) 4);
	fileReader = Utf8FileReader.create(anyFile);
	fileReader.openStream();
	final int readInt = fileReader.readInt();

	assertEquals(16909060, readInt);
	assertFalse(fileReader.readyToRead());
    }

    /**
     * Should be able to read the next two byte and convert them to a short.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void shouldBeAbleToReadTheNextTwoByteAndConvertThemToAShort()
	    throws IOException {

	writeBytesToTestFile((byte) 1, (byte) 2);
	fileReader = Utf8FileReader.create(anyFile);
	fileReader.openStream();
	final short readShort = fileReader.readShort();

	assertEquals(258, readShort);
	assertFalse(fileReader.readyToRead());
    }

    /**
     * Should be able to return read the next byte in a file.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void shouldBeAbleToReturnReadTheNextByteInAFile() throws IOException {

	final byte nextByteValue = 1;
	writeBytesToTestFile(nextByteValue);

	fileReader = Utf8FileReader.create(anyFile);
	fileReader.openStream();
	final byte readByte = fileReader.readByte();
	assertEquals(nextByteValue, readByte);
    }

    /**
     * Should be able to skip an fixed number of bytes before it reads the next
     * bytes.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void shouldBeAbleToSkipAnFixedNumberOfBytesBeforeItReadsTheNextBytes()
	    throws IOException {

	writeBytesToTestFile((byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5,
		(byte) 6, (byte) 7);
	fileReader = Utf8FileReader.create(anyFile);
	fileReader.openStream();
	fileReader.skipBytes(6);
	final byte readByte = fileReader.readByte();

	assertEquals(7, readByte);
    }

    /**
     * Should be ready to read if the file contains at least one more byte.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void shouldBeReadyToReadIfTheFileContainsAtLeastOneMoreByte()
	    throws IOException {

	writeBytesToTestFile((byte) 1);
	fileReader = Utf8FileReader.create(anyFile);
	fileReader.openStream();

	assertTrue(fileReader.readyToRead());
    }

    /**
     * Should not be ready to read if the file contains no more bytes.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void shouldNotBeReadyToReadIfTheFileContainsNoMoreBytes()
	    throws IOException {

	fileReader = Utf8FileReader.create(anyFile);
	fileReader.openStream();

	assertFalse(fileReader.readyToRead());
    }

    /**
     * Should not say that it is open if the underlying stream is closed.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void shouldNotSayThatItIsOpenIfTheUnderlyingStreamIsClosed()
	    throws IOException {

	fileReader = Utf8FileReader.create(anyFile);
	fileReader.closeStream();
	assertFalse(fileReader.isOpen());
    }

    /**
     * Should not say that its is closed if the underlying stream is open.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void shouldNotSayThatItsIsClosedIfTheUnderlyingStreamIsOpen()
	    throws IOException {

	writeBytesToTestFile();
	fileReader = Utf8FileReader.create(anyFile);
	fileReader.openStream();
	assertFalse(fileReader.isClosed());
    }

    /**
     * Should return the next three bytes in the file if the method read byte is
     * three times in a row.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void shouldReturnTheNextThreeBytesInTheFileIfTheMethodReadByteIsThreeTimesInARow()
	    throws IOException {

	final byte firstByteValue = 1;
	final byte secondByteValue = 12;
	final byte thirdByteValue = 56;

	writeBytesToTestFile(firstByteValue, secondByteValue, thirdByteValue);

	fileReader = Utf8FileReader.create(anyFile);
	fileReader.openStream();
	final byte firstReadByte = fileReader.readByte();
	final byte secondReadByte = fileReader.readByte();
	final byte thirdReadByte = fileReader.readByte();

	assertEquals(firstByteValue, firstReadByte);
	assertEquals(secondByteValue, secondReadByte);
	assertEquals(thirdByteValue, thirdReadByte);
    }

    /**
     * Should say that it is closed if it was closed.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void shouldSayThatItIsClosedIfItWasClosed() throws IOException {

	fileReader = Utf8FileReader.create(anyFile);
	fileReader.openStream();
	fileReader.closeStream();
	assertTrue(fileReader.isClosed());
    }

    /**
     * Should say that it is open if it was opened.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void shouldSayThatItIsOpenIfItWasOpened() throws IOException {

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
    @Test(expected = IllegalStateException.class)
    public void shouldThrowAnExceptionIfAReadMethodIsCalledBeforeOpenStreamWasCalled()
	    throws IOException {

	fileReader = Utf8FileReader.create(anyFile);
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
