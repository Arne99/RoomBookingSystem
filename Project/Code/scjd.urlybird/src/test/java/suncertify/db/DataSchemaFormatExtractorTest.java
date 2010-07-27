package suncertify.db;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import org.junit.Test;

public class DataSchemaFormatExtractorTest {

    private DataSchemaFormatExtractor extractor;

    @Test
    public final void DataSchemaFormatExtractorCreatesValidSchemaFormat() {

	byte dataFileFormatDescriptionByte = 0;
	byte byteOneNameLength = 0;
	byte byteTwoNameLength = 2;
	byte firstNameByte = 'o';
	byte secondNameByte = 'k';

	byte[] input = new byte[] { dataFileFormatDescriptionByte,
		dataFileFormatDescriptionByte, dataFileFormatDescriptionByte,
		byteOneNameLength, byteTwoNameLength, firstNameByte,
		secondNameByte };
	DataInputStream dataStream = new DataInputStream(
		new ByteArrayInputStream(input));

	DataFileFormat format = mock(DataFileFormat.class);
	int numberOfDataFileDescriptionBytes = 3;
	when(format.getFileFormatDescriptionLength()).thenReturn(
		numberOfDataFileDescriptionBytes);
	when(format.getNumberOfFields()).thenReturn(1);

	extractor = new DataSchemaFormatExtractor(format);
	DataSchemaFormat schema = extractor.extractDataSchemaFormat(dataStream,
		format);

	DataFieldFormat<String> firstDataFieldFormat = schema
		.getFormatForField(0);
	assertEquals("" + firstNameByte + secondNameByte,
		firstDataFieldFormat.getFieldName());
    }
}
