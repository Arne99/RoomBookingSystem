package suncertify.datafile;

import static suncertify.util.DesignByContract.*;

import java.io.File;
import java.io.IOException;

import suncertify.db.DatabaseHandler;

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

    public DatabaseHandler getHandlerForDataFile(final File dataFile)
	    throws IOException, UnsupportedDataFileFormatException {

	checkNotNull(dataFile, "dataFile");

	final DataFileSchema dataFileSchema = schemaBuilder
		.createSchemaForDataFile(new Utf8ByteCountingReader(
			Utf8FileReader.create(dataFile)));

	final DataFileHandler dataFileHandler = new DataFileHandler(
		dataFileSchema, Utf8FileReader.create(dataFile), null);

	return dataFileHandler;
    }
}
