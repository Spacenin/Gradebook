package gradebook;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.time.DateTimeException;
import java.util.Scanner;
import java.util.ArrayList;

//Set of menu functions called by the main driver
public class Menu {
	private static Scanner sc = new Scanner(System.in);
	
	//Add grade option
	public ArrayList<AssignmentInterface> addGrade(ArrayList<AssignmentInterface> grades, int elemNum) throws GradebookFullException {
		try {
			//Tries to access the next element to add, throws exception if is full
			int userChoice;
			int tempScore;
			char tempLetter;
			String tempName;
			LocalDate tempDate;
			
			//User enters the type of grade to add
			System.out.println("Enter what type of grade you would like:");
			System.out.println("1. Discussion");
			System.out.println("2. Program");
			System.out.println("3. Quiz");
			System.out.print("--> ");
			userChoice = sc.nextInt();
			sc.nextLine();
			
			//Sets the grade to add as a discussion and sets reading
			if (userChoice == 1) {
				grades.add(new Discussion());
				
				System.out.println("Enter the associated reading: ");
				String tempReading = sc.nextLine();
				((Discussion)grades.get(elemNum)).setReading(tempReading);
			}
			
			//Sets the grade to add as a program and sets concept
			else if (userChoice == 2) {
				grades.add(new Program());
				
				System.out.println("Enter the concept being tested: ");
				String tempConcept = sc.nextLine();
				((Program)grades.get(elemNum)).setConcept(tempConcept);
			}
			
			//Sets the grade to add as a quiz and sets question number
			else if (userChoice == 3) {
				grades.add(new Quiz());
				
				System.out.println("Enter the number of questions in the quiz: ");
				int tempQNum = sc.nextInt();
				sc.nextLine();
				((Quiz)grades.get(elemNum)).setQNumber(tempQNum);
			}
			
			//Skips this grade if user did not enter one of the three
			else {
				System.out.println("You didn't pick a right choice! You have to go back now.");
				
				return(grades);
			}
			
			//Enter the score of the grade and catch exception if non integer, setting the grade to null
			try {
				System.out.println("Enter the score: ");
				tempScore = sc.nextInt();
				sc.nextLine(); 					//Had to include nextLine after each single variable scan, as it would leave lingering newline char
			} catch (InputMismatchException exc) {
				System.out.println("That's not a number! It must be an integer value.");
				grades.set(elemNum, null);
				sc.nextLine();
				
				return(grades);
			}
			
			//Enter the letter of the grade and catch exception if non character, setting the grade to null
			try {
				System.out.println("Enter the letter of the graded assignment: ");
				tempLetter = sc.next().charAt(0);
				sc.nextLine();
			} catch (InputMismatchException exc) {
				System.out.println("That's not a letter! It must be a character value.");
				grades.set(elemNum, null);
				sc.nextLine();
				
				return(grades);
			}
			
			//Enter the name of the grade and catch exception if the non string, setting the grade to null
			try {
				System.out.println("Enter the name of the graded assignment: ");
				tempName = sc.nextLine();
			} catch (InputMismatchException exc) {
				System.out.println("That's not a string! It must be a string.");
				grades.set(elemNum, null);
				sc.nextLine();
				
				return(grades);
			}
			
			//Enter the due date as a LocalDate variable, and checks that it's a valid LocalDate and all integers
			try {
				System.out.println("Enter the due date of the assignment (m d y): ");
				int tempMonth, tempDay, tempYear;
				tempMonth = sc.nextInt();
				tempDay = sc.nextInt();
				tempYear = sc.nextInt();
				sc.nextLine();
				
				tempDate = LocalDate.of(tempYear, tempMonth, tempDay);
			} 
			catch (InputMismatchException exc) {
				System.out.println("It must be an integer value!");
				grades.set(elemNum, null);
				sc.nextLine();
				
				return(grades);
			}
			catch (DateTimeException exc) {
				System.out.println("That date isn't right! Exiting branch...");
				grades.set(elemNum, null);				
				
				return(grades);
			}
			
			//Sets each variable
			grades.get(elemNum).setDue(tempDate);
			grades.get(elemNum).setLetter(tempLetter);
			grades.get(elemNum).setName(tempName);
			grades.get(elemNum).setScore(tempScore);
			
			//Checks that score and letter are valid combination
			if (!grades.get(elemNum).isValid()) {
				System.out.println("That grade is invalid, so it wasn't added!");
				grades.set(elemNum, null);
			} 
		
			return(grades);
		}
		//Exception if full
		catch (ArrayIndexOutOfBoundsException exc) {
			throw new GradebookFullException();
		}
		//Blanket case exception if input doesn't match
		catch (InputMismatchException exc) {
			System.out.println("That input isn't valid! Exiting branch...");
			grades.set(elemNum, null);
			sc.nextLine();
			
			return(grades);
		}
	}
	
	//Loop through and print each grade
	public void printGrades(ArrayList<AssignmentInterface> grades, int elemNum) throws GradebookEmptyException {
		//Check if gradebook is empty
		if (elemNum == 0) {
			throw new GradebookEmptyException();
		} 
		
		//Loop to print each one
		for (int i = 1; i <= elemNum; ++i) {
			System.out.println(i + ". " + grades.get(i-1).toString());
		}
	}
	
	//Remove a specified grade by name
	public ArrayList<AssignmentInterface> removeGrade(ArrayList<AssignmentInterface> grades, int elemNum) throws GradebookEmptyException, InvalidGradeException {
		//Check if gradebook is empty
		if (elemNum == 0) {
			throw new GradebookEmptyException();
		}
		
		//Loop through to find the grade and remove it
		try {
			System.out.println("Names of all assignments:");
			
			//Displays currently stored names
			for (int i = 0; i < elemNum; ++i) {
				System.out.println((i+1) + ". " + grades.get(i).getName());
			}
			
			System.out.println("Enter the name of the assignment you'd like to remove: ");
			String tempName = sc.nextLine();
			
			boolean looper = true;
			int itr = 0;
			
			//Loop through and try to find grade
			while (looper) {
				if (grades.get(itr).getName().equals(tempName)) {					
					grades.remove(itr);
					
					looper = false;
					System.out.println("Done!");
				}
				
				else {
					++itr;
				}
			}
		//Exception if iterator meets outside array bounds, throwing the grade isn't in gradebook
		} catch(IndexOutOfBoundsException exc) {
			throw new InvalidGradeException();
		} catch (NullPointerException exc) {
			System.out.println("That's not a name!");
		}
		
		return(grades);
	}
}