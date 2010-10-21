package suncertify.datafile;

import java.util.ArrayList;
import java.util.List;

import suncertify.db.DataSchema;

// TODO: Auto-generated Javadoc
/**
 * The Class DataFileSchema.
 */
public class DataFileSchema implements DataSchema {

    /** The columns. */
    private final ArrayList<DataFileColumn> columns;

    /** The record length. */
    private final int recordLength;

    /**
     * Instantiates a new data file schema.
     * 
     * @param columnMetaData
     *            the column meta data
     * @param recordLength
     *            the record length
     */
    public DataFileSchema(final List<DataFileColumn> columnMetaData,
	    final int recordLength) {
	this.recordLength = recordLength;
	this.columns = new ArrayList<DataFileColumn>(columnMetaData);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final DataFileSchema other = (DataFileSchema) obj;
	if (columns == null) {
	    if (other.columns != null) {
		return false;
	    }
	} else if (!columns.equals(other.columns)) {
	    return false;
	}
	if (recordLength != other.recordLength) {
	    return false;
	}
	return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see suncertify.db.DataSchema#getNumberOfColumns()
     */
    @Override
    public Object getNumberOfColumns() {
	return columns.size();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((columns == null) ? 0 : columns.hashCode());
	result = prime * result + recordLength;
	return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "DataFileSchema [columns=" + columns + ", recordLength="
		+ recordLength + "]";
    }

}
