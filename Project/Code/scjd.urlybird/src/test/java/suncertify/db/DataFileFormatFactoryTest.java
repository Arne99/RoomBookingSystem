package suncertify.db;

import java.io.DataInput;
import java.io.IOException;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DataFileFormatFactoryTest {

    private DataFileFormatFactory factory;

    @Test
    public void createdFormatContainsCorrectHeaderLength() throws IOException,
	    UnsupportedDataSourceException {

	final DataInput input = mock(DataInput.class);
	final int validIdentifier = 10;
	when(input.readInt()).thenReturn(validIdentifier);

	final int anyHeaderLength = 100;
	factory = DataFileFormatFactory.getInstance(validIdentifier,
		anyHeaderLength);
	final DataFileFormat format = factory.createFormat(input);

	assertEquals(anyHeaderLength, format.getHeaderLengthInBytes());
    }

    @Test
    public void createdFormatContainsCorrectNumberOfFields()
	    throws IOException, UnsupportedDataSourceException {

	final DataInput input = mock(DataInput.class);
	final int validIdentifier = 10;
	final short numberOfColumns = 100;
	when(input.readInt()).thenReturn(validIdentifier);
	when(input.readShort()).thenReturn(numberOfColumns);

	final int anyHeaderLength = 0;
	factory = DataFileFormatFactory.getInstance(validIdentifier,
		anyHeaderLength);
	final DataFileFormat format = factory.createFormat(input);

	assertEquals(numberOfColumns, format.getNumberOfColumns());
    }

    @Test
    public void createdFormatContainsCorrectRecordLength() throws IOException,
	    UnsupportedDataSourceException {

	final DataInput input = mock(DataInput.class);
	final int validIdentifier = 10;
	final int recordLength = 100;
	when(input.readInt()).thenReturn(validIdentifier, recordLength);

	final int anyHeaderLength = 0;
	factory = DataFileFormatFactory.getInstance(validIdentifier,
		anyHeaderLength);
	final DataFileFormat format = factory.createFormat(input);

	assertEquals(recordLength, format.getRecordLength());
    }

    @Test
    public void createdFormatDoesntSupportInvalidIdentifier()
	    throws IOException, UnsupportedDataSourceException {

	final DataInput input = mock(DataInput.class);
	final int validIdentifier = 10;
	when(input.readInt()).thenReturn(validIdentifier);

	final int anyHeaderLength = 0;
	factory = DataFileFormatFactory.getInstance(validIdentifier,
		anyHeaderLength);
	final DataFileFormat format = factory.createFormat(input);

	final int invalidIdentifier = 9;
	assertFalse(format.supports(invalidIdentifier));
    }

    @Test
    public void createdFormatSupportsValidIdentifier() throws IOException,
	    UnsupportedDataSourceException {

	final DataInput input = mock(DataInput.class);
	final int validIdentifier = 10;
	when(input.readInt()).thenReturn(validIdentifier);

	final int anyHeaderLength = 0;
	factory = DataFileFormatFactory.getInstance(validIdentifier,
		anyHeaderLength);
	final DataFileFormat format = factory.createFormat(input);

	assertTrue(format.supports(validIdentifier));
    }

    @Test(expected = UnsupportedDataSourceException.class)
    public void factoryThrowsExceptionIfDataSourceContainsInValidIdentifier()
	    throws IOException, UnsupportedDataSourceException {

	final DataInput input = mock(DataInput.class);
	final int invalidIdentifier = 12;
	when(input.readInt()).thenReturn(invalidIdentifier);

	final int anyHeaderLength = 0;
	final int supportedIdentifier = invalidIdentifier + 1;
	factory = DataFileFormatFactory.getInstance(supportedIdentifier,
		anyHeaderLength);
	factory.createFormat(input);
    }
}
