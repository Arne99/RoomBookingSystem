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
	    DataFileSchemaBuilder.instance());

    private static final DataFileAccessService instance() {
	return INSTANCE;
    }

    private final DataFileSchemaBuilder schemaBuilder;

    public DataFileAccessService(final DataFileSchemaBuilder schemaBuilder) {
	this.schemaBuilder = schemaBuilder;
    }

    DatabaseHandler getHandlerForDataFile(final File dataFile)
	    throws IOException, InvalidDataFileFormatException {

	try {
	    final DataFileSchema dataFileSchema = schemaBuilder
		    .buildSchemaForDataFile(new DataFileReader(
			    new DataInputStream(new FileInputStream(dataFile))));
	    final DataOutputStream dataOutputStream = new DataOutputStream(
		    new FileOutputStream(dataFile));

	    final DataFileHandler dataFileHandler = new DataFileHandler(
		    dataFileSchema, new DataFileReader(new DataInputStream(
			    new FileInputStream(dataFile))), dataOutputStream);
	} finally {

	}
    }
}
