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
@Suite.SuiteClasses({ Utf8FileReaderTest.class,
	Utf8ByteCountingReaderTest.class, DataFileHandlerTest.class,
	DataFileSchemaFactoryTest.class, DataFileHeaderTest.class,
	DataFileColumnTest.class, DataFileSchemaTest.class })
public class DataFileTestSuite {

}
