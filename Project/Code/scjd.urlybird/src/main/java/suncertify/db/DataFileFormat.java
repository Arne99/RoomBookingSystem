package suncertify.db;

public interface DataFileFormat {

    int getHeaderLengthInBytes();

    int getNumberOfColumns();

    int getRecordLength();

    boolean supports(int readInt);

}
