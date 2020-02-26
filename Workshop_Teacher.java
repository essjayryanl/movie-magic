package workshop5;

import java.io.*;

public class Workshop_Teacher extends User {

	private static dbFileName = "StudentsGrades.txt";
	
	public static void calcGrade() {
		//this method reads from the movie database and returns the total number of records found
		public static int readDatabaseSize() {
			
			// reset dbRecordCount class variable
			int dbRecordCount = 0;
			
			// read from external text file
			try {
				String currentLine = null;
				
				BufferedReader br = new BufferedReader (new FileReader("StudentsGrades.txt"));
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
		public static User[] readDatabaseRecords() {
			
			// create object reference variable
			User[] users;
			users = new User[readDatabaseSize()];
			
			// read from external text file
			int lineNumber = 0;
			String currentLine;
			String [] currentLineField = new String [2];
			
			try {
			
				int sumGrade = 0;
				
				BufferedReader br = new BufferedReader (new FileReader("StudentsGrades.txt"));
				while ((currentLine = br.readLine()) != null) {
					currentLineField = currentLine.split(",");
					
					// calc
					sumGrade = sumGrade + Integer.parseInt(currentLineField[1]);
					lineNumber++;
					
					
					
				
				}
				
				br.close();
				
				int avgGrade = sumGrade / readDatabaseSize();
				System.out.println("The average grade of your students is: " + avgGrade);
						
				} catch (IOException e){
					System.out.print("\nError reading from external database file.");
				}
		
			return users;
		}	
	
	public static void logout () {
		System.out.print("BYE!");
	}
	
	}
	
