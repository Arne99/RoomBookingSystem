package suncertify.datafile;

import static suncertify.util.DesignByContract.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import suncertify.db.Record;

final class DataFileRecord implements Record {

    private final List<String> values;

    public DataFileRecord(final List<String> values) {
	super();
	checkNotNull(values, "values");
	this.values = values;
    }

    @Override
    public boolean isValid() {
	return true;
    }

    @Override
    public List<String> getValues() {
	return new ArrayList<String>(values);
    }

    @Override
    public boolean matches(final Record recordToMatch) {
	throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(final Object object) {
	if (this == object) {
	    return true;
	}
	if (!(object instanceof DataFileRecord)) {
	    return false;
	}
	final DataFileRecord record = (DataFileRecord) object;
	return this.values.equals(record.values);
    }

    @Override
    public int hashCode() {
	int result = 17;
	result = 31 * result + values.hashCode();
	return result;
    }

    @Override
    public String toString() {
	return this.getClass().getSimpleName() + " " + values.toString();
    }
}
