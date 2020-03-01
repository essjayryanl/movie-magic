public class MovieDB_Integration_Example {
	
	// Class created to help explain expected inputs for MovieDB class. Delete before submitting assignment!!
	
	public static void main(String[] args) {
		
		// create movie object array & populate with records from external database
		MovieDB[] movie;
		movie = MovieDB.readDatabaseRecords();
		
		// delete a movie
		movie[10].setDeleteFlag(true);		// set deletion flag
		MovieDB.overwriteDatabase(movie);	// call overwrite method
		
		// edit a movie (in this case updating it's year of release)
		movie[2].setYear(1989);				// set the new year value
		MovieDB.overwriteDatabase(movie);	// call overwrite method
		
	}