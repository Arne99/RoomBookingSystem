package suncertify.datafile;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * Tests for the Class {@link UrlyBirdSchema}.
 */
public final class UrlyBirdSchemaTest {

    private final DeletedFlag deletedFlag = new DeletedFlag(1, (byte) 1);

    /**
     * Should be equals to itself.
     */
    @Test
    public void shouldBeEqualsToItself() {

	final UrlyBirdSchema schema = new UrlyBirdSchema(new DataFileHeader(12,
		1234), Lists.newArrayList(DataFileColumn
		.create("Test", 12, 100)), deletedFlag, 12);
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
	final int recordLength = 123;

	final UrlyBirdSchema schemaOne = new UrlyBirdSchema(header, columns,
		deletedFlag, 123);
	final UrlyBirdSchema schemaTwo = new UrlyBirdSchema(header, columns,
		deletedFlag, recordLength);

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

	final UrlyBirdSchema schemaOne = new UrlyBirdSchema(header, columns,
		deletedFlag, 1);
	final UrlyBirdSchema schemaTwo = new UrlyBirdSchema(header, columns,
		deletedFlag, 2);

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
	final int recordLength = 123;

	final UrlyBirdSchema schemaOne = new UrlyBirdSchema(header, columns,
		deletedFlag, 123);
	final UrlyBirdSchema schemaTwo = new UrlyBirdSchema(header, columns,
		deletedFlag, recordLength);

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

	final UrlyBirdSchema schemaOne = new UrlyBirdSchema(header, columns,
		deletedFlag, 1);
	final UrlyBirdSchema schemaTwo = new UrlyBirdSchema(header, columns,
		deletedFlag, 2);

	assertThat(schemaOne, is(not(equalTo(schemaTwo))));
	assertThat(schemaOne.hashCode(), is(not(equalTo(schemaTwo.hashCode()))));
    }

    public void shouldReturnTheColumnsInDatabaseOrderAscendingOrderedByTheirStatIndex() {

	final DataFileHeader header = new DataFileHeader(12, 1234);
	final DataFileColumn firstColumn = DataFileColumn.create("firstColumn",
		0, 20);
	final DataFileColumn secondColumn = DataFileColumn.create(
		"secondColumn", 20, 20);
	final DataFileColumn thirdColumn = DataFileColumn.create("thirdColumn",
		40, 20);

	final ArrayList<DataFileColumn> columnsInWrongOrder = Lists
		.newArrayList(secondColumn, firstColumn, thirdColumn);
	final DataFileMetadata schema = new UrlyBirdSchema(header,
		columnsInWrongOrder, deletedFlag, 20);

	final List<DataFileColumn> schemaOrder = schema
		.getColumnsInDatabaseOrder();
	assertThat(schemaOrder.get(0), is(equalTo(firstColumn)));
	assertThat(schemaOrder.get(1), is(equalTo(secondColumn)));
	assertThat(schemaOrder.get(2), is(equalTo(thirdColumn)));
    }

}
