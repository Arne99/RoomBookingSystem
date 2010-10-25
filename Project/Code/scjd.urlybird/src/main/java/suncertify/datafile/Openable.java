package suncertify.datafile;

import java.io.IOException;

interface Openable {

    boolean isOpen();

    void openStream() throws IOException;
}
