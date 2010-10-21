package suncertify.datafile;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;

import suncertify.db.DataSchema;
import suncertify.db.UnsupportedDataSourceException;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating DataFileSchema objects.
 */
public class DataFileSchemaFactory {

    /**
     * Gets the single instance of DataFileSchemaFactory.
     * 
     * @param supportedFormat
     *            the supported format
     * @return single instance of DataFileSchemaFactory
     */
    public static DataFileSchemaFactory getInstance(
	    final DataFileFormat supportedFormat) {
	return new DataFileSchemaFactory(supportedFormat);
    }

    /** The supported format. */
    private final DataFileFormat supportedFormat;

    /**
     * Instantiates a new data file schema factory.
     * 
     * @param dataFormat
     *            the data format
     */
    private DataFileSchemaFactory(final DataFileFormat dataFormat) {
	super();
	this.supportedFormat = dataFormat;
    }

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
    public DataSchema createSchema(final DataInput input) throws IOException,
	    UnsupportedDataSourceException {

	if (!supportedFormat.supports(input.readInt())) {
	    throw new UnsupportedDataSourceException();
	}
	input.skipBytes(supportedFormat.getHeaderLengthInBytes());

	final ArrayList<DataFileColumn> columns = new ArrayList<DataFileColumn>();
	for (int i = 1; i <= supportedFormat.getNumberOfColumns(); i++) {

	    final int columnNameLengthInByte = input.readShort();
	    final StringBuilder sb = new StringBuilder();

	    for (int j = 1; j <= columnNameLengthInByte; j++) {
		sb.append((char) input.readByte());
	    }

	    final int columnSize = input.readShort();
	    columns.add(new DataFileColumn(sb.toString(), columnSize));
	}

	return new DataFileSchema(columns, supportedFormat.getRecordLength());
    }
}
