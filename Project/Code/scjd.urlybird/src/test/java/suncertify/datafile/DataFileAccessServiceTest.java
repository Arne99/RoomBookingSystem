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

    public void shouldCreateADatabaseHandlerThatCouldReadARecordFromAnDataFile()
	    throws IOException, UnsupportedDataFileFormatException {

	final File dataFile = new File(
		"/Users/arnelandwehr/Coden/Sun Certified Java Developer/Project/Code/scjd.urlybird/src/test/ressources/db-1x1.db");
	final DatabaseHandler handler = DataFileAccessService.instance()
		.getHandlerForDataFile(dataFile);
	System.out.println(handler.readRecord(0));
	System.out.println(handler.readRecord(1));
	System.out.println(handler.readRecord(2));
	System.out.println(handler.readRecord(3));
	System.out.println(handler.readRecord(4));
	System.out.println(handler.readRecord(5));
	System.out.println(handler.readRecord(6));
	System.out.println(handler.readRecord(7));

    }
}
