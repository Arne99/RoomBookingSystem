package suncertify.datafile;

final class DataFileColumn {

    private final String name;
    private final int size;
    private final int startPosition;

    DataFileColumn(final String name, final int startPositionInRecord,
	    final int size) {
	this.name = name;
	this.startPosition = startPositionInRecord;
	this.size = size;
    }

    @Override
    public String toString() {
	return "DataFileColumn" + " [ " + "name = " + name
		+ "; startPosition = " + startPosition + "; size = " + size
		+ " ] ";
    }

    @Override
    public boolean equals(final Object object) {
	if (object == this) {
	    return true;
	}
	if (!(object instanceof DataFileColumn)) {
	    return false;
	}
	final DataFileColumn column = (DataFileColumn) object;
	return this.name.equals(column.name) && this.size == column.size
		&& this.startPosition == column.startPosition;
    }

    @Override
    public int hashCode() {
	int result = 17;
	result = 31 * result + name.hashCode();
	result = 31 * result + size;
	result = 31 * result + startPosition;
	return result;
    }

    int getEndPosition() {
	return startPosition + size - 1;
    }

    String getName() {
	return name;
    }

    int getStartPosition() {
	return startPosition;
    }

    int size() {
	return size;
    }
}
