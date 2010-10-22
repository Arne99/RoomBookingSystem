package suncertify.db;

public class InvalidDataFileFormatException extends Exception {

    public InvalidDataFileFormatException(final String information) {
	super(information);
    }

}
