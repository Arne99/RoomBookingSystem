package suncertify.datafile;

import java.io.DataInput;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import suncertify.db.UnsupportedDataSourceException;

class DataFileSchemaBuilder {

    private static final int supportedFormat = 42;

    /**
     * Creates a new DataFileSchema object.
     * 
     * @param input
     *            the input
     * @return the data schema
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     * @throws UnsupportedDataSourceException
     *             the unsupported data source exception
     */
    public DataFileSchema createSchemaForDataFile(final DataFileReader input)
	    throws IOException, UnsupportedDataSourceException {

	final int dataFileFormatidentifier = input.readInt();
	if (supportedFormat != dataFileFormatidentifier) {
	    throw new UnsupportedDataSourceException();
	}
	final int recordLength = dataFileFormatidentifier;
	final int numberOfColumns = input.readShort();

	final ArrayList<DataFileColumn> columns = new ArrayList<DataFileColumn>();
	for (int i = 1; i <= numberOfColumns; i++) {

	    final int columnNameLengthInByte = input.readShort();
	    final StringBuilder sb = new StringBuilder();

	    for (int j = 1; j <= columnNameLengthInByte; j++) {
		sb.append((char) input.readByte());
	    }

	    final int columnSize = input.readShort();
	    columns.add(new DataFileColumn(sb.toString(), columnSize));
	}

	return new DataFileSchema(supportedFormat, input.getReadBytes(),
		recordLength, columns);
    }

    ArrayList<DataFileSchema> buildSchemasFromXmlFixture(final InputStream input)
	    throws SAXException, IOException, ParserConfigurationException {

	final DocumentBuilderFactory builderFactory = DocumentBuilderFactory
		.newInstance();
	final DocumentBuilder documentBuilder = builderFactory
		.newDocumentBuilder();
	final Document document = documentBuilder.parse(input);
	final NodeList schemaNodes = document
		.getElementsByTagName("DataFileSchema");

	final ArrayList<DataFileSchema> schemas = new ArrayList<DataFileSchema>();
	for (int i = 0; i < schemaNodes.getLength(); i++) {
	    final Node schemaNode = schemaNodes.item(i);
	    final NamedNodeMap schemaAttributes = schemaNode.getAttributes();
	    final String key = schemaAttributes.getNamedItem("key")
		    .getNodeValue();
	    final String offset = schemaAttributes.getNamedItem("offset")
		    .getNodeValue();
	    final String recordLength = schemaAttributes.getNamedItem(
		    "recordLength").getNodeValue();

	    final NodeList childNodes = schemaNode.getChildNodes();
	    final ArrayList<DataFileColumn> columns = new ArrayList<DataFileColumn>();
	    for (int j = 0; j < childNodes.getLength(); j++) {
		final Node nextNode = childNodes.item(j);
		if (nextNode.getNodeName().equals("Column")) {
		    final NamedNodeMap columnAttributes = nextNode
			    .getAttributes();
		    final String name = columnAttributes.getNamedItem("name")
			    .getNodeValue();
		    final String length = columnAttributes.getNamedItem("size")
			    .getNodeValue();
		    final DataFileColumn column = new DataFileColumn(name,
			    Integer.parseInt(length));
		    columns.add(column);
		}
	    }
	    final DataFileSchema schema = new DataFileSchema(key,
		    Integer.parseInt(offset), Integer.parseInt(recordLength),
		    columns);
	    schemas.add(schema);
	}

	return schemas;
    }
}
