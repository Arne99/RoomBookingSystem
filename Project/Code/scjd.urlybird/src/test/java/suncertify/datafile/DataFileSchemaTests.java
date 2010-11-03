package suncertify.datafile;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;

import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * Tests for the Class {@link DataFileSchema}.
 */
public final class DataFileSchemaTests {

    /**
     * Should be equals to itself.
     */
    @Test
    public void shouldBeEqualsToItself() {

	final DataFileSchema schema = DataFileSchema.create(new DataFileHeader(
		12, 1234), Lists.newArrayList(DataFileColumn.create("Test", 12,
		100)), 1);
	assertThat(schema, is(equalTo(schema)));
    }

    /**
     * Schould be symmetric equal.
     */
    @Test
    public void schouldBeSymmetricEqual() {

	final DataFileHeader header = new DataFileHeader(12, 1234);
	final ArrayList<DataFileColumn> columns = Lists
		.newArrayList(DataFileColumn.create("Test", 12, 100));
	final int deletedIndex = 1;

	final DataFileSchema schemaOne = DataFileSchema.create(header, columns,
		deletedIndex);
	final DataFileSchema schemaTwo = DataFileSchema.create(header, columns,
		deletedIndex);

	assertThat(schemaOne, is(equalTo(schemaTwo)));
	assertThat(schemaTwo, is(equalTo(schemaOne)));
    }

    /**
     * Should be not equal to a different schema.
     */
    @Test
    public void shouldBeNotEqualToADifferentSchema() {

	final DataFileHeader header = new DataFileHeader(12, 1234);
	final ArrayList<DataFileColumn> columns = Lists
		.newArrayList(DataFileColumn.create("Test", 12, 100));

	final DataFileSchema schemaOne = DataFileSchema.create(header, columns,
		1);
	final DataFileSchema schemaTwo = DataFileSchema.create(header, columns,
		2);

	assertThat(schemaOne, is(not(equalTo(schemaTwo))));
    }

    /**
     * Should have the same hash code with an equal schema.
     */
    @Test
    public void shouldHaveTheSameHashCodeWithAnEqualSchema() {

	final DataFileHeader header = new DataFileHeader(12, 1234);
	final ArrayList<DataFileColumn> columns = Lists
		.newArrayList(DataFileColumn.create("Test", 12, 100));
	final int deletedIndex = 1;

	final DataFileSchema schemaOne = DataFileSchema.create(header, columns,
		deletedIndex);
	final DataFileSchema schemaTwo = DataFileSchema.create(header, columns,
		deletedIndex);

	assertThat(schemaOne, is(equalTo(schemaTwo)));
	assertThat(schemaTwo.hashCode(), is(equalTo(schemaOne.hashCode())));
    }

    /**
     * Should have an different hash code than a different schema.
     */
    @Test
    public void shouldHaveAnDifferentHashCodeThanADifferentSchema() {

	final DataFileHeader header = new DataFileHeader(12, 1234);
	final ArrayList<DataFileColumn> columns = Lists
		.newArrayList(DataFileColumn.create("Test", 12, 100));

	final DataFileSchema schemaOne = DataFileSchema.create(header, columns,
		1);
	final DataFileSchema schemaTwo = DataFileSchema.create(header, columns,
		2);

	assertThat(schemaOne, is(not(equalTo(schemaTwo))));
	assertThat(schemaOne.hashCode(), is(not(equalTo(schemaTwo.hashCode()))));
    }

    @Test
    public void shouldReturnTheSumOfAllColumnSizesAsTheRecordLength() {

	final int firstColumnSize = 23;
	final int secondColumnSize = 12;
	final int thirdColumnSize = 6;
	final int fourthColumnSize = 156;

	final int firstStartIndex = 0;
	final int secondStartIndex = firstStartIndex + firstColumnSize;
	final int thirdStartIndex = secondStartIndex + secondColumnSize;
	final int fourthStartIndex = thirdStartIndex + thirdColumnSize;

	final DataFileColumn firstColumn = DataFileColumn.create("test",
		firstStartIndex, firstColumnSize);
	final DataFileColumn secondColumn = DataFileColumn.create("test",
		secondStartIndex, secondColumnSize);
	final DataFileColumn thirdColumn = DataFileColumn.create("test",
		thirdStartIndex, thirdColumnSize);
	final DataFileColumn fourthColumn = DataFileColumn.create("test",
		fourthStartIndex, fourthColumnSize);

	final DataFileHeader header = new DataFileHeader(12, 1234);
	final DataFileSchema schema = DataFileSchema.create(header, Lists
		.newArrayList(firstColumn, secondColumn, thirdColumn,
			fourthColumn), 1);

	assertThat(schema.getRecordLength(), is(equalTo(firstColumnSize
		+ secondColumnSize + thirdColumnSize + fourthColumnSize)));
    }
}
