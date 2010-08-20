package suncertify.db;

import java.io.File;

public interface DatabaseHandler {

    void init(File databasePath);

    Record readRecord(int anyValidIndex);
}
