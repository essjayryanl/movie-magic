package database;

import java.io.*;

public class Suggestion {
	
	// declaring class variables
	private static String dbFileName = "suggestionDB.txt"; // filename for external text file storing movie database
	private static int dbRecordCount;
	private static int suggestionCount;
	
	// declaring instance variables
	private String movieTitle;
	private int movieYear;
	private String movieGenre;
	private String movieCast;
	private String movieDirector;
	private String movieDescription;
	private boolean ageRestricted;
	private boolean toDelete;
		
		/*
		 * Suggestion object constructor 
		 * This constructor is used when reading suggestion data from the external data file
		 * 
		 */
	public Suggestion(String movieTitle, String movieYear, String movieGenre, String movieCast, String movieDirector,
			String movieDescription, String ageRestricted) {
		
		this.movieTitle = movieTitle;
		this.movieYear = Integer.parseInt(movieYear);
		this.movieGenre = movieGenre;
		this.movieCast = movieCast;
		this.movieDirector = movieDirector;
		this.movieDescription = movieDescription;
		
		// set ageRestricted attribute based on integer value in external database 
		if (Integer.parseInt(ageRestricted) == 1) {
			this.ageRestricted = true;
		} else {
			this.ageRestricted = false;
		}
		
		toDelete = false;
	}
	
	/*
	 * Suggestion object constructor 
	 * This constructor is used when creating a new suggestion object, prior to appending it to the database
	 * 
	 */
	public Suggestion(String movieTitle, int movieYear, String movieGenre, String movieCast, String movieDirector,
			String movieDescription, boolean ageRestricted) {
		
		this.movieTitle = movieTitle;
		this.movieYear = movieYear;
		this.movieGenre = movieGenre;
		this.movieCast = movieCast;
		this.movieDirector = movieDirector;
		this.movieDescription = movieDescription;
		this.ageRestricted = ageRestricted;
		toDelete = false;
	}
	
	//this method reads from the movie database and returns the total number of records found
	private static int readDatabaseSize() {
		
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
	public static Suggestion[] readDatabaseRecords() {
		
		/*  Movie database must be stored is a tab delimited text file, with the following columns:		
		 *  title	
		 *  year	
		 *  genre	
		 *  cast (comma delimited list)
		 *  director
		 *  description	
		 *  restricted	
		 *
		 */
		
		// create object reference variable
		Suggestion[] suggestions;
		suggestions = new Suggestion[readDatabaseSize()];
		
		// read from external text file
		int lineNumber = 0;
		String currentLine;
		String [] currentLineField = new String [7];
		
		try {
			
			BufferedReader br = new BufferedReader (new FileReader(dbFileName));
			while ((currentLine = br.readLine()) != null) {
				currentLineField = currentLine.split("\t");
				
				// create new object
				suggestions[lineNumber] = new Suggestion(currentLineField[0], currentLineField[1], currentLineField[2], 
						currentLineField[3], currentLineField[4], currentLineField[5], 
						currentLineField[6]);
				
				lineNumber++;
				suggestionCount++;
				
			}
		
			br.close();
				
			} catch (IOException e){
				System.out.print("\nError reading from external database file.");
			}
	
		return suggestions;
	}

	/* 
	 * This method forms a new database record from the suggestion object provided and appends this record as new line to the database
	 * 
	 * Method expects a single Suggestion object to be passed, containing all attributes defined as instance variables in the Suggestion class
	 * 
	 */
	public static void appendDatabase (Suggestion newSuggestion) {
		
		// check ageRestricted field 
		String ageRestricted = "0";
		if (newSuggestion.ageRestricted == true) ageRestricted = "1";
		
		// parse array to form the line to be written to the database file
		String newLine = newSuggestion.movieTitle + "\t" 
			+ newSuggestion.movieYear + "\t"  
			+ newSuggestion.movieGenre + "\t" 
			+ newSuggestion.movieCast + "\t" 
			+ newSuggestion.movieDirector + "\t"
			+ newSuggestion.movieDescription + "\t"
			+ ageRestricted;
		
		// write to external database 
		try {
			PrintWriter wr = new PrintWriter( new BufferedWriter (new FileWriter(dbFileName, true)));
			wr.print(newLine);
			wr.close();
			
		} catch (IOException ex) {
			System.out.println("\nError writing to data file.");
		}
	}
	
	/*
	 * This method overwrites the suggestion database with new file contents
	 *
	 * Method will only write a suggestion record to file for Suggestion object where the toDelete attribute is set to 'false'
	 *
	 * Method expects an array of Suggestion objects to be passed, with each object containing all attributes 
	 * defined as instance variables in the Suggestion class
	 * 
	 */
	public static void overwriteDatabase (Suggestion[] suggestions) {
		
	// determine number of records to be written to file
	int writeCount = suggestionCount;
	for (int i = 0; i < suggestionCount; i++) {
		if (suggestions[i].toDelete == true) writeCount--;
	}
	
	// create array of strings to be output to external file from objects in Suggestion array
	// only Suggestion objects with toDelete flag set to 'false' should be written to file
	
	int writeIndex = 0;
	
	String[] newFileContents = new String [writeCount];
	
		for (int i = 0; i < writeCount; i++) {
			
			if (suggestions[i].toDelete == false) {
			
				//set age restriction value
				String restrictionValue = "0";
				if (suggestions[i].ageRestricted == true) restrictionValue = "1";
				
				newFileContents[writeIndex] = suggestions[i].movieTitle + "\t" 
				+ suggestions[i].movieYear + "\t"
				+ suggestions[i].movieGenre + "\t"
				+ suggestions[i].movieCast + "\t"
				+ suggestions[i].movieDirector + "\t"
				+ suggestions[i].movieDescription + "\t"
				+ restrictionValue;
				
				writeIndex = writeIndex++;
				System.out.println("Writing!");
			}
		}
				
		// write to file
		try {
			PrintWriter wr = new PrintWriter( new BufferedWriter (new FileWriter(dbFileName, false)));
			
			// loop to write all lines contained in input array to file
			for (int i = 0; i < newFileContents.length; i++) {
				wr.println(newFileContents[i]);
			}
			wr.close();
			
		} catch (IOException ex) {
			System.out.println("\nError writing to data file.");
		}
	}
	
	
	
	// Setter and Getter methods defined below 
	
	public void setTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}
	
	public void setYear(int movieYear) {
		this.movieYear = movieYear;
	}
	
	public void setGenre(String movieGenre) {
		this.movieGenre = movieGenre;
	}
	
	public void setCast(String movieCast) {
		this.movieCast = movieCast;
	}
	
	public void setDirector(String movieDirector) {
		this.movieDirector = movieDirector;
	}
	
	public void setDescription(String movieDescription) {
		this.movieDescription = movieDescription;
	}
	
	public void setRestriction(boolean ageRestricted) {
		this.ageRestricted = ageRestricted;
	}

	public void setDeleteFlag(boolean toDelete) {
		this.toDelete = toDelete;
	}
	
	public static int getSuggestionCount() {
		return suggestionCount;
	}
	
	public String getTitle() {
		return movieTitle;
	}
		
	public int getYear() {
		return movieYear;
	}
		
	public String getGenre() {
		return movieGenre;
	}
		
	public String getCast() {
		return movieCast;
	}
	
	public String getDirector() {
		return movieDirector;
	}
		
	public String getDescription() {
		return movieDescription;
	}
	
	public boolean getResriction() {
		return ageRestricted;
	}
	
	public boolean getDeleteFlag() {
		return toDelete;
	}
}
