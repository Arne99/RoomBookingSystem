package suncertify.db;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;
import org.mockito.InOrder;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DataFileSchemaFactoryTest {

    DataFileSchemaFactory factory;

    @Test
    public final void createdSchemaContainsTheRightInformationFor2Columns()
	    throws IOException, UnsupportedDataSourceException {

	final int singleColumn = 1;
	final String columnName = "Test";
	final int columnLength = 100;
	final int recordLength = 100;

	final DataFileFormat supportedFormat = mock(DataFileFormat.class);
	when(supportedFormat.supports(anyInt())).thenReturn(true);
	when(supportedFormat.getNumberOfColumns()).thenReturn(singleColumn);
	when(supportedFormat.getRecordLength()).thenReturn(recordLength);

	final DataInput dataInputMock = mock(DataInput.class);
	when(dataInputMock.readShort()).thenReturn((short) columnName.length(),
		(short) columnLength);
	when(dataInputMock.readByte()).thenReturn((byte) 'T', (byte) 'e',
		(byte) 's', (byte) 't');

	factory = DataFileSchemaFactory.getInstance(supportedFormat);
	final DataSchema schema = factory.createSchema(dataInputMock);

	final DataSchema expectedSchema = new DataFileSchema(
		Collections.singletonList(new RawColumnMetaData(columnName,
			columnLength)), recordLength);
	assertEquals(expectedSchema, schema);
    }

    @Test
    public final void createdSchemaContainsTheRightInformationForOneColumn()
	    throws IOException, UnsupportedDataSourceException {

	final int twoColumns = 2;
	final String firstColumnName = "Test1";
	final int firstColumnLength = 100;
	final String secondColumnName = "Hello";
	final int secondColumnLenght = 5;
	final int recordLength = 100;

	final DataFileFormat supportedFormat = mock(DataFileFormat.class);
	when(supportedFormat.supports(anyInt())).thenReturn(true);
	when(supportedFormat.getNumberOfColumns()).thenReturn(twoColumns);
	when(supportedFormat.getRecordLength()).thenReturn(recordLength);

	final DataInput dataInputMock = mock(DataInput.class);
	when(dataInputMock.readShort()).thenReturn(
		(short) firstColumnName.length(), (short) firstColumnLength,
		(short) secondColumnName.length(), (short) secondColumnLenght);
	when(dataInputMock.readByte()).thenReturn((byte) 'T', (byte) 'e',
		(byte) 's', (byte) 't', (byte) '1', (byte) 'H', (byte) 'e',
		(byte) 'l', (byte) 'l', (byte) 'o');

	factory = DataFileSchemaFactory.getInstance(supportedFormat);
	final DataSchema schema = factory.createSchema(dataInputMock);

	final DataSchema expectedSchema = new DataFileSchema(
		new ArrayList<ColumnMetaData>(Arrays.asList(
			new RawColumnMetaData(firstColumnName,
				firstColumnLength), new RawColumnMetaData(
				secondColumnName, secondColumnLenght))),
		recordLength);

	assertEquals(expectedSchema, schema);
    }

    @Test
    public final void createdSchemaHas0ColumnsIfDataFileFormatHas0ColumnDeclared()
	    throws IOException, UnsupportedDataSourceException {

	final DataFileFormat supportedFormat = mock(DataFileFormat.class);
	when(supportedFormat.supports(anyInt())).thenReturn(true);

	final DataInput dataInputMock = mock(DataInput.class);

	factory = DataFileSchemaFactory.getInstance(supportedFormat);
	final DataSchema schema = factory.createSchema(dataInputMock);

	assertSame(0, schema.getNumberOfColumns());
    }

    @Test
    public final void createdSchemaHas3ColumnsIfDataFileFormatHas3ColumnDeclared()
	    throws IOException, UnsupportedDataSourceException {

	final DataFileFormat supportedFormat = mock(DataFileFormat.class);
	when(supportedFormat.supports(anyInt())).thenReturn(true);
	final int numberOfColumnsInDataFileFormat = 3;
	when(supportedFormat.getNumberOfColumns()).thenReturn(
		numberOfColumnsInDataFileFormat);

	final DataInput dataInputMock = mock(DataInput.class);

	factory = DataFileSchemaFactory.getInstance(supportedFormat);
	final DataSchema schema = factory.createSchema(dataInputMock);

	assertSame(numberOfColumnsInDataFileFormat, schema.getNumberOfColumns());
    }

    @Test
    public final void createdSchemaSkipsHeaderInformationBlockInTheRigthOrder()
	    throws IOException, UnsupportedDataSourceException {

	final int headerBlockLenght = 10;
	final int oneColumnToRead = 1;
	final DataFileFormat supportedFormat = mock(DataFileFormat.class);
	when(supportedFormat.supports(anyInt())).thenReturn(true);
	when(supportedFormat.getHeaderLengthInBytes()).thenReturn(
		headerBlockLenght);
	when(supportedFormat.getNumberOfColumns()).thenReturn(oneColumnToRead);

	final DataInput dataInputMock = mock(DataInput.class);

	factory = DataFileSchemaFactory.getInstance(supportedFormat);
	factory.createSchema(dataInputMock);

	final InOrder inOrder = inOrder(dataInputMock);
	inOrder.verify(dataInputMock, atLeastOnce()).readInt();
	inOrder.verify(dataInputMock, atLeastOnce()).skipBytes(
		headerBlockLenght);
	inOrder.verify(dataInputMock, atLeastOnce()).readShort();
    }

    @Test
    public final void createSchemaCreatesSchemaForValidIdentifier()
	    throws IOException, UnsupportedDataSourceException {

	final DataInput dataInputMock = mock(DataInput.class);
	final int identifierForSupportedDataFormat = 10;
	when(dataInputMock.readInt()).thenReturn(
		identifierForSupportedDataFormat);
	final DataFileFormat supportedFormat = mock(DataFileFormat.class);
	when(supportedFormat.supports(identifierForSupportedDataFormat))
		.thenReturn(true);

	factory = DataFileSchemaFactory.getInstance(supportedFormat);
	final DataSchema schema = factory.createSchema(dataInputMock);

	assertNotNull("Factory didnt create an Schema for an valid Identifier",
		schema);
    }

    @Test(expected = UnsupportedDataSourceException.class)
    public final void createSchemaThrowsExceptionIfDataFileIdentifierIsInvalid()
	    throws IOException, UnsupportedDataSourceException {

	final int identifierForNotSupportedDataFormat = 10;
	final DataInput dataInputMock = mock(DataInput.class);
	when(dataInputMock.readInt()).thenReturn(
		identifierForNotSupportedDataFormat);

	final DataFileFormat supportedFormat = mock(DataFileFormat.class);
	when(supportedFormat.supports(identifierForNotSupportedDataFormat))
		.thenReturn(false);

	factory = DataFileSchemaFactory.getInstance(supportedFormat);
	factory.createSchema(dataInputMock);
    }

}
