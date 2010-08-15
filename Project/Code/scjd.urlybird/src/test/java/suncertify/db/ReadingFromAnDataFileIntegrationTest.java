package suncertify.db;

import static org.junit.Assert.assertEquals;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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

    /**
     * TODO - Am besten Zugriff direkt Ÿber die Data Klasse - Die Data Klasse
     * muss wegen threading wohl am betsen auch ein singleton sein (vieleicht
     * aber ach nicht gut...) - init einbauen - Ÿberlegen was besser als
     * Singletons ist. - vieleicht Client State mit einem DB Path...sehr
     * kompliziert :/
     * 
     * @throws IOException
     */
    @Test
    public final void clientCouldReadAValidRecordFromAnDataFile()
	    throws IOException {

	final DatabaseHandler databaseHandler = handlerRegistry
		.findHandlerForDataFile(new DataInputStream(
			new FileInputStream(testDataFile)));

	final int anyValidIndex = 0;
	final Record record = databaseHandler.readRecord(anyValidIndex);

	final Record expectedRecord = new Record(new String[] { "test" });
	assertEquals(expectedRecord, record);
    }
}
