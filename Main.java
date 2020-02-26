package database;

public class Main {
	
	public static void main(String[] args) {
		// TODO Remove after testing!!

	// Test MovieDB Class: Read from database and create a movie object for each record found
		MovieDB[] movie;
		movie = MovieDB.readDatabaseRecords(); //create object array
				
	// Test MovieDB Class: Search records
		
		// define search criteria
		String searchCriteria = "Hathaway";
		int searchType = 3; 	// Supported search types: 0 = title, 1 = year, 2 = genre, 3 = cast
		boolean restrictResults = true;
		
		MovieDB.searchMovies(movie, searchCriteria, searchType, restrictResults);
		
	// Test MovieDB Class: Print search results, based on updated objects returned by searchMovies() method	
		for (int i = 0; i < MovieDB.getMovieCount(); i++) {
			if (movie[i].getMatch() == true) {
				System.out.println("\n----------------------");
				System.out.println("*** SEARCH TEST ***");
				System.out.println(movie[i].getTitle());
				System.out.printf("%d - %s - %.1f stars\n", movie[i].getYear(), movie[i].getGenre(), movie[i].getRating());
				System.out.println("Starring: " + movie[i].getCast());
				System.out.println(movie[i].getDescription());
				System.out.println("*******");
			}
		}
		
		
	// Test MovieDB Class: Update rating
		
		int movieIndexID = 2;
		int newRating = 5;
		
		System.out.println("\n----------------------");
		System.out.println("*** UDPATE RATING TEST ***");
		System.out.println("Movie name: " + movie[movieIndexID].getTitle());
		System.out.printf("Old rating: %.1f\n", movie[movieIndexID].getRating());
		
		MovieDB.updateRating(movie, movieIndexID, newRating);
		
		System.out.printf("Updated rating: %.1f\n", movie[movieIndexID].getRating());
		
	// Test MovieDB Class: Overwrite existing database file with entire contents from object array
		movie[0].setTitle("New Title" + Math.random()); 
		MovieDB.overwriteDatabase(movie);
		
		
	// Test MovieDB Class: append single new record to database file
		String movieTitle = "A new movie" + Math.random();
		int movieYear = 2021;
		String movieGenre = "Comedy";
		String movieCast = "James Franco, Sanda Bullock, Some Guy";
		String movieDescription = "A new movie due to come out in 2021";
		boolean ageRestricted = false;
		
		// create new movie object
		MovieDB newMovie = new MovieDB(movieTitle, movieYear, movieGenre, movieCast, movieDescription, ageRestricted);
		
		// call appendDatabase() in MovieDB class
		MovieDB.appendDatabase(newMovie);
		
	}
	
}
