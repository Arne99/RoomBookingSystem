package suncertify.datafile;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import suncertify.db.InvalidDataFileFormatException;

public class DataFileHandlerTest {

    @Test
    public final void schouldReadAValidRecord() throws FileNotFoundException,
	    IOException, InvalidDataFileFormatException {

	final File db = new File(
		"/Users/arnelandwehr/Coden/Sun Certified Java Developer/Project/Code/scjd.urlybird/src/test/ressources/db-1x1.db");
	final DataFileSchema schema = DataFileSchemaBuilder.instance()
		.buildSchemaForDataFile(
			new Utf8FileReader(new DataInputStream(
				new FileInputStream(db))));

	final DataFileHandler handler = new DataFileHandler(
		schema,
		new Utf8FileReader(new DataInputStream(new FileInputStream(db))),
		null);

	final DataFileRecord record = handler.readRecord(0);
	System.out.println(record.toString());

    }

}
