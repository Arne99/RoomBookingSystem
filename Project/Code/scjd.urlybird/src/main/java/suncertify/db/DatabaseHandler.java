package suncertify.db;

import java.io.IOException;
import java.util.List;

public interface DatabaseHandler {

    void deleteRecord(int index) throws IOException;

    List<Record> findMatchingRecords(final Record queryRecord)
	    throws IOException;

    Record readValidRecord(int index) throws IOException, RecordNotFoundException;

    void writeRecord(Record record) throws IOException;
}
