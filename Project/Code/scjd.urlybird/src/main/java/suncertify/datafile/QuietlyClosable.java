package suncertify.datafile;

interface QuietlyClosable {

    void closeStream();

    boolean isClosed();
}