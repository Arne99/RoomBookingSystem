package suncertify.datafile;

import static suncertify.util.DesignByContract.*;

import java.io.File;
import java.io.IOException;

import suncertify.db.DatabaseHandler;

public final class DataFileAccess {

    private static final DataFileAccess INSTANCE = new DataFileAccess();

    public static final DataFileAccess instance() {
	return INSTANCE;
    }

    public DatabaseHandler getDatabaseHandler(final File dataFile)
	    throws IOException, UnsupportedDataFileFormatException {

	checkNotNull(dataFile, "dataFile");

	final BytesToStringDecoder decoder = new Utf8DecoderTest();
	final DataFileSchemaFactory schemaFactory = new DataFileSchemaFactory(
		decoder);

	final DataFileMetadata schema = schemaFactory
		.createSchemaForDataFile(dataFile);

	final RecordFactory recordFactory = new RecordFactory(schema, decoder);
	final ReadableRecordSource recordSource = new UrlyBirdDataFileReader(
		schema, dataFile, recordFactory);

	return new DataFileHandler(recordSource);
    }

    public void closeDatabaseConnection() {
	throw new UnsupportedOperationException();
    }
}
