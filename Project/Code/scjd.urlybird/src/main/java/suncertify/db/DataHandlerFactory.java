package suncertify.db;

import java.io.DataInput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.activation.DataHandler;

public class DataHandlerFactory {

    private static DataHandlerFactory INSTANCE = new DataHandlerFactory();

    public static DataHandlerFactory getInstance() {
	return INSTANCE;
    }

    DataHandler createHandlerForDataSource(final DataInput source)
	    throws UnsupportedDataSourceException {

	source.readInt();
    }
}
