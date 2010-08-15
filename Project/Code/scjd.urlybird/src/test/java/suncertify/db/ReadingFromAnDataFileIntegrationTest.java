package suncertify.db;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

/**
 * TODO Javadoc
 * 
 * @author arnelandwehr
 * 
 */
public class ReadingFromAnDataFileIntegrationTest {

    private static final File testDataFile = new File("dummy");

    DatabaseHandlerRegistry handlerRegistry = DatabaseHandlerRegistry
	    .getInstance();

    @Test
    public final void clientCouldReadAValidRecordFromAnDataFile() {

	final DatabaseHandler databaseHandler = handlerRegistry
		.createHandlerForDataFile(testDataFile);

	final int anyValidIndex = 0;
	final Record record = databaseHandler.readRecord(anyValidIndex);

	final Record expectedRecord = new Record(new String[] { "test" });
	assertEquals(expectedRecord, record);
    }
}
