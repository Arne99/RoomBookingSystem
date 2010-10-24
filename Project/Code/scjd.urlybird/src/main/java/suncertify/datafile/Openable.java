package suncertify.datafile;

import java.io.IOException;

interface Openable {

    boolean isOpen();

    void open() throws IOException;
}
