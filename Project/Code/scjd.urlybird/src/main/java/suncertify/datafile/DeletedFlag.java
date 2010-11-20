package suncertify.datafile;

final class DeletedFlag {
    private final int deletedFlagIndex;
    private final byte deletedFlagValue;

    DeletedFlag(final int deletedFlagIndex, final byte deletedFlagValue) {
	this.deletedFlagIndex = deletedFlagIndex;
	this.deletedFlagValue = deletedFlagValue;
    }

    int getIndex() {
	return deletedFlagIndex;
    }

    byte getIdentifier() {
	return deletedFlagValue;
    }
}