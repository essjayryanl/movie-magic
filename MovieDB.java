package database;

import java.io.*;

public class MovieDB {
	
	// declaring class variables
	private static String dbFileName = "movieDB.txt"; // filename for external text file storing movie database
	private static int dbRecordCount;
	private static int movieCount;
	
	// declaring instance variables
	private String movieTitle;
	private int movieYear;
	private String movieGenre;
	private String movieCast;
	private String movieDirector;
	private String movieDescription;
	private boolean ageRestricted;
	private int ratingTotal;
	private int ratingCount;
	private double ratingAvg;
	private boolean isMatch;
	
	/*
	 * Movie object constructor 
	 * This constructor is used when reading movie data from the external data file
	 * 
	 */
	public MovieDB(String movieTitle, String movieYear, String movieGenre, String movieCast, String movieDirector,
			String movieDescription, String ageRestricted, String ratingTotal, String ratingCount) {
		
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
		
		this.ratingTotal = Integer.parseInt(ratingTotal);
		this.ratingCount = Integer.parseInt(ratingCount);
		
		// calculate average rating
		ratingAvg = 0;
		
		if (this.ratingCount != 0) {
			ratingAvg = (double)this.ratingTotal / (double)this.ratingCount;
		}
		
		isMatch = false;
	
	}

	/*
	 * Movie object constructor 
	 * This constructor is used when creating a new movie object, prior to appending it to the database
	 * 
	 */
	public MovieDB(String movieTitle, int movieYear, String movieGenre, String movieCast, String movieDirector,
			String movieDescription, boolean ageRestricted) {
		
		this.movieTitle = movieTitle;
		this.movieYear = movieYear;
		this.movieGenre = movieGenre;
		this.movieCast = movieCast;
		this.movieDirector = movieDirector;
		this.movieDescription = movieDescription;
		this.ageRestricted = ageRestricted;
		int ratingTotal = 0;
		int ratingCount = 0;
		double ratingAvg = 0.0;
		isMatch = false;
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
	public static MovieDB[] readDatabaseRecords() {
		
		/*  Movie database must be stored is a tab delimited text file, with the following columns:		
		 *  title	
		 *  year	
		 *  genre	
		 *  cast (comma delimited list)
		 *  director
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
		String [] currentLineField = new String [10];
		
		try {
			
			BufferedReader br = new BufferedReader (new FileReader(dbFileName));
			while ((currentLine = br.readLine()) != null) {
				currentLineField = currentLine.split("\t");
				
				// create new object
				movie[lineNumber] = new MovieDB(currentLineField[0], currentLineField[1], currentLineField[2], 
						currentLineField[3], currentLineField[4], currentLineField[5], 
						currentLineField[6], currentLineField[7], currentLineField[8]);
				
				lineNumber++;
				movieCount++;
				
			}
		
			br.close();
				
			} catch (IOException e){
				System.out.print("\nError reading from external database file.");
			}
	
		return movie;
	}
		
	/* 
	 * This method searches through movie objects and sets instance variable 'isMatch' to true for matching records
	 *
	 * Method expects the following inputs: an array of MovieDB objects, search criteria, integer indicating the search type, 
	 * boolean flag to indicate if age restricted results should be returned  
     * 
	 * The following 'search types' are supported: 0 = title, 1 = year, 2 = genre, 3 = cast, 4 = director
	 * 
	 */
	public static MovieDB[] searchMovies(MovieDB[] movie, String searchCriteria, int searchType, boolean searchRestricted) {
		
		// clear results from any past searches in current session
		clearResults(movie);
		
		// loop through object array to find matching titles 
		for (int i = 0; i < movieCount; i++) {
			
			// check for and filter age restricted titles 
			if (searchRestricted == true && movie[i].ageRestricted == true) continue;
			
			// perform search
			switch (searchType) {
			case 0: // title search 
				if(movie[i].movieTitle.contains(searchCriteria)) movie[i].isMatch = true; // TODO .contains is case sensiive! add regex
				break;
			case 1: // year search
				if(movie[i].movieYear == Integer.parseInt(searchCriteria)) movie[i].isMatch = true;
				break;
			case 2: // search genre
				if(movie[i].movieGenre.equals(searchCriteria)) movie[i].isMatch = true; // TODO .contains is case sensiive! add regex
				break;
			case 3: // search cast
				if(movie[i].movieCast.contains(searchCriteria)) movie[i].isMatch = true; // TODO .contains is case sensiive! add regex
				break;
			case 4: // search director
				if(movie[i].movieDirector.contains(searchCriteria)) movie[i].isMatch = true; // TODO .contains is case sensiive! add regex
				break;
			}
			
		}
		return movie;
		
	}
	
	// this method sets the isMatch attribute to 'false' for all objects in the specified array
	private static void clearResults (MovieDB[] movie) {
		
		// loop through all objects in array and set isMatch to false
		for(int i = 0; i < movieCount; i++) {
			movie[i].isMatch = false;
		}
		
	}
	/*
	 * This method adjusts rating data based on new rating provided by user
	 * ratingTotal and ratingCount values are updated so a new average rating can be calcualted
	 *
	 * Method expects the following arguments: an array of MoivieDB objects to be passed, 
	 * index of movie object to have rating updated, new rating provided by user 
	 * 
	 */
	public static void updateRating (MovieDB[] movie, int movieIndex, int rating) {
		
		// adjust in memory
		movie[movieIndex].ratingTotal = movie[movieIndex].ratingTotal + rating;
		movie[movieIndex].ratingCount = movie[movieIndex].ratingCount++;
		
		if (movie[movieIndex].ratingCount != 0) {
			movie[movieIndex].ratingAvg = (double)movie[movieIndex].ratingTotal / (double)movie[movieIndex].ratingCount;
		}
			
		// write updated rating data to file
		overwriteDatabase(movie);
		
	}
	
	/* 
	 * This method forms a new database record from the movie object provided and appends this record as new line to the database
	 *  
	 * Method expects a single MovieDB object to be passed, containing all attributes defined as instance variables in the MovieDB class
	 * 
	 */
	public static void appendDatabase (MovieDB newMovie) {
			
		// check ageRestricted field 
		String ageRestricted = "0";
		if (newMovie.ageRestricted == true) ageRestricted = "1";
		
		// parse array to form the line to be written to the database file
		String newLine = newMovie.movieTitle + "\t" 
				+ newMovie.movieYear + "\t"  
				+ newMovie.movieGenre + "\t" 
				+ newMovie.movieCast + "\t" 
				+ newMovie.movieDirector + "\t"
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
	
	/*
	 * This method overwrites the movie database with new file contents
	 *
	 * Method expects an array of MovieDB objects to be passed, with each object containing all attributes 
	 * defined as instance variables in the MovieDB class
	 * 
	 */
	public static void overwriteDatabase (MovieDB[] movie) {
		
		// create array of strings to be output to external file from objects in MovieDB array
		String[] newFileContents = new String [movieCount];
		
		for (int i = 0; i < movieCount; i++) {
			
			//set age restriction value
			String restrictionValue = "0";
			if (movie[i].ageRestricted == true) restrictionValue = "1";
			
			newFileContents[i] = movie[i].movieTitle + "\t" 
			+ movie[i].movieYear + "\t"
			+ movie[i].movieGenre + "\t"
			+ movie[i].movieCast + "\t"
			+ movie[i].movieDirector + "\t"
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

	public static int getMovieCount() {
		return movieCount;
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
		
	public double getRating() {
		return ratingAvg;
	}

	public boolean getMatch() {
		return isMatch;
	}
		
}
