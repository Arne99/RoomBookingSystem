package suncertify.datafile;

import java.io.IOException;

class Utf8ByteCountingReader implements ByteCountingReader {

    private final ByteFileReader reader;

    private int byteCount;

    Utf8ByteCountingReader(final ByteFileReader reader) {
	super();
	this.reader = reader;
    }

    @Override
    public void closeStream() {
	reader.closeStream();
    }

    @Override
    public int getCount() {
	return byteCount;
    }

    @Override
    public boolean isClosed() {
	return reader.isClosed();
    }

    @Override
    public boolean isOpen() {
	return reader.isOpen();
    }

    @Override
    public void openStream() throws IOException {
	reader.openStream();
    }

    @Override
    public byte readByte() throws IOException {
	final byte readByte = reader.readByte();
	byteCount += 1;
	return readByte;
    }

    @Override
    public char readChar() throws IOException {
	final char readChar = reader.readChar();
	byteCount += 1;
	return readChar;
    }

    @Override
    public int readInt() throws IOException {
	final int readInt = reader.readInt();
	byteCount += 4;
	return readInt;
    }

    @Override
    public short readShort() throws IOException {
	final short readShort = reader.readShort();
	byteCount += 2;
	return readShort;
    }

    @Override
    public String readString(final int numberOfBytes) throws IOException {
	final String string = reader.readString(numberOfBytes);
	byteCount += numberOfBytes;
	return string;
    }

    @Override
    public boolean readyToRead() throws IOException {
	return reader.readyToRead();
    }

    @Override
    public void resetCount() {
	byteCount = 0;
    }

    @Override
    public void skipFully(final int numberOfBytes) throws IOException {
	reader.skipFully(numberOfBytes);
    }

    @Override
    public int availableBytes() throws IOException {
	return reader.availableBytes();
    }

}
