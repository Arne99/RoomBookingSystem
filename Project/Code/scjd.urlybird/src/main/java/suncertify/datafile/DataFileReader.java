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
}
