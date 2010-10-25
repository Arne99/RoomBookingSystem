package suncertify.datafile;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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
	    throw new IllegalStateException(
		    "This DataFileReader cannot read in the State \"closed\"! ");
	}

	@Override
	public char readChar() throws IOException {
	    throw new IllegalStateException(
		    "This DataFileReader cannot read in the State \"closed\"! ");
	}

	@Override
	public int readInt() throws IOException {
	    throw new IllegalStateException(
		    "This DataFileReader cannot read in the State \"closed\"! ");
	}

	@Override
	public short readShort() throws IOException {
	    throw new IllegalStateException(
		    "This DataFileReader cannot read in the State \"closed\"! ");
	}

	@Override
	public String readString(final int numberOfBytes) throws IOException {
	    throw new IllegalStateException(
		    "This DataFileReader cannot read in the State \"closed\"! ");
	}

	@Override
	public void skipBytes(final int numberOfBytes) throws IOException {
	    throw new IllegalStateException(
		    "This DataFileReader cannot skip in the State \"closed\"! ");

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
	public void closeStream() {

	    try {
		input.close();
	    } catch (final IOException e) {
		// nothing we can do here
		e.printStackTrace();
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
		sb.append(readChar());
	    }
	    return sb.toString();
	}

	@Override
	public void skipBytes(final int numberOfBytes) throws IOException {
	    input.skipBytes(numberOfBytes);
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
    public void closeStream() {

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
	return readShort();
    }

    @Override
    public String readString(final int length) throws IOException {
	return readString(length);
    }

    @Override
    public void skipBytes(final int bytesToSkip) throws IOException {
	state.skipBytes(bytesToSkip);
    }
}
