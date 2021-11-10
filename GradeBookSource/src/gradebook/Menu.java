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
	public AssignmentInterface[] addGrade(AssignmentInterface[] grades, int elemNum) throws GradebookFullException {
		try {
			//Tries to access the next element to add, throws exception if is full
			AssignmentInterface tryToAccess = grades[elemNum];
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
				grades[elemNum] = new Discussion();
				
				System.out.println("Enter the associated reading: ");
				String tempReading = sc.nextLine();
				((Discussion)grades[elemNum]).setReading(tempReading);
			}
			
			//Sets the grade to add as a program and sets concept
			else if (userChoice == 2) {
				grades[elemNum] = new Program();
				
				System.out.println("Enter the concept being tested: ");
				String tempConcept = sc.nextLine();
				((Program)grades[elemNum]).setConcept(tempConcept);
			}
			
			//Sets the grade to add as a quiz and sets question number
			else if (userChoice == 3) {
				grades[elemNum] = new Quiz();
				
				System.out.println("Enter the number of questions in the quiz: ");
				int tempQNum = sc.nextInt();
				sc.nextLine();
				((Quiz)grades[elemNum]).setQNumber(tempQNum);
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
				grades[elemNum] = null;
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
				grades[elemNum] = null;
				sc.nextLine();
				
				return(grades);
			}
			
			//Enter the name of the grade and catch exception if the non string, setting the grade to null
			try {
				System.out.println("Enter the name of the graded assignment: ");
				tempName = sc.nextLine();
			} catch (InputMismatchException exc) {
				System.out.println("That's not a string! It must be a string.");
				grades[elemNum] = null;
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
				grades[elemNum] = null;
				sc.nextLine();
				
				return(grades);
			}
			catch (DateTimeException exc) {
				System.out.println("That date isn't right! Exiting branch...");
				grades[elemNum] = null;				
				
				return(grades);
			}
			
			//Sets each variable
			grades[elemNum].setDue(tempDate);
			grades[elemNum].setLetter(tempLetter);
			grades[elemNum].setName(tempName);
			grades[elemNum].setScore(tempScore);
			
			//Checks that score and letter are valid combination
			if (!grades[elemNum].isValid()) {
				System.out.println("That grade is invalid, so it wasn't added!");
				grades[elemNum] = null;
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
			grades[elemNum] = null;
			sc.nextLine();
			
			return(grades);
		}
	}
	
	//Loop through and print each grade
	public void printGrades(AssignmentInterface[] grades, int elemNum) throws GradebookEmptyException {
		//Check if gradebook is empty
		if (elemNum == 0) {
			throw new GradebookEmptyException();
		} 
		
		//Loop to print each one
		for (int i = 1; i <= elemNum; ++i) {
			System.out.println(i + ". " + grades[i-1].toString());
		}
	}
	
	//Remove a specified grade by name
	public AssignmentInterface[] removeGrade(AssignmentInterface[] grades, int elemNum) throws GradebookEmptyException, InvalidGradeException {
		//Check if gradebook is empty
		if (elemNum == 0) {
			throw new GradebookEmptyException();
		}
		
		//Loop through to find the grade and remove it
		try {
			System.out.println("Names of all assignments:");
			
			//Displays currently stored names
			for (int i = 0; i < elemNum; ++i) {
				System.out.println((i+1) + ". " + grades[i].getName());
			}
			
			System.out.println("Enter the name of the assignment you'd like to remove: ");
			String tempName = sc.nextLine();
			
			boolean looper = true;
			int itr = 0;
			
			//Loop through and try to find grade
			while (looper) {
				if (grades[itr].getName().equals(tempName)) {					
					for (int i = itr; i < grades.length - 1; ++i) {
							grades[i] = grades[i+1];
					}
					
					looper = false;
					System.out.println("Done!");
				}
				
				else {
					++itr;
				}
			}
		//Exception if iterator meets outside array bounds, throwing the grade isn't in gradebook
		} catch(ArrayIndexOutOfBoundsException exc) {
			throw new InvalidGradeException();
		} catch (NullPointerException exc) {
			System.out.println("That's not a name!");
		}
		
		return(grades);
	}
	
	//Print average score
	public void printAverage(AssignmentInterface[] grades, int elemNum) throws GradebookEmptyException {
		//Check if gradebook is empty
		if (elemNum == 0) {
			throw new GradebookEmptyException();
		}
		
		double average = 0;
		
		//Loop through and add each grade to sum, then divide
		for (int i = 0; i < elemNum; ++i) {
			average = average + grades[i].getScore();
		}
		
		average = average / elemNum;
		
		System.out.println("Average score of all grades: " + average);
	}
	
	//Print max and min scores
	public void printMaxMin(AssignmentInterface[] grades, int elemNum) throws GradebookEmptyException {
		//Checks if gradebook is empty
		if (elemNum == 0) {
			throw new GradebookEmptyException();
		}
		
		//Invalid grade starts
		int max = -1;
		int min = 101;
		
		//Loop and check if each grade is greater than or equal to each
		for (int i = 0; i < elemNum; ++i) {
			if (grades[i].getScore() <= min) {
				min = grades[i].getScore();
			}
			
			if (grades[i].getScore() >= max) {
				max = grades[i].getScore();
			}
		}
		
		System.out.println("Highest score in your gradebook: " + max);
		System.out.println("Lowest score in your gradebook: " + min);
	}
	
	//Print quiz question number average
	public void printQuizAverage(AssignmentInterface[] grades, int elemNum) throws GradebookEmptyException {
		//Check if gradebook is empty
		if (elemNum == 0) {
			throw new GradebookEmptyException();
		}
		
		int quizCounter = 0;
		int averageQuestion = 0;
		
		//Check if each grade is a quiz and count them, if still 0 then there are no quizzes
		for (int i = 0; i < elemNum; ++i) {
			if (grades[i] instanceof Quiz) {
				++quizCounter;
				averageQuestion = averageQuestion + ((Quiz) grades[i]).getQNumber();
			}
		}
		
		if (quizCounter == 0) {
			System.out.println("There are no quizzes in your gradebook!");			
		}
		
		else {
			averageQuestion = averageQuestion / quizCounter;
			
			System.out.println("Average question number: " + averageQuestion);
		}
	}
	
	//Print all discussion readings
	public void printReadings(AssignmentInterface[] grades, int elemNum) throws GradebookEmptyException {
		//Check if gradebook is empty
		if (elemNum == 0) {
			throw new GradebookEmptyException();
		}
		
		//Arraylist to add each string (I didn't know if we could use one here or not)
		ArrayList<String> readings = new ArrayList<String>();
		int disCounter = 0;
		
		//Loop through and check if it is a discussion, if counter is still 0 then there are no discussions
		for (int i = 0; i < elemNum; ++i) {
			if (grades[i] instanceof Discussion) {
				++disCounter;
				readings.add(((Discussion)grades[i]).getReading());
			}
		}
		
		if (disCounter == 0) {
			System.out.println("There are no discussions in your gradebook!");
		}
		
		else {
			System.out.println("All associated readings: ");
			
			for (String each : readings) {
				System.out.println(each);
			}
		}
	}
	
	//Print all program concepts
	public void printConcepts(AssignmentInterface[] grades, int elemNum) throws GradebookEmptyException {
		//Check if gradebook is empty
		if (elemNum == 0) {
			throw new GradebookEmptyException();
		}
		
		//Arraylist to add each concept string
		ArrayList<String> concepts = new ArrayList<String>();
		int proCounter = 0;
		
		//Loop through and see if each grade is a program, if counter is 0 there are no grades 
		for (int i = 0; i < elemNum; ++i) {
			if (grades[i] instanceof Program) {
				++proCounter;
				concepts.add(((Program)grades[i]).getConcept());
			}
		}
		
		if (proCounter == 0) {
			System.out.println("There are no programs in your gradebook!");
		}
		
		else {
			System.out.println("All program concepts: ");
			
			for (String each : concepts) {
				System.out.println(each);
			}
		}
	}
}
