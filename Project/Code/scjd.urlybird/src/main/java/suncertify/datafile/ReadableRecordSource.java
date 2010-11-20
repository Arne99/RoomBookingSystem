package suncertify.datafile;

import java.io.IOException;
import java.util.List;

import suncertify.db.Record;
import suncertify.db.RecordNotFoundException;

interface ReadableRecordSource extends Iterable<Record> {

    Record getRecordAtIndex(int index) throws IOException;

    int getIndexOfLastRecord() throws IOException;

}
