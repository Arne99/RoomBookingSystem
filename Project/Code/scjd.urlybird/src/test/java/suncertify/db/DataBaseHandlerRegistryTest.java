package suncertify.db;

import static org.mockito.Mockito.mock;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class DataBaseHandlerRegistryTest {

    private DatabaseHandlerRegistry registry;

    @Test
    public final void databaseHandlerRegistry_Finds_DataFile_Handler_For_Valid_Cookie() {

	final File fileMock = mock(File.class);
	final DatabaseHandler dbHandlerMock = mock(DatabaseHandler.class);
	final List<DatabaseHandler> validDbHandler = new ArrayList<DatabaseHandler>(
		Arrays.asList(dbHandlerMock));
	registry = new DatabaseHandlerRegistry(validDbHandler);

	registry.createHandlerForDataFile(fileMock);
    }

}
