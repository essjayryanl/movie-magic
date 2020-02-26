package workshop5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Workshop_User {

	private static String dbFileName = "UserData.txt";
	private static int userCount;
	private static int dbRecordCount;
	
	private String FirstName;
	private String LastName;
	private String UserName;
	private String Password;
	private String ID;
	private String Role;
	
	public Workshop_User (String FirstName, String LastName, String UserName, String Password, String ID, String role) {
		this.FirstName = FirstName;
		this.LastName = LastName;
		this.UserName = UserName;
		this.Password = Password;
		this.ID = ID;
		this.Role = role;
	}
	
	//this method reads from the movie database and returns the total number of records found
	public static int readDatabaseSize() {
		
		// reset dbRecordCount class variable
		dbRecordCount = 0;
		
		// read from external text file
		try {
			String currentLine = null;
			
			BufferedReader br = new BufferedReader (new FileReader(dbFileName));
			while ((currentLine = br.readLine()) != null) {
				dbRecordCount++;
			}
			
			br.close();
				
			} catch (IOException e){
				System.out.print("\nError reading from external database file.");
			}
	
		return dbRecordCount;
		
	}
	
	// this method reads from the movie database text file and creates an array of objects from the data found
	public static Workshop_User[] readDatabaseRecords() {
		
		// create object reference variable
		Workshop_User[] users;
		users = new Workshop_User[readDatabaseSize()];
		
		// read from external text file
		int lineNumber = 0;
		String currentLine;
		String [] currentLineField = new String [6];
		
		try {
			
			BufferedReader br = new BufferedReader (new FileReader(dbFileName));
			while ((currentLine = br.readLine()) != null) {
				currentLineField = currentLine.split(",");
				
				// create new object
				users[lineNumber] = new Workshop_User(currentLineField[0],currentLineField[1],currentLineField[2],
						currentLineField[3],currentLineField[4],currentLineField[5]);
				
				lineNumber++;
				userCount++;	
				
				
			
			}
			
			br.close();
				
			} catch (IOException e){
				System.out.print("\nError reading from external database file.");
			}
	
		return users;
	}

	public static int getUserCount() {
		return userCount;
	}
	
	public String getUsername() {
		return UserName;
	}
	
	public String getPassword() {
		return Password;
	}
	
	public String getRole() {
		return Role;
	}
	
	public String getFirstName() {
		return FirstName;
		
	}
	
	public String getLastName() {
		return LastName;
		
	}
}
