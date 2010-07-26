package suncertify.db;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class Main {

	public static void main(String[] args) throws IOException {

		File database = new File(
				"/Users/arnelandwehr/Coden/Sun Certified Java Developer/TestProject/TestDatabase/db-1x1.db");

//		DataInputStream reader = new DataInputStream(new FileInputStream(database)); 
//		byte [] content = new byte [1000];
//		reader.read(content);
//		System.out.println(Arrays.toString(content));
			
		
		DatabaseReader dataReader = new DatabaseReader(database);
		dataReader.readRecordsFromDatabase();

	}

}
