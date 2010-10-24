package suncertify.datafile;

interface QuietlyClosable {

    void closeQuietly();

    boolean isClosed();
}