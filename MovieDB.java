package database;

import java.io.*;

public class MovieDB {
	
	static String dbFileName = "movieDB.txt"; // filename for external text file storing movie database
	static int dbRecordCount;
	static int movieCount;
	
	String movieTitle;
	int movieYear;
	String movieGenre;
	String movieCast;
	String movieDescription;
	boolean ageRestricted;
	double movieRating;
	int movieTotalRatings;
	boolean isMatch;
	
	// movie object constructor
	public MovieDB(String movieTitle, String movieYear, String movieGenre, String movieCast, 
			String movieDescription, String ageRestricted, String movieRating, String movieTotalRatings) {
		
		this.movieTitle = movieTitle;
		this.movieYear = Integer.parseInt(movieYear);
		this.movieGenre = movieGenre;
		this.movieCast = movieCast;
		this.movieDescription = movieDescription;
		
		if (Integer.parseInt(ageRestricted) == 1) {
			this.ageRestricted = true;
		} else {
			this.ageRestricted = false;
		}
		
		this.movieRating = Double.parseDouble(movieRating);
		this.movieTotalRatings = Integer.parseInt(movieTotalRatings);
		
		isMatch = false;
		
		movieCount++;
	}
	

		
	//this method reads from the movie database and returns contents and an array of strings
	public static int readDatabaseSize() {
	
		dbRecordCount = 4;
		
		return dbRecordCount;
		
	}
	
	// this method reads from the movie database text file and creates an array of objects from the data found
	public static MovieDB[] readDatabaseRecords() {
		
		/*  Movie database must be stored is a tab delimited text file, with the following columns:		
		 *  title	
		 *  year	
		 *  genre	
		 *  cast (comma delimited list)
		 *  description	
		 *  restricted	
		 *  rating
		 *  total ratings
		 *
		 */
		
		// create object reference variable
		MovieDB[] movie;
		movie = new MovieDB[readDatabaseSize()];
		
		// read from external text file
		int lineNumber = 0;
		String CurrentLine;
		String [] currentLineField = new String [8];
		
		try {
			
			BufferedReader br = new BufferedReader (new FileReader(dbFileName));
			while ((CurrentLine = br.readLine()) != null) {
				currentLineField = CurrentLine.split("\t");
				// create new object
				movie[lineNumber] = new MovieDB(currentLineField[0], currentLineField[1], currentLineField[2], 
						currentLineField[3], currentLineField[4], currentLineField[5], currentLineField[6], currentLineField[7]);
				
				lineNumber++;
			}
			
			br.close();
				
			} catch (IOException e){
				System.out.print("\nError reading from external database file.");
			}
	
		return movie;
	}
		
	// this method searches through movie objects and sets instance variable 'isMatch' to true before returning the updated object array
	public static MovieDB[] searchMovies(MovieDB[] searchObjects, String searchCriteria, int searchType, boolean searchRestricted) {
		
		// create local references for object array
		MovieDB[] movie;
		movie = searchObjects;
		
		// clear results from any past searhes in current session
		clearResults(movie);
		
		// loop through object array to find matching titles 
		for (int i = 0; i < movieCount; i++) {
			
			// check for age restricted titles 
			if (searchRestricted == true && movie[i].ageRestricted == true) continue;
			
			// perform search
			switch (searchType) {
			case 0: // title search //TODO fix string search, support wildcards
				System.out.println(searchCriteria);
				System.out.println(movie[i].movieTitle);
				if(movie[i].movieTitle == searchCriteria) movie[i].isMatch = true;
				break;
			case 1: // year search
				if(movie[i].movieYear == Integer.parseInt(searchCriteria)) movie[i].isMatch = true;
				break;
			case 2: // search genre
				
				break;
			case 4: // search cast
				
				break;
			}
			
		}
		return movie;
		
	}
	
	// this method sets the isMatch attribute to 'false' for all objects in the specified array
	public static void clearResults (MovieDB[] searchObjects) {
		
		// create local references for object array
		MovieDB[] movie;
		movie = searchObjects;
		
		// loop through all objects in array and set isMatch to false
		for(int i = 0; i < movieCount; i++) {
			movie[i].isMatch = false;
		}
		
	}
	// this method adjusts ratings based on new rating provided
	public static void setRating (double rating) {
		
		

		// adjust in memory
	
		// store to file for next run
	
	}
	
	// this method appends a new line to the database
	public static void appendDatabase (String newRecord) {
		
	}
	
	// this method overwrites the movie database with new file contents
	public static void overwriteDatabase (MovieDB[] newDataObjects) {
		
		// create local object arrray 
		MovieDB[] movie;
		movie = newDataObjects;
		
		// create strings to be output to external file from objects in local array
		String[] newFileContents = new String [movieCount];
		String restrictionValue = "0";
				
		for (int i = 0; i < movieCount; i++) {
			
			//set age restriction 
			if (movie[i].ageRestricted == true) restrictionValue = "1";
			
			newFileContents[i] = movie[i].movieTitle + "\t" 
			+ movie[i].movieYear + "\t"
			+ movie[i].movieGenre + "\t"
			+ movie[i].movieCast + "\t"
			+ movie[i].movieDescription + "\t"
			+ restrictionValue + "\t"
			+ movie[i].movieRating + "\t"
			+ movie[i].movieTotalRatings;
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

}
