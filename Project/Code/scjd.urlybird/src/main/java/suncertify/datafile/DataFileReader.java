package suncertify.datafile;

import java.io.DataInput;
import java.io.IOException;

class DataFileReader {

    private final DataInput inputDelegate;
    private int readBytes;

    public DataFileReader(final DataInput inputDelegate) {
	super();
	this.inputDelegate = inputDelegate;
    }

    public String readString(final int length) throws IOException {

	final StringBuilder sb = new StringBuilder();
	for (int i = 0; i < length; i++) {
	    sb.append(readByteAsChar());
	}
	return sb.toString();
    }

    private char readByteAsChar() throws IOException {
	return (char) readByte();
    }

    int getReadBytes() {
	return readBytes;
    }

    byte readByte() throws IOException {
	final byte readByte = inputDelegate.readByte();
	readBytes += 1;
	return readByte;
    }

    int readInt() throws IOException {
	final int readInt = inputDelegate.readInt();
	readBytes += 4;
	return readInt;
    }

    short readShort() throws IOException {
	final short readShort = inputDelegate.readShort();
	readBytes += 2;
	return readShort;
    }

    void skipBytes(final int bytesToSkip) throws IOException {
	inputDelegate.skipBytes(bytesToSkip);
    }

}
