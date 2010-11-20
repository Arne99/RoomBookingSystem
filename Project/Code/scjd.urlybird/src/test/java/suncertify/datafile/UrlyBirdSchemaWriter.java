package suncertify.datafile;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.google.common.collect.Lists;

import suncertify.util.Pair;

public class UrlyBirdSchemaWriter {

    private final int identifier;
    private final int recordLength;
    private final ArrayList<Pair<byte[], Integer>> columns = Lists
	    .newArrayList();

    public UrlyBirdSchemaWriter(final int identifier, final int recordLength) {
	this.identifier = identifier;
	this.recordLength = recordLength;

    }

    static UrlyBirdSchemaWriter writeSchema(final int identifier,
	    final int recordLength) {

	return new UrlyBirdSchemaWriter(identifier, recordLength);
    }

    UrlyBirdSchemaWriter withColumn(final String name, final int length) {

	columns.add(new Pair<byte[], Integer>(name.getBytes(), length));
	return this;
    }

    void toFile(final File file) throws IOException {

	DataOutputStream outputStream = null;
	try {
	    outputStream = new DataOutputStream(new FileOutputStream(file));
	    writeHeaderInformation(outputStream);
	    writeColumnInformation(outputStream);
	} finally {
	    outputStream.close();
	}
    }

    private void writeColumnInformation(final DataOutputStream outputStream)
	    throws IOException {
	for (final Pair<byte[], Integer> column : columns) {
	    outputStream.writeShort(column.getLeft().length);
	    for (final Byte b : column.getLeft()) {
		outputStream.write(b);
	    }
	    outputStream.writeShort(column.getRigth());
	}
    }

    private void writeHeaderInformation(final DataOutputStream outputStream)
	    throws IOException {
	outputStream.writeInt(identifier);
	outputStream.writeInt(recordLength);
	outputStream.writeShort(columns.size());
    }

}
