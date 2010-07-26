package suncertify.db;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DatabaseReader {

	private final File database;
	
	public DatabaseReader(File database) {
		super();
		this.database = database;
	}



	public void readRecordsFromDatabase() throws FileNotFoundException {
		
		DataInputStream reader =new DataInputStream(new FileInputStream(database));
		
		  int magicCookieValue=0;
		  int databaseIdentifier=0;
		  short numberOfFields=0;
		  short nameLength=0;
		  String fieldName=null; 
		  int fieldLength=0;
		
		try {
			magicCookieValue=reader.readInt();
			databaseIdentifier=reader.readInt();
			numberOfFields=reader.readShort();
			nameLength=reader.readShort();
			StringBuilder sb = new StringBuilder();
			for(int i =0; i<nameLength;i++ ) {
				sb.append((char)reader.read());
			}
			fieldName=sb.toString();
			fieldLength=reader.readShort();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(magicCookieValue);
		System.out.println(databaseIdentifier);
		System.out.println(numberOfFields);
		System.out.println(nameLength);
		System.out.println(fieldName);
		System.out.println(fieldLength);
	}



	
}
