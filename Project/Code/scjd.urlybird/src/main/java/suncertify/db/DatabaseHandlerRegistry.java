package suncertify.db;

import java.io.DataInput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DatabaseHandlerRegistry {

    private static DatabaseHandlerRegistry INSTANCE = new DatabaseHandlerRegistry();

    public static DatabaseHandlerRegistry getInstance() {
	return INSTANCE;
    }

    private Map<Integer, DatabaseHandler> handlers;

    private boolean isInitialised;

    public DatabaseHandler findHandlerForDataFile(final DataInput input)
	    throws IOException {

	if (!isInitialised) {
	    throw new IllegalStateException(
		    "the DatabaseHandlerRegistry has not been inilialised yet!");
	}

	final int dataFileIdentifier = input.readInt();
	return handlers.get(dataFileIdentifier);
    }

    public void init(final Map<Integer, DatabaseHandler> handlers) {

	if (isInitialised) {
	    throw new IllegalStateException(
		    "the DatabaseHandlerRegistry has already been inilialised yet!");
	}

	this.handlers = new HashMap<Integer, DatabaseHandler>(handlers);
	isInitialised = true;
    }

    public void reset() {
	handlers.clear();
	isInitialised = false;
    }
}
