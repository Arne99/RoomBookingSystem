package suncertify.db;

import java.io.DataInput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DatabaseHandlerRegistry {

    /**
     * Ein guarded by einführen
     */
    private static DatabaseHandlerRegistry INSTANCE = null;

    public static synchronized DatabaseHandlerRegistry getInstance() {

	if (INSTANCE == null) {
	    throw new IllegalStateException(
		    "the DatabaseHandlerRegistry has not been inilialised yet!");
	}

	return INSTANCE;
    }

    public synchronized static void init(
	    final Map<Integer, DatabaseHandler> handlers) {

	if (INSTANCE != null) {
	    throw new IllegalStateException(
		    "the DatabaseHandlerRegistry is already initialiesed!");
	}
	INSTANCE = new DatabaseHandlerRegistry(handlers);
    }

    public static synchronized void reset() {
	INSTANCE = null;
    }

    private final Map<Integer, DatabaseHandler> handlers;

    private DatabaseHandlerRegistry(final Map<Integer, DatabaseHandler> handlers) {
	this.handlers = new HashMap<Integer, DatabaseHandler>(handlers);

    }

    public DatabaseHandler findHandlerForDataFile(final DataInput input)
	    throws IOException {

	final int dataFileIdentifier = input.readInt();
	return handlers.get(dataFileIdentifier);
    }
}
