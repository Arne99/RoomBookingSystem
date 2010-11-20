package suncertify.datafile;

import java.util.List;

import suncertify.db.Record;

final class InValidRecord implements Record {

    private static final int HASH_CODE_VALUE = 344567;

    @Override
    public boolean isValid() {
	return false;
    }

    @Override
    public List<String> getValues() {
	throw new UnsupportedOperationException(
		"this record is not valid, values are only gettable by valid records");
    }

    @Override
    public boolean matches(final Record recordToMatch) {
	return false;
    }

    @Override
    public boolean equals(final Object object) {

	if (this == object) {
	    return true;
	}
	if (!(object instanceof InValidRecord)) {
	    return false;
	}
	return true;
    }

    @Override
    public int hashCode() {
	return HASH_CODE_VALUE;
    }

}
