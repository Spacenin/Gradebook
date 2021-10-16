package gradebook;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Gradebook {

	public static void main(String[] args) {
		int gradesNum;
		int userChoice = 0;
		int elemNum = 0;
		boolean looper = true;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Welcome to the 2810 Gradebook Project!");
		System.out.print("Enter the number of grades you would like to enter (max of 20): ");
		
		try {
			gradesNum = sc.nextInt();
		} catch (InputMismatchException exc) {
			System.out.println("You did not enter a valid integer for the size! Defaulting to size 20.");
			gradesNum = 20;
			sc.nextLine();
		}
		
		if ((gradesNum <= 0) || (gradesNum > 20)) {
			System.out.println("You entered an invalid gradebook number, defaulting to size 20.");
			gradesNum = 20;
		}
		
		AssignmentInterface[] grades = new AssignmentInterface[gradesNum];
		
		Menu m = new Menu();
		
		while (looper) {
			System.out.println();
			System.out.println("--CHOOSE AN OPTION--");
			System.out.println("1. Add grades to gradebook");
			System.out.println("2. Remove grade from gradebook");
			System.out.println("3. Print grades in gradebook");
			System.out.println("4. Print average grade");
			System.out.println("5. Print the highest and lowest grades");
			System.out.println("6. Print average # of questions in quizzes");
			System.out.println("7. Print assoicated readings for every discussion");
			System.out.println("8. Print concepts of every programming assignment");
			System.out.println("9. Leave this program");
			System.out.print("--> ");
			
			try {
				userChoice = sc.nextInt();
				sc.nextLine();
			} catch (InputMismatchException exc) {
				System.out.println("That is not a number! Continuing on...");
				
				userChoice = 0;
			}
			
			switch(userChoice) {
				case 0:
					break;
				case 1:
					int loopNum = 0;
					
					try {
						System.out.println("Enter the number of grades you'd like to enter (can add until full): ");
						loopNum = sc.nextInt();
						
						if (loopNum > grades.length) {
							System.out.println("That number is higher than size of array! Defaulting to one addition.\n");
							
							loopNum = 1;
						}
					} catch (InputMismatchException exc) {
						System.out.println("You must enter a number!");
					}
					
					for (int i = 0; i < loopNum; ++i) {
						try {
							grades = m.addGrade(grades, elemNum);
							
							if (grades[elemNum] != null) {
								++elemNum;
								
								System.out.println("1 grade added!");
							}
						} 
						catch (GradebookFullException exc) {
							break;
						}
					}
					
					break;
				case 2:
					try {
						grades = m.removeGrade(grades, elemNum);
						
						--elemNum;
					}
					catch (GradebookEmptyException exc) {}
					catch (InvalidGradeException exc) {}
					
					break;
				case 3:
					try {
						m.printGrades(grades, elemNum);
					} catch (GradebookEmptyException exc) {}
					
					break;
				case 4:
					try {
						m.printAverage(grades, elemNum);
					} catch (GradebookEmptyException exc) {}
					
					break;
				case 5:
					try {
						m.printMaxMin(grades, elemNum);
					} catch (GradebookEmptyException exc) {}
					
					break;
				case 6:
					try {
						m.printQuizAverage(grades, elemNum);
					} catch (GradebookEmptyException exc) {}
					
					break;
				case 7:
					try {
						m.printReadings(grades, elemNum);
					} catch (GradebookEmptyException exc) {}
					
					break;
				case 8:
					try {
						m.printConcepts(grades, elemNum);
					} catch (GradebookEmptyException exc) {}
					
					break;
				case 9:
					System.out.println("Goodbye. :(");
					
					looper = false;
					break;
				default:
					System.out.println("Error, that choice isn't an option!");
					
					break;
			}
		}
		
		sc.close();
	}
}
