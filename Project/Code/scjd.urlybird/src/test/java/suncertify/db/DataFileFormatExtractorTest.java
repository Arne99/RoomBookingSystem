package suncertify.db;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.DataInput;
import java.io.IOException;

import org.junit.Test;

/**
 * Tests for the Class {@link DataFileFormatExtractor}.
 * 
 * @author arnelandwehr
 */
public class DataFileFormatExtractorTest {

    /** the {@code DataFileFormatReader} to test. */
    private DataFileFormatExtractor formatExtractor;

    /**
     * Tests the {@code DataFileFormatReader} for throwing an
     * {@code InvalidDataFormatException} if the DataFile contains an invalid
     * <b>MagicCookie</b>.
     * 
     * @throws IOException
     *             caught by JUnit
     * @throws InvalidDataFormatException
     *             expected Exception
     */
    @Test(expected = InvalidDataFormatException.class)
    public final void dataFileFormatReaderThrowsExceptionForInvalidMagicCookie()
	    throws IOException, InvalidDataFormatException {

	int expectedMagicCookie = 123;
	int expectedFileFormatBlockLength = 10;

	DataInput dataFileStream = mock(DataInput.class);
	int invalidMagicCookie = 124;
	when(dataFileStream.readInt()).thenReturn(invalidMagicCookie);

	formatExtractor = new DataFileFormatExtractor(expectedMagicCookie,
		expectedFileFormatBlockLength);
	formatExtractor.extractDataFileFormat(dataFileStream);
    }

    /**
     * Tests if the {@code DataFileFormatReader} extracts the right information.
     * These are:
     * <ol>
     * <li>the magicCookie value</li>
     * <li>the fataFile Identifier</li>
     * <li>the total record length</li>
     * <li>the number of fields</li>
     * <li>the schema informations</li>
     * </ol>
     * 
     * 
     * @throws IOException
     *             caught by JUnit
     * @throws InvalidDataFormatException
     *             caught by JUnit
     */
    @Test
    public final void dataFileFormatReaderCreatesCorrectDataFileFormat()
	    throws IOException, InvalidDataFormatException {

	int expectedMagicCookie = 124;
	int expectedRecordLength = 1234;
	int expectedNumberOfFields = 2;
	int expectedFileFormatBlockLength = 10;

	DataInput dataFileStream = mock(DataInput.class);
	when(dataFileStream.readInt()).thenReturn(expectedMagicCookie,
		expectedRecordLength);
	when(dataFileStream.readShort()).thenReturn(
		(short) expectedNumberOfFields,
		(short) expectedFileFormatBlockLength);

	formatExtractor = new DataFileFormatExtractor(expectedMagicCookie,
		expectedFileFormatBlockLength);
	DataFileFormat extractedFormat = formatExtractor
		.extractDataFileFormat(dataFileStream);

	DataFileFormat expectedFormat = new DataFileFormat(
		expectedRecordLength, expectedNumberOfFields,
		expectedFileFormatBlockLength);
	assertEquals("DataFileFormats are not equals!", expectedFormat,
		extractedFormat);
    }
}
