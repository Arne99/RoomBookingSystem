package suncertify.db;

import java.io.DataInput;
import java.io.IOException;

/**
 * 
 * @author arnelandwehr
 * 
 */
public class DataFileFormatReader {

    private final int dataFormatIdentifier;
    private final int dataFormatHeaderLength;

    public DataFileFormatReader(int dataFormatIdentifier,
	    int dataFormatHeaderLength) {
	super();
	this.dataFormatIdentifier = dataFormatIdentifier;
	this.dataFormatHeaderLength = dataFormatHeaderLength;
    }

    public DataFileFormat extractDataFileFormat(DataInput input)
	    throws IOException, InvalidDataFormatException {

	if (dataFormatIdentifier != input.readInt()) {
	    throw new InvalidDataFormatException();
	}

	int recordLength = input.readInt();
	int numberOfFields = input.readShort();

	DataFileFormat formatInformation = new DataFileFormat(recordLength,
		numberOfFields, dataFormatHeaderLength);

	return formatInformation;
    }
}
