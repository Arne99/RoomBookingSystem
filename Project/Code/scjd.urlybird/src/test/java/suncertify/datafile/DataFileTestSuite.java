package suncertify.datafile;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Suite for all Test for the Package datafile.
 * 
 * @author arnelandwehr
 * 
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ Utf8FileReaderTests.class,
	Utf8ByteCountingReaderTests.class, DataFileHandlerTests.class,
	DataFileSchemaFactoryTests.class, DataFileHeaderTests.class,
	DataFileColumnTests.class })
public class DataFileTestSuite {

}
