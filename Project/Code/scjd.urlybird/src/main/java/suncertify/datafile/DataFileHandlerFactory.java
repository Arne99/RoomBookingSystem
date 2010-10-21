package suncertify.datafile;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import suncertify.db.DataSchema;
import suncertify.db.DatabaseAccessHandler;
import suncertify.db.UnsupportedDataSourceException;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating DataHandler objects.
 */
public class DataFileHandlerFactory {

    /** The schema factory. */
    private final DataFileSchemaFactory schemaFactory;

    /** The format factory. */
    private final DataFileFormatFactory formatFactory;

    /**
     * Instantiates a new data handler factory.
     * 
     * @param schemaFactory
     *            the schema factory
     * @param formatFactory
     *            the format factory
     */
    DataFileHandlerFactory(final DataFileSchemaFactory schemaFactory,
	    final DataFileFormatFactory formatFactory) {
	this.schemaFactory = schemaFactory;
	this.formatFactory = formatFactory;

    }

    /**
     * Creates a new DataHandler object.
     * 
     * @param dataFile
     *            the source
     * @return the database access handler
     * @throws UnsupportedDataSourceException
     *             the unsupported data source exception
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    DatabaseAccessHandler createDataFileHandler(final File dataFile)
	    throws UnsupportedDataSourceException, IOException {

	final DataFileFormat format = formatFactory
		.createFormat(new DataInputStream(new FileInputStream(dataFile)));
	final DataSchema schema = schemaFactory
		.createSchema(new DataInputStream(new FileInputStream(dataFile)));

	return new DataFileAccessHandler(schema);
    }
}
