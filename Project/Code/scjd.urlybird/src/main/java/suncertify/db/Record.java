package suncertify.db;

public interface Record {

    boolean isValid();

    String[] getValues();

    boolean matches(Record recordToMatch);

}
