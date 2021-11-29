package gradebook;

import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.Scanner;

//Main driver method, looping and printing menu, then calling menu functions accordingly
public class Gradebook {

	public static void main(String[] args) {
		int userChoice = 0;
		boolean looper = true;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Welcome to the 2810 Gradebook Project!");
		
		ArrayList<AssignmentInterface> grades = new ArrayList<AssignmentInterface>();
		
		Menu m = new Menu();
		
		//Loop and print menu until option 9 is entered
		while (looper) {
			System.out.println();
			System.out.println("--CHOOSE AN OPTION--");
			System.out.println("1. Add grades to gradebook");
			System.out.println("2. Remove grade from gradebook");
			System.out.println("3. Print grades in gradebook");
			System.out.println("4. Print to file");
			System.out.println("5. Read from file");
			System.out.println("6. To MySQL");
			System.out.println("7. From MySQL");
			System.out.println("8. Search MySQL");
			System.out.println("9. Leave this program");
			System.out.print("--> ");
			
			//Catch non integer input and loop again
			try {
				userChoice = sc.nextInt();
				sc.nextLine();
			} catch (InputMismatchException exc) {
				System.out.println("That is not a number! Continuing on...");
				sc = new Scanner(System.in);
				
				userChoice = 0;
			} 
			
			switch(userChoice) {
				case 0:
					break;
				//Add grades option
				case 1:
					int loopNum = 0;
					
					try {
						//Allow user to enter multiple grades
						System.out.println("Enter the number of grades you'd like to enter: ");
						loopNum = sc.nextInt();

						//Catch non integer value
					} catch (InputMismatchException exc) {
						System.out.println("You must enter a number!");
						sc.nextLine();
					}
					
					//Loop and add each grade
					for (int i = 0; i < loopNum; ++i) {
						try {
							AssignmentInterface gradeTry = m.addGrade();
							
							//If function performed as wanted
							if (gradeTry != null) {
								grades.add(gradeTry);
								
								System.out.println("1 grade added!");
							}
						} catch (IndexOutOfBoundsException exc) {
							break;
						}
					}
					
					break;
				//Remove grade
				case 2:
					try {
						grades = m.removeGrade(grades);
					}
					//Catch gradebook is empty or the grade doesn't exist
					catch (GradebookEmptyException exc) {}
					catch (InvalidGradeException exc) {}
					
					break;
				//Print all grades in gradebook
				case 3:
					try {
						m.printGrades(grades);
						
						System.out.println("Done!");
					//Catch gradebook is empty 
					} catch (GradebookEmptyException exc) {}
					
					break;
				//Print to file
				case 4:
					try {
						m.printFile(grades);
						
						System.out.println("Done!");
					} catch (GradebookEmptyException exc) {}
					
					break;
				//Read file
				case 5:
					ArrayList<AssignmentInterface> gradesFiled = m.readFile();
					
					//Check if the file was actually read
					if (gradesFiled == null) {}
					
					//If so, then set the gradebook to the read one
					else {
						grades.addAll(gradesFiled);
						
						System.out.println("Done!");
					}
					
					break;
				//Send local gradebook to SQL database
				case 6:
					try {
						m.toMySQL(grades);
						
						System.out.println("Done!");
					} catch (GradebookEmptyException exc) {}
					
					break;
				//Get unadded grades from SQL database to local gradebook
				case 7:
					grades = m.fromMySQL(grades);
					
					System.out.println("Done!");
					
					break;
				//Search SQL database under certain criteria
				case 8:
					m.searchMySQL();
					
					System.out.println("Done!");
					
					break;
				//Leave program
				case 9:
					System.out.println("Goodbye. :(");
					
					looper = false;
					break;
				//If input isn't in a valid option
				default:
					System.out.println("Error, that choice isn't an option!");
					
					break;
			}
		}
		
		DBUtil.closeConnection();
		sc.close();
	}
}
