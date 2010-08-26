package suncertify.db;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DataHandlerFactory {

    public static DataHandlerFactory getInstance(
	    final DataFileSchemaFactory schemaFactory,
	    final DataFileFormatFactory formatFactory) {
	return new DataHandlerFactory(schemaFactory, formatFactory);
    }

    private final DataFileSchemaFactory schemaFactory;

    private final DataFileFormatFactory formatFactory;

    DataHandlerFactory(final DataFileSchemaFactory schemaFactory,
	    final DataFileFormatFactory formatFactory) {
	this.schemaFactory = schemaFactory;
	this.formatFactory = formatFactory;

    }

    DatabaseAccessHandler createHandlerForDataSource(final File source)
	    throws UnsupportedDataSourceException, IOException {

	final DataFileFormat format = formatFactory
		.createFormat(new DataInputStream(new FileInputStream(source)));
	final DataSchema schema = schemaFactory
		.createSchema(new DataInputStream(new FileInputStream(source)));

	return new DataFileAccessHandler(schema);
    }
}
