package suncertify.datafile;

class UnsupportedDataFileFormatException extends Exception {

    private static final long serialVersionUID = -6958147326260949483L;

    UnsupportedDataFileFormatException(final String message) {
	super(message);
    }

}
