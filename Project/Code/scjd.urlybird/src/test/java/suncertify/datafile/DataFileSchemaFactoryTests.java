package suncertify.datafile;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class DataFileSchemaFactoryTests {

    @Test
    public void shouldThrowAnExceptionIfItGetsAFileWithANotSupportedIdentifier()
	    throws IOException, UnsupportedDataFileFormatException {

	final ByteCountingReader reader = ByteCountingReaderTestBuilder
		.instance().createHeader(257, 12)
		.addColumn((short) 5, "Hello", (short) 10)
		.addColumn((short) 4, "Name", (short) 64)
		.setFinalByteCount(100).build();

	final DataFileSchemaFactory schemaBuilder = DataFileSchemaFactory
		.instance();
	final DataFileSchema schema = schemaBuilder
		.buildSchemaForDataFile(reader);

	assertNotNull(schema);

    }
}
