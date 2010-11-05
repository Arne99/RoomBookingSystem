package suncertify.util;

public class DesignByContract {

    private DesignByContract() {

    }

    public static void checkNotNull(final Object schouldBeNotNull,
	    final String paramName) {
	if (schouldBeNotNull == null) {
	    throw new IllegalArgumentException(paramName + " is null!");
	}
    }

    public static void checkPositiv(final Number number) {
	if (number.intValue() <= 0) {
	    throw new IllegalArgumentException(number + " is <= 0 !");
	}
    }

    public static void checkNotNegativ(final Number number) {
	if (number.intValue() < 0) {
	    throw new IllegalArgumentException(number + " is < 0 !");
	}
    }

}
