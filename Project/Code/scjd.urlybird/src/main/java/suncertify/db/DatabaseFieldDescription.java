package suncertify.db;

/**
 * 
 * @author arnelandwehr
 * 
 */
public class DatabaseFieldDescription {

    private final String fieldName;

    private final int fieldLength;

    public DatabaseFieldDescription(String fieldName, int fieldLength) {
	super();
	this.fieldName = fieldName;
	this.fieldLength = fieldLength;
    }

    public final String getFieldName() {
	return fieldName;
    }

    public final int getFieldLength() {
	return fieldLength;
    }

    @Override
    public final int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + fieldLength;
	result = prime * result
		+ ((fieldName == null) ? 0 : fieldName.hashCode());
	return result;
    }

    @Override
    public final boolean equals(final Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	DatabaseFieldDescription other = (DatabaseFieldDescription) obj;
	if (fieldLength != other.fieldLength)
	    return false;
	if (fieldName == null) {
	    if (other.fieldName != null)
		return false;
	} else if (!fieldName.equals(other.fieldName))
	    return false;
	return true;
    }

    @Override
    public final String toString() {
	return "DatabaseFieldDescription [fieldName=" + fieldName
		+ ", fieldLength=" + fieldLength + "]";
    }

}
