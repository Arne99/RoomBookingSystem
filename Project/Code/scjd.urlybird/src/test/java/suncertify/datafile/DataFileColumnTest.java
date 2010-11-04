package suncertify.datafile;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for the CLass {@link DataFileColumn}.
 */
public final class DataFileColumnTest {

    /**
     * Should be equals to itself.
     */
    @Test
    public void shouldBeEqualsToItself() {

	final DataFileColumn column = DataFileColumn.create("test", 12, 5674);
	assertEquals(column, column);
    }

    /**
     * Should be symmetric equals.
     */
    @Test
    public void shouldBeSymmetricEquals() {

	final String name = "test";
	final int startIndexInRecord = 12;
	final int size = 5674;
	final DataFileColumn columnOne = DataFileColumn.create(name,
		startIndexInRecord, size);
	final DataFileColumn columnTwo = DataFileColumn.create(name,
		startIndexInRecord, size);

	assertEquals(columnOne, columnTwo);
	assertEquals(columnTwo, columnOne);
    }

    /**
     * Should be not equals for different columns.
     */
    @Test
    public void shouldBeNotEqualsForDifferentColumns() {

	final String name = "test";
	final int startIndexInRecord = 12;

	final DataFileColumn columnOne = DataFileColumn.create(name,
		startIndexInRecord, 12);
	final DataFileColumn columnTwo = DataFileColumn.create(name,
		startIndexInRecord, 13);

	assertFalse(columnOne.equals(columnTwo));
    }

    /**
     * Should return the same hash code for equal columns.
     */
    @Test
    public void shouldReturnTheSameHashCodeForEqualColumns() {

	final String name = "test";
	final int startIndexInRecord = 12;
	final int size = 5674;

	final DataFileColumn columnOne = DataFileColumn.create(name,
		startIndexInRecord, size);
	final DataFileColumn columnTwo = DataFileColumn.create(name,
		startIndexInRecord, size);

	assertEquals(columnOne, columnTwo);
	assertEquals(columnOne.hashCode(), columnTwo.hashCode());
    }

    /**
     * Should return different hash codes for different columns.
     */
    @Test
    public void shouldReturnDifferentHashCodesForDifferentColumns() {

	final String name = "test";
	final int startIndexInRecord = 12;

	final DataFileColumn columnOne = DataFileColumn.create(name,
		startIndexInRecord, 12);
	final DataFileColumn columnTwo = DataFileColumn.create(name,
		startIndexInRecord, 13);

	assertFalse(columnOne.equals(columnTwo));
	assertTrue(columnOne.hashCode() != columnTwo.hashCode());
    }

    /**
     * Should return the start index plus the size as the end index.
     */
    @Test
    public void shouldReturnTheStartIndexPlusTheSizeMinusOneAsTheEndIndex() {

	final int size = 12343;
	final int startIndexInRecord = 45;
	final DataFileColumn column = DataFileColumn.create("test",
		startIndexInRecord, size);

	assertEquals(startIndexInRecord + size - 1, column.getEndIndex());
    }

}
