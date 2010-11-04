package suncertify.datafile;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import suncertify.datafile.UnsupportedDataFileFormatException;
import suncertify.db.DatabaseHandler;

import org.junit.Test;

public class DataFileAccessServiceTest {

    public void test() throws IOException, UnsupportedDataFileFormatException {

	final File dataFile = new File(
		"/Users/arnelandwehr/Coden/Sun Certified Java Developer/Project/Code/scjd.urlybird/src/test/ressources/db-1x1.db");
	final DataInputStream stream = new DataInputStream(new FileInputStream(
		dataFile));

	for (int i = 0; i < dataFile.length(); i++) {
	    final byte readByte = stream.readByte();
	    System.out.print(i + ".   " + readByte + "   " + (char) readByte
		    + "\n");
	}
	stream.close();
    }

    @Test
    public void shouldCreateADatabaseHandlerThatCouldReadARecordFromAnDataFile()
	    throws IOException, UnsupportedDataFileFormatException {

	final File dataFile = new File(
		"/Users/arnelandwehr/Coden/Sun Certified Java Developer/Project/Code/scjd.urlybird/src/test/ressources/db-1x1.db");
	final DatabaseHandler handler = DataFileAccessService.instance()
		.getHandlerForDataFile(dataFile);
    }
}
