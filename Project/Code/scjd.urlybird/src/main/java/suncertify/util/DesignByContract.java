package suncertify.util;

public class DesignByContract {

    private DesignByContract() {

    }

    public static void checkNotNull(Object schouldBeNotNull, String paramName) {
	if (schouldBeNotNull == null) {
	    throw new IllegalArgumentException(paramName + " is null!");
	}
    }

    public static void checkPositiv(Number number) {
	if (number.intValue() <= 0) {
	    throw new IllegalArgumentException(number + " is <= 0 !");
	}
    }
}
