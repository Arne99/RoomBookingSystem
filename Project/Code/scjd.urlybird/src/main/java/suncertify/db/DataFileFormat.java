package suncertify.db;

public interface DataFileFormat {

    int getHeaderLengthInBytes();

    int getNumberOfColumns();

    boolean supports(int readInt);

}
