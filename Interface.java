package database;

public class Interface {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/* Menu: what do you want to do?
		* 1. Search
		*	-> Search by (title, cast, year, genre, director)
		*	-> Enter search term
		
		* 2. Register
		* 3. Login
		*/
		
		/* Admin menu:
		* 1. Search
		* - > Same as regular user search 
		* 
		* 2. Edit movie
		*  -> Edit title
		*  -> Edit Year
		*  -> Edit Cast 
		*  -> Edit Director
		*  -> Edit Genre
		
		* 3. Manage user
		* 	-> Delete user
		* 
		* 4. Check suggestions (read from suggestion temp file, loop through every suggestion, for every row ask (1) add OR (2) reject)
		* 	-> Add suggestion (append row[x] to MovieDB, delete row[x])
		* 	-> Delete/remove suggestion	 (delete rox[x] from temp file)
		* 	-> Ignore
		*/
		
		/* Guest menu:
		 * 1. Search
		 * 	-> Search movies (restricted results only)
		 * 
		 */
		
		/* Regular user menu:
		 * 1. Search
		 * 	-> Search movies (restricted results based on age)
		 * -> Rate movie (from those listed in search results) 
		 * 
		 * 2. Suggest a movie
		 * - > Add suggestion (Check record with same title & year doesn't exsit, Then suggestion goes to temp file)
		 */
	
		/* Registration menu:
		 * Enter username:
		 * Enter password:
		 * Enter age:
		 * Enter email:
		 */
	}

}
