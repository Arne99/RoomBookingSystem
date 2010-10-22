package suncertify.db;

import java.io.File;
import java.io.IOException;
import java.util.List;

import suncertify.datafile.DataFileRecord;

public interface DatabaseHandler {

    DataFileRecord readRecord(int index) throws IOException;
}
