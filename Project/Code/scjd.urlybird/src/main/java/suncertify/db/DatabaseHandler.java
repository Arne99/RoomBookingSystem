package suncertify.db;

import java.io.IOException;
import java.util.List;

public interface DatabaseHandler {

    void deleteRecord(int index) throws IOException;

    List<Record> findMatchingRecords(final Record queryRecord)
	    throws IOException;

    List<String> readRecord(int index) throws IOException;

    void writeRecord(Record record) throws IOException;
}
