package suncertify.datafile;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

class DataFileSchemaBuilder {

    DataFileHandler buildDataFileHandler(final InputStream input)
	    throws SAXException, IOException, ParserConfigurationException {
	final DocumentBuilderFactory builderFactory = DocumentBuilderFactory
		.newInstance();
	final DocumentBuilder documentBuilder = builderFactory
		.newDocumentBuilder();
	final Document document = documentBuilder.parse(input);

	System.out.println(document.getNodeName());
	final NodeList elementsByTagName = document
		.getElementsByTagName("DataFileSchema");
	final Node node = elementsByTagName.item(0);
	System.out.println(node.getNodeName());
	
	parseDocument(Document document);

	return null;
    }
}
