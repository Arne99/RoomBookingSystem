package suncertify.datafile;

class DataFileColumn {

    private final String name;
    private final int length;

    DataFileColumn(final String name, final int length) {
	this.name = name;
	this.length = length;
    }

    @Override
    public String toString() {
	return "DataFileColumn" + "[" + "name = " + name + "; length = "
		+ length;
    }
}
