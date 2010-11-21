package suncertify.datafile;

final class Utf8Decoder implements BytesToStringDecoder {

    @Override
    public String decodeBytesToString(final byte... bytes) {

	final StringBuilder builder = new StringBuilder();
	for (final byte b : bytes) {
	    if (b <= 9) {
		builder.append("" + b);
	    } else {
		builder.append((char) b);
	    }
	}
	return builder.toString().trim();
    }

}
