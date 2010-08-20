package suncertify.db;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.DataInput;
import java.io.IOException;
import java.util.HashMap;

import org.junit.After;
import org.junit.Test;

public class DatabaseHandlerRegistryTest {

    private final DatabaseHandlerRegistry registry = DatabaseHandlerRegistry
	    .getInstance();

    @Test
    public final void registry_finds_dataFile_handler_for_valid_key()
	    throws IOException {

	final int keyForTheDbHandler = 13;
	final DatabaseHandler dbHandlerMock = mock(DatabaseHandler.class);
	final DataInput input = createDataInputThatReturnsKey(keyForTheDbHandler);

	registry.init(new HashMap<Integer, DatabaseHandler>() {
	    {
		put(keyForTheDbHandler, dbHandlerMock);
	    }
	});

	final DatabaseHandler dbHandlerFromRegistry = DatabaseHandlerRegistry
		.getInstance().findHandlerForDataFile(input);

	assertEquals(
		"Registry does not find a valid DataFileHandler under the specified key",
		dbHandlerMock, dbHandlerFromRegistry);
    }

    @Test
    public final void registry_returns_null_for_invalid_key()
	    throws IOException {

	final int keyForTheDbHandler = 13;
	final DatabaseHandler dbHandlerMock = mock(DatabaseHandler.class);
	final int invalidKey = keyForTheDbHandler + 1;
	final DataInput input = createDataInputThatReturnsKey(invalidKey);

	registry.init(new HashMap<Integer, DatabaseHandler>() {
	    {
		put(keyForTheDbHandler, dbHandlerMock);
	    }
	});

	final DatabaseHandler dbHandlerFromRegistry = DatabaseHandlerRegistry
		.getInstance().findHandlerForDataFile(input);

	assertNull("Registry should not find a handler with an invalid key",
		dbHandlerFromRegistry);
    }

    @Test(expected = IllegalStateException.class)
    public final void registry_throws_exception_if_already_initiliased() {
	registry.init(new HashMap<Integer, DatabaseHandler>());
	registry.init(new HashMap<Integer, DatabaseHandler>());
    }

    @Test(expected = IllegalStateException.class)
    public final void registry_throws_exception_if_not_initiliased()
	    throws IOException {
	final DataInput input = mock(DataInput.class);
	registry.findHandlerForDataFile(input);
    }

    @Test
    public final void registry_throws_no_exception_if_resetted_between_inits() {
	registry.init(new HashMap<Integer, DatabaseHandler>());
	registry.reset();
	registry.init(new HashMap<Integer, DatabaseHandler>());
    }

    @After
    public void tearDown() {
	registry.reset();
    }

    private DataInput createDataInputThatReturnsKey(final int keyForTheDbHandler)
	    throws IOException {
	final DataInput input = mock(DataInput.class);
	when(input.readInt()).thenReturn(keyForTheDbHandler);
	return input;
    }
}
