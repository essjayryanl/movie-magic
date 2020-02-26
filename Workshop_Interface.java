package workshop5;

import java.util.Scanner;

public class Workshop_Interface {
	
	static Scanner userInput = new Scanner(System.in);

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		User[] users;
		users = User.readDatabaseRecords(); //create object array
		
		System.out.print("Username: ");
		String username = userInput.nextLine();
		
		System.out.print("Password: ");
		String password = userInput.nextLine();
		
		for(int i = 1; i < User.getUserCount(); i++) {
						
			if (users[i].getUsername().equals(username) && users[i].getPassword().equals(password)) {
				if (users[i].getRole().equals("Teacher")) {
					System.out.println("Welcome " + users[i].getFirstName() + " " + users[i].getLastName());
					System.out.println("You are now accessing the teacher interface");
					
					System.out.println("Please select");
					System.out.println("1) Print avg grade");
					System.out.println("2) logout");
					
					String userChoice = userInput.nextInt();
					
					Switch(userChoice) {
					case 1: 
						Teacher.calcGrade();
						break
					case 2:
						Teacher.logout();
						break
				}
				
				} else if (users[i].getRole().equals("Student")) {
					System.out.println("Welcome " + users[i].getFirstName() + " " + users[i].getLastName());
					System.out.println("You are now accessing the student interface");
				}
			
			// } else System.out.println("Invalid login details!");
				
			} 
		}
	}
}