package suncertify.datafile;

import static suncertify.util.DesignByContract.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import suncertify.db.Record;

final class RecordFactory {

    private final DataFileSchema schema;
    private final BytesToStringDecoder decoder;

    public RecordFactory(final DataFileSchema schema,
	    final BytesToStringDecoder decoder) {
	super();
	this.schema = schema;
	this.decoder = decoder;
    }

    public Record createRecordFrom(final byte[] buffer) {

	checkNotNull(buffer, "buffer");

	if (schema.isBufferAValidRecord(buffer)) {
	    return createValidRecord(buffer);
	}

	return new InValidRecord();
    }

    private Record createValidRecord(final byte[] buffer) {

	final List<String> values = new ArrayList<String>();
	for (final DataFileColumn column : schema.getColumnsInDatabaseOrder()) {
	    final byte[] bytesInColumn = Arrays.copyOfRange(buffer,
		    column.getStartIndex(), column.getEndIndex());
	    final String value = decoder.decodeBytesToString(bytesInColumn);
	    values.add(value);
	}

	return new ValidRecord(values);
    }

}
