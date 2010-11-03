package suncertify.datafile;

import static org.junit.Assert.*;

import org.junit.Test;

public final class DataFileColumnTests {

    @Test
    public void shouldBeEqualsToItself() {

	final DataFileColumn column = DataFileColumn.create("test", 12, 5674);
	assertEquals(column, column);
    }

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

}
