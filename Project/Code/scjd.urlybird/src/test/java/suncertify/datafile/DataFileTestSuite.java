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
@Suite.SuiteClasses({ UrlyBirdDataFileReaderTest.class,
	UrlyBirdSchemaFactoryTest.class, DataFileHeaderTest.class,
	DataFileColumnTest.class, UrlyBirdSchemaTest.class,
	DataFileAccessTest.class })
public class DataFileTestSuite {

}
