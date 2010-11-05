package suncertify.util;

public final class DesignByContract {

    private DesignByContract() {
	super();
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
