package suncertify.datafile;

import static suncertify.util.DesignByContract.*;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * 
 * @author arnelandwehr
 * 
 */
final class Utf8FileReader implements ByteFileReader {

    /**
     * 
     * @author arnelandwehr
     * 
     */
    private final class Closed implements ReaderState {

	private static final String CANNOT_SKIP_IN_THE_STATE_CLOSED = "This DataFileReader cannot skip in the State \"closed\"! ";
	private static final String CANNOT_READ_IN_THE_STATE_CLOSED = "This DataFileReader cannot read in the State \"closed\"! ";

	@Override
	public boolean readyToRead() {
	    return false;
	}

	@Override
	public void closeStream() {
	    // nothing to do, reader is already closed.
	}

	@Override
	public boolean isClosed() {
	    return true;
	}

	@Override
	public boolean isOpen() {
	    return false;
	}

	@Override
	public void openStream() throws IOException {
	    state = new Open(new DataInputStream(new FileInputStream(file)));
	}

	@Override
	public byte readByte() throws IOException {
	    throw new IllegalStateException(CANNOT_READ_IN_THE_STATE_CLOSED);
	}

	@Override
	public char readChar() throws IOException {
	    throw new IllegalStateException(CANNOT_READ_IN_THE_STATE_CLOSED);
	}

	@Override
	public int readInt() throws IOException {
	    throw new IllegalStateException(CANNOT_READ_IN_THE_STATE_CLOSED);
	}

	@Override
	public short readShort() throws IOException {
	    throw new IllegalStateException(CANNOT_READ_IN_THE_STATE_CLOSED);
	}

	@Override
	public String readString(final int numberOfBytes) throws IOException {
	    throw new IllegalStateException(CANNOT_READ_IN_THE_STATE_CLOSED);
	}

	@Override
	public void skipFully(final int numberOfBytes) throws IOException {
	    throw new IllegalStateException(CANNOT_SKIP_IN_THE_STATE_CLOSED);

	}

	@Override
	public int availableBytes() throws IOException {
	    throw new IllegalStateException(CANNOT_SKIP_IN_THE_STATE_CLOSED);
	}

    }

    /**
     * 
     * @author arnelandwehr
     * 
     */
    private final class Open implements ReaderState {

	private final DataInputStream input;

	public Open(final DataInputStream input) {
	    super();
	    this.input = input;
	}

	@Override
	public boolean readyToRead() throws IOException {
	    return input.available() > 0;
	}

	@Override
	public void closeStream() {

	    try {
		input.close();
	    } catch (final IOException e) {
		// nothing we can do here
		e.printStackTrace();
	    } finally {
		state = new Closed();
	    }
	}

	@Override
	public boolean isClosed() {
	    return false;
	}

	@Override
	public boolean isOpen() {
	    return true;
	}

	@Override
	public void openStream() throws IOException {
	    // nothing to do, reader is already open.
	}

	@Override
	public byte readByte() throws IOException {
	    return input.readByte();
	}

	@Override
	public char readChar() throws IOException {
	    return (char) input.readByte();
	}

	@Override
	public int readInt() throws IOException {
	    return input.readInt();
	}

	@Override
	public short readShort() throws IOException {
	    return input.readShort();
	}

	@Override
	public String readString(final int numberOfBytes) throws IOException {

	    final StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < numberOfBytes; i++) {
		final byte readByte = readByte();
		if (readByte <= 9) {
		    sb.append(readByte);
		} else {
		    sb.append((char) readByte);
		}
	    }
	    return sb.toString();
	}

	@Override
	public void skipFully(final int numberOfBytes) throws IOException {

	    int bytesToSkip = numberOfBytes;
	    while (bytesToSkip > 0) {

		if (input.available() == 0) {
		    throw new EOFException();
		}
		final int skipped = input.skipBytes(numberOfBytes);
		bytesToSkip -= skipped;
	    }
	}

	@Override
	public int availableBytes() throws IOException {
	    return input.available();
	}

    }

    private interface ReaderState extends ByteFileReader {
    }

    static Utf8FileReader create(final File file) {
	final Utf8FileReader dataFileReader = new Utf8FileReader(file);
	dataFileReader.state = dataFileReader.new Closed();
	return dataFileReader;
    }

    private final File file;

    private ReaderState state;

    private Utf8FileReader(final File file) {
	super();
	this.file = file;
    }

    @Override
    public boolean readyToRead() throws IOException {
	return state.readyToRead();
    }

    @Override
    public void closeStream() {
	state.closeStream();
    }

    @Override
    public boolean isClosed() {
	return state.isClosed();
    }

    @Override
    public boolean isOpen() {
	return state.isOpen();
    }

    @Override
    public void openStream() throws IOException {
	state.openStream();
    }

    @Override
    public byte readByte() throws IOException {
	return state.readByte();
    }

    @Override
    public char readChar() throws IOException {
	return state.readChar();
    }

    @Override
    public int readInt() throws IOException {
	return state.readInt();
    }

    @Override
    public short readShort() throws IOException {
	return state.readShort();
    }

    @Override
    public String readString(final int length) throws IOException {
	checkNotNegativ(length);
	return state.readString(length);
    }

    @Override
    public void skipFully(final int bytesToSkip) throws IOException {
	checkNotNegativ(bytesToSkip);
	state.skipFully(bytesToSkip);
    }

    @Override
    public int availableBytes() throws IOException {
	return state.availableBytes();
    }
}
