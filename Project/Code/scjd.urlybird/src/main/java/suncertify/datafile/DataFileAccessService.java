package suncertify.datafile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import suncertify.db.DatabaseHandler;
import suncertify.db.InvalidDataFileFormatException;

public class DataFileAccessService {

    private static final DataFileAccessService INSTANCE = new DataFileAccessService(
	    DataFileSchemaFactory.instance());

    public static final DataFileAccessService instance() {
	return INSTANCE;
    }

    private final DataFileSchemaFactory schemaBuilder;

    private DataFileAccessService(final DataFileSchemaFactory schemaBuilder) {
	this.schemaBuilder = schemaBuilder;
    }

    DatabaseHandler getHandlerForDataFile(final File dataFile)
	    throws IOException, InvalidDataFileFormatException {

	final DataFileSchema dataFileSchema = schemaBuilder
		.buildSchemaForDataFile(new Utf8ByteCountingReader(
			Utf8FileReader.create(dataFile)));

	final DataFileHandler dataFileHandler = new DataFileHandler(
		dataFileSchema, Utf8FileReader.create(dataFile), null);

	return dataFileHandler;
    }
}
