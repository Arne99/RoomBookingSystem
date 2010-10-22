package suncertify.datafile;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

public class DataFileSchemaBuilderTest {

    @Test
    public void shouldPrintFirstNodeName() throws SAXException, IOException,
	    ParserConfigurationException {
	final DataFileSchemaBuilder schemaBuilder = new DataFileSchemaBuilder();
	final InputStream fileInputStream = getClass().getResourceAsStream(
		"SupportedDataFileSchemas.xml");
	schemaBuilder.buildDataFileHandler(fileInputStream);
    }
}
