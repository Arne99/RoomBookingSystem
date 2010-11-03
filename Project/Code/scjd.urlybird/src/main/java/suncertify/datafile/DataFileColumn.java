package suncertify.datafile;

final class DataFileColumn {

    private final String name;
    private final int size;
    private final int startIndex;
    private final int endIndex;

    static DataFileColumn create(final String name,
	    final int startIndexInRecord, final int size) {

	final int endIndex = startIndexInRecord + size;
	return new DataFileColumn(name, startIndexInRecord, endIndex, size);
    }

    private DataFileColumn(final String name, final int startIndexInRecord,
	    final int endIndexInRecord, final int size) {
	this.name = name;
	this.startIndex = startIndexInRecord;
	endIndex = endIndexInRecord;
	this.size = size;
    }

    @Override
    public String toString() {
	return "DataFileColumn" + " [ " + "name = " + name
		+ "; startPosition = " + startIndex + "; size = " + size
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
		&& this.startIndex == column.startIndex;
    }

    @Override
    public int hashCode() {
	int result = 17;
	result = 31 * result + name.hashCode();
	result = 31 * result + size;
	result = 31 * result + startIndex;
	return result;
    }

    int getEndIndex() {
	return endIndex;
    }

    String getName() {
	return name;
    }

    int getStartIndex() {
	return startIndex;
    }

    int size() {
	return size;
    }
}
