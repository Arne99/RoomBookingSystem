package suncertify.datafile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class ByteCountingReaderTestBuilder {

    private static final ByteCountingReaderTestBuilder INSTANCE = new ByteCountingReaderTestBuilder();

    private final List<ColumnMetaData> columns = new ArrayList<ColumnMetaData>();

    private final List<List<String>> records = new ArrayList<List<String>>();

    private int identifier;

    private int recordLength;

    private int byteCount;

    static ByteCountingReaderTestBuilder instance() {
	return INSTANCE;
    }

    ByteCountingReaderTestBuilder createHeader(final int identifier,
	    final int recordLength) {
	this.identifier = identifier;
	this.recordLength = recordLength;
	return this;
    }

    ByteCountingReaderTestBuilder addColumn(final short nameLength,
	    final String name, final short columnLength) {
	columns.add(new ColumnMetaData(columnLength, nameLength, name));
	return this;
    }

    ByteCountingReaderTestBuilder addRecord(final String... entrys) {

	if (entrys.length != columns.size()) {
	    throw new IllegalArgumentException(
		    "The Number of specified entrys does not fit with the number of columns! Columns: "
			    + columns.size() + "; Entries: " + entrys.length);
	}
	records.add(Arrays.asList(entrys));
	return this;
    }

    ByteCountingReaderTestBuilder setFinalByteCount(final int count) {
	byteCount = count;
	return this;
    }

    ByteCountingReader build() {

	final ByteCountingReader reader = mock(ByteCountingReader.class);
	try {

	    final List<Short> shortReturnValues = new ArrayList<Short>();
	    final List<String> stringReturnValues = new ArrayList<String>();
	    for (final ColumnMetaData column : columns) {
		shortReturnValues.add(column.getNameLength());
		shortReturnValues.add(column.getColumnLength());
		stringReturnValues.add(column.getName());
	    }

	    when(reader.readInt()).thenReturn(identifier, recordLength);

	    when(reader.readShort()).thenReturn((short) columns.size(),
		    shortReturnValues.toArray(new Short[] {}));

	    final String firstString = stringReturnValues.remove(0);
	    when(reader.readString(anyInt())).thenReturn(firstString,
		    stringReturnValues.toArray(new String[] {}));

	    when(reader.getCount()).thenReturn(byteCount);
	    reader.closeStream();

	} catch (final IOException e) {
	    e.printStackTrace();
	}
	return reader;
    }

    private static class ColumnMetaData {

	private final short columnLength;

	private final short nameLength;

	private final String name;

	public ColumnMetaData(final short columnLength, final short nameLength,
		final String name) {
	    super();

	    if (name.length() != nameLength) {
		throw new IllegalArgumentException(
			"The expected length of the name does not match the real name length");
	    }
	    this.columnLength = columnLength;
	    this.nameLength = nameLength;
	    this.name = name;
	}

	public short getColumnLength() {
	    return columnLength;
	}

	public short getNameLength() {
	    return nameLength;
	}

	public String getName() {
	    return name;
	}

    }
}
