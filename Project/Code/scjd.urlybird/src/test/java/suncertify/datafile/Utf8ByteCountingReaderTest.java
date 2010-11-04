package suncertify.datafile;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.Test;

/**
 * The Test for the Class Utf8ByteCountingReaderTest.
 */
public final class Utf8ByteCountingReaderTest {

    /** The file reader. */
    private final ByteFileReader fileReader = mock(ByteFileReader.class);

    /**
     * Should be able to reset the counter.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void shouldBeAbleToResetTheCounter() throws IOException {

	final Utf8ByteCountingReader countingReader = new Utf8ByteCountingReader(
		fileReader);
	countingReader.readInt();
	countingReader.resetCount();

	assertEquals(0, countingReader.getCount());
    }

    /**
     * Should count four bytes if it reades an int.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void shouldCountFourBytesIfItReadesAnInt() throws IOException {

	final Utf8ByteCountingReader countingReader = new Utf8ByteCountingReader(
		fileReader);
	countingReader.readInt();

	assertEquals(4, countingReader.getCount());
    }

    /**
     * Should count n bytes if it reades a string of length n.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void shouldCountNBytesIfItReadesAStringOfLengthN()
	    throws IOException {

	final Utf8ByteCountingReader countingReader = new Utf8ByteCountingReader(
		fileReader);
	final int stringLength = 12;
	countingReader.readString(stringLength);

	assertEquals(stringLength, countingReader.getCount());
    }

    /**
     * Should count no bytes that it skips.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void shouldCountNoBytesThatItSkips() throws IOException {

	final Utf8ByteCountingReader countingReader = new Utf8ByteCountingReader(
		fileReader);
	countingReader.skipBytes(12);

	assertEquals(0, countingReader.getCount());
    }

    /**
     * Should count one byte if it reades a byte.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void shouldCountOneByteIfItReadesAByte() throws IOException {

	final Utf8ByteCountingReader countingReader = new Utf8ByteCountingReader(
		fileReader);
	countingReader.readByte();

	assertEquals(1, countingReader.getCount());
    }

    /**
     * Should count two bytes if it reades a short.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void shouldCountTwoBytesIfItReadesAShort() throws IOException {

	final Utf8ByteCountingReader countingReader = new Utf8ByteCountingReader(
		fileReader);
	countingReader.readShort();

	assertEquals(2, countingReader.getCount());
    }

    /**
     * Should count one byte if it reades a char.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void shouldCountOneByteIfItReadesAChar() throws IOException {

	final Utf8ByteCountingReader countingReader = new Utf8ByteCountingReader(
		fileReader);
	countingReader.readChar();

	assertEquals(1, countingReader.getCount());
    }

    /**
     * Should sum all read bytes over multiple reads.
     * 
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    @Test
    public void shouldSumAllReadBytesOverMultipleReads() throws IOException {

	final Utf8ByteCountingReader countingReader = new Utf8ByteCountingReader(
		fileReader);

	final int numberOfBytesToSkip = 10;
	final int stringLength = 24;

	countingReader.skipBytes(numberOfBytesToSkip);
	countingReader.readString(stringLength);
	countingReader.readByte();
	countingReader.readChar();
	countingReader.readShort();
	countingReader.readInt();

	assertEquals(stringLength + 1 + 1 + 2 + 4, countingReader.getCount());
    }
}
