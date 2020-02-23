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
	int ratingTotal;
	int ratingCount;
	double ratingAvg;
	boolean isMatch;
	
	// movie object constructor (used when reading from eternal datafile)
	public MovieDB(String movieTitle, String movieYear, String movieGenre, String movieCast, 
			String movieDescription, String ageRestricted, String ratingTotal, String ratingCount) {
		
		this.movieTitle = movieTitle;
		this.movieYear = Integer.parseInt(movieYear);
		this.movieGenre = movieGenre;
		this.movieCast = movieCast;
		this.movieDescription = movieDescription;
		
		// set ageRestricted attribute based on integer value in external database 
		if (Integer.parseInt(ageRestricted) == 1) {
			this.ageRestricted = true;
		} else {
			this.ageRestricted = false;
		}
		
		this.ratingTotal = Integer.parseInt(ratingTotal);
		this.ratingCount = Integer.parseInt(ratingCount);
		
		// calculate average rating
		ratingAvg = 0;
		
		if (this.ratingCount != 0) {
			ratingAvg = (double)this.ratingTotal / (double)this.ratingCount;
		}
		
		isMatch = false;
		

	}
	
	// movie object constructor (used when creating a new movie object, prior to appending it to the database)
	public MovieDB(String movieTitle, int movieYear, String movieGenre, String movieCast, 
			String movieDescription, boolean ageRestricted) {
		
		this.movieTitle = movieTitle;
		this.movieYear = movieYear;
		this.movieGenre = movieGenre;
		this.movieCast = movieCast;
		this.movieDescription = movieDescription;
		this.ageRestricted = ageRestricted;
		int ratingTotal = 0;
		int ratingCount = 0;
		double ratingAvg = 0.0;
		isMatch = false;
		

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
	public static MovieDB[] readDatabaseRecords() {
		
		/*  Movie database must be stored is a tab delimited text file, with the following columns:		
		 *  title	
		 *  year	
		 *  genre	
		 *  cast (comma delimited list)
		 *  description	
		 *  restricted	
		 *  rating total
		 *  rating count
		 *
		 */
		
		// create object reference variable
		MovieDB[] movie;
		movie = new MovieDB[readDatabaseSize()];
		
		// read from external text file
		int lineNumber = 0;
		String currentLine;
		String [] currentLineField = new String [8];
		
		try {
			
			BufferedReader br = new BufferedReader (new FileReader(dbFileName));
			while ((currentLine = br.readLine()) != null) {
				currentLineField = currentLine.split("\t");
				
				// create new object
				movie[lineNumber] = new MovieDB(currentLineField[0], currentLineField[1], currentLineField[2], 
						currentLineField[3], currentLineField[4], currentLineField[5], currentLineField[6], currentLineField[7]);
				
				lineNumber++;
				movieCount++;
			}
			
			br.close();
				
			} catch (IOException e){
				System.out.print("\nError reading from external database file.");
			}
	
		return movie;
	}
		
	// this method searches through movie objects and sets instance variable 'isMatch' to true before returning the updated object array
	//TODO search logic needs to be updated to support wildcards
	
	public static MovieDB[] searchMovies(MovieDB[] searchObjects, String searchCriteria, int searchType, boolean searchRestricted) {
		
		// create local references for object array
		MovieDB[] movie;
		movie = searchObjects;
		
		// clear results from any past searches in current session
		clearResults(movie);
		
		// loop through object array to find matching titles 
		for (int i = 0; i < movieCount; i++) {
			
			// check for age restricted titles 
			if (searchRestricted == true && movie[i].ageRestricted == true) continue;
			
			// perform search
			switch (searchType) {
			case 0: // title search 
				if(movie[i].movieTitle == searchCriteria) movie[i].isMatch = true;
				break;
			case 1: // year search
				if(movie[i].movieYear == Integer.parseInt(searchCriteria)) movie[i].isMatch = true;
				break;
			case 2: // search genre
				if(movie[i].movieGenre == searchCriteria) movie[i].isMatch = true;
				break;
			case 3: // search cast
				if(movie[i].movieCast == searchCriteria) movie[i].isMatch = true;
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
	public static void updateRating (MovieDB[] searchObjects, int movieIndex, int rating) {
		
		// create local references for object array
		MovieDB[] movie;
		movie = searchObjects;
		
		// adjust in memory
		movie[movieIndex].ratingTotal = movie[movieIndex].ratingTotal + rating;
		movie[movieIndex].ratingCount = movie[movieIndex].ratingCount++;
		
		if (movie[movieIndex].ratingCount != 0) {
			movie[movieIndex].ratingAvg = (double)movie[movieIndex].ratingTotal / (double)movie[movieIndex].ratingCount;
		}
			
		// store to file for next run
		overwriteDatabase(movie);
		
	}
	
	// this method forms a new database record from the movie object provided and appends this record as new line to the database
	public static void appendDatabase (MovieDB newMovie) {
			
		// check ageRestricted field 
		String ageRestricted = "0";
		if (newMovie.ageRestricted == true) ageRestricted = "1";
		
		// parse array to form the line to be written to the database file
		String newLine = newMovie.movieTitle + "\t" 
				+ newMovie.movieYear + "\t"  
				+ newMovie.movieGenre + "\t" 
				+ newMovie.movieCast + "\t" 
				+ newMovie.movieDescription + "\t" 
				+ ageRestricted + "\t" 
				+ newMovie.ratingTotal + "\t"
				+ newMovie.ratingCount;
		
		// write to external database 
		try {
			PrintWriter wr = new PrintWriter( new BufferedWriter (new FileWriter(dbFileName, true)));
			wr.print(newLine);
			wr.close();
			
		} catch (IOException ex) {
			System.out.println("\nError writing to data file.");
		}
	}
	
	// this method overwrites the movie database with new file contents
	public static void overwriteDatabase (MovieDB[] newDataObjects) {
		
		// create local object array 
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
			+ movie[i].ratingTotal + "\t"
			+ movie[i].ratingCount;
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
