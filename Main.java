package database;

public class Main {

	
	
	public static void main(String[] args) {
		// TODO Remove after testing!!

		// create objects for each movie
		MovieDB[] movie;
		movie = MovieDB.readDatabaseRecords(); //create object array
				
		// search records
		
		String searchCriteria = "2020";
		int searchType = 1;
		// 0 = title, 1 = year, 3 = genre, 4 = cast
		boolean restrictResults = false;
		
		MovieDB.searchMovies(movie, searchCriteria, searchType, restrictResults);

		// print matching records		
		for (int i = 0; i < MovieDB.movieCount; i++) {
			if (movie[i].isMatch == true) {
				System.out.println(movie[i].movieTitle);
				System.out.println(movie[i].movieYear + " - " + movie[i].movieGenre + " - " 
				+ movie[i].movieRating + " stars");
				System.out.println("Starring: " + movie[i].movieCast);
				System.out.println(movie[i].movieDescription);
				System.out.println("*******");
			}
		}
		
		movie[0].movieTitle = "New Title1a"; 
		MovieDB.overwriteDatabase(movie);
		
	}
	
}
