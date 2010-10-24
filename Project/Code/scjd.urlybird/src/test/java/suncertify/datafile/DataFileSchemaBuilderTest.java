package suncertify.datafile;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import suncertify.db.InvalidDataFileFormatException;

public class DataFileSchemaBuilderTest {

    @Test
    public void shouldPrintFirstNodeName() throws SAXException, IOException,
	    ParserConfigurationException, InvalidDataFileFormatException {
	final DataFileSchemaBuilder schemaBuilder = DataFileSchemaBuilder
		.instance();
	final File db = new File(
		"/Users/arnelandwehr/Coden/Sun Certified Java Developer/Project/Code/scjd.urlybird/src/test/ressources/db-1x1.db");
	final DataFileSchema schema = schemaBuilder
		.buildSchemaForDataFile(new Utf8FileReader(new DataInputStream(
			new FileInputStream(db))));
	System.out.println(schema);
    }
}
