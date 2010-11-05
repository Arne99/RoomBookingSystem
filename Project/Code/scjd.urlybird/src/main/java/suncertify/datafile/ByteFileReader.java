package suncertify.datafile;

import java.io.IOException;

interface ByteFileReader extends QuietlyClosable, Openable {

    boolean readyToRead() throws IOException;

    byte readByte() throws IOException;

    char readChar() throws IOException;

    int readInt() throws IOException;

    short readShort() throws IOException;

    String readString(int numberOfBytes) throws IOException;

    void skipFully(int numberOfBytes) throws IOException;

    int availableBytes() throws IOException;

}
