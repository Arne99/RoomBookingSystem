package suncertify.db;

import java.util.ArrayList;
import java.util.List;

public class DatabaseSchemaDescription {

	private final int magicCookieValue;
	
	private final int recordLength;
	
	private final List<DatabaseFieldDescription> fieldDescriptions;

	public DatabaseSchemaDescription(int magicCookieValue, int recordLength,
			List<DatabaseFieldDescription> fields) {
		super();
		this.magicCookieValue = magicCookieValue;
		this.recordLength = recordLength;
		this.fieldDescriptions = new ArrayList<DatabaseFieldDescription>(fields);
	}

	public int getMagicCookieValue() {
		return magicCookieValue;
	}

	public int getRecordLength() {
		return recordLength;
	}
	
	public int getNumberOfFields() {
		return fieldDescriptions.size();
	}
	
	public DatabaseFieldDescription getFieldDescription(int index) {
		return fieldDescriptions.get(index);
	}

	@Override
	public String toString() {
		return "DatabaseSchemaDescription [magicCookieValue="
				+ magicCookieValue + ", recordLength=" + recordLength
				+ ", fieldDescriptions=" + fieldDescriptions + "]";
	}
	
	
	
}