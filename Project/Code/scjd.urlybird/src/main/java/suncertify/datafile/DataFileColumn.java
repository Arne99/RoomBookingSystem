package suncertify.datafile;

class DataFileColumn {

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
