package suncertify.datafile;

import static org.mockito.Mockito.*;

import org.junit.Test;

public class DataFileSchemaBuilderTests {

    @Test
    public void shouldThrowAnExceptionIfItGetsAFileWithANotSupportedIdentifier() {

	final DataFileSchemaFactory schemaBuilder = DataFileSchemaFactory
		.instance();

	final ByteFileReader reader = mock(ByteFileReader.class);
	// when(reader.readInt());

	// schemaBuilder.buildSchemaForDataFile(reader);
    }
}
