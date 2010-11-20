package suncertify.db;

import java.util.List;

public interface Record {

    boolean isValid();

    List<String> getValues();

    boolean matches(Record recordToMatch);

}
