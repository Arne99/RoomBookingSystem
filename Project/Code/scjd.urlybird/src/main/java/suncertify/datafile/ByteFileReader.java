package suncertify.datafile;

import java.io.IOException;

interface ByteFileReader extends QuietlyClosable, Openable {

    byte readByte() throws IOException;

    char readChar() throws IOException;

    int readInt() throws IOException;

    short readShort() throws IOException;

    String readString(int numberOfBytes) throws IOException;

    void skipBytes(int numberOfBytes) throws IOException;

}
