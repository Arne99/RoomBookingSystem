package suncertify.datafile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * The Class ByteCountingReaderTestBuilder.
 */
final class SchemaReaderTestBuilder {

    /** The columns. */
    private final List<ColumnMetaData> columns = new ArrayList<ColumnMetaData>();

    /** The identifier. */
    private int identifier;

    /** The record length. */
    private int recordLength;

    /** The offset. */
    private int offset;

    /**
     * Instance.
     * 
     * @return the byte counting reader test builder
     */
    static SchemaReaderTestBuilder instance() {

	return new SchemaReaderTestBuilder();
    }

    /**
     * Creates the header.
     * 
     * @param identifier
     *            the identifier
     * @param offset
     *            the offset
     * @param recordLength
     *            the record length
     * @return the byte counting reader test builder
     */
    SchemaReaderTestBuilder createHeader(final int identifier,
	    final int offset, final int recordLength) {
	this.identifier = identifier;
	this.recordLength = recordLength;
	this.offset = offset;
	return this;
    }

    /**
     * Adds the column.
     * 
     * @param nameLength
     *            the name length
     * @param name
     *            the name
     * @param columnLength
     *            the column length
     * @return the byte counting reader test builder
     */
    SchemaReaderTestBuilder addColumn(final short nameLength,
	    final String name, final short columnLength) {
	columns.add(new ColumnMetaData(columnLength, nameLength, name));
	return this;
    }

    /**
     * Builds the.
     * 
     * @return the byte counting reader
     */
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

	    final String firstString = (stringReturnValues.isEmpty()) ? ""
		    : stringReturnValues.remove(0);
	    when(reader.readString(anyInt())).thenReturn(firstString,
		    stringReturnValues.toArray(new String[] {}));

	    when(reader.getCount()).thenReturn(offset);
	    reader.closeStream();

	} catch (final IOException e) {
	    e.printStackTrace();
	}
	return reader;
    }

    /**
     * The Class ColumnMetaData.
     */
    private static class ColumnMetaData {

	/** The column length. */
	private final short columnLength;

	/** The name length. */
	private final short nameLength;

	/** The name. */
	private final String name;

	/**
	 * Instantiates a new column meta data.
	 * 
	 * @param columnLength
	 *            the column length
	 * @param nameLength
	 *            the name length
	 * @param name
	 *            the name
	 */
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

	/**
	 * Gets the column length.
	 * 
	 * @return the column length
	 */
	public short getColumnLength() {
	    return columnLength;
	}

	/**
	 * Gets the name length.
	 * 
	 * @return the name length
	 */
	public short getNameLength() {
	    return nameLength;
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
	    return name;
	}

	@Override
	public boolean equals(final Object object) {
	    throw new UnsupportedOperationException();
	}

	@Override
	public int hashCode() {
	    throw new UnsupportedOperationException();
	}
    }
}
