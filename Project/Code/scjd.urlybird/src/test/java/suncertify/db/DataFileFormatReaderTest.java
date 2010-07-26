package suncertify.db;

import static org.junit.Assert.*;

import org.junit.Test;

public class DataFileFormatReaderTest {

	private  DataFileFormatReader formatReader; 
	
	@Test
	public void dataFileFormatReaderReadsTheMagicCookie() {
		
		formatReader = new DataFileFormatReader();
	}
	
}
