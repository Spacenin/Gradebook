package gradebook;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.time.DateTimeException;
import java.util.Scanner;

public class Menu {
	private static Scanner sc = new Scanner(System.in);
	
	public AssignmentInterface[] addGrade(AssignmentInterface[] grades, int elemNum) throws GradebookFullException {
		try {
			AssignmentInterface tryToAccess = grades[elemNum];
			int userChoice;
			int tempScore;
			char tempLetter;
			String tempName;
			LocalDate tempDate;
		
			System.out.println("Enter what type of grade you would like:");
			System.out.println("1. Discussion");
			System.out.println("2. Program");
			System.out.println("3. Quiz");
			System.out.print("--> ");
			userChoice = sc.nextInt();
			
			if (userChoice == 1) {
				grades[elemNum] = new Discussion();
				
				System.out.println("Enter the associated reading: ");
				String tempReading = sc.next();
				((Discussion)grades[elemNum]).setReading(tempReading);
			}
			
			else if (userChoice == 2) {
				grades[elemNum] = new Program();
				
				System.out.println("Enter the concept being tested: ");
				String tempConcept = sc.next();
				((Program)grades[elemNum]).setConcept(tempConcept);
			}
			
			else if (userChoice == 3) {
				grades[elemNum] = new Quiz();
				
				System.out.println("Enter the number of questions in the quiz: ");
				int tempQNum = sc.nextInt();
				((Quiz)grades[elemNum]).setQNumber(tempQNum);
			}
			
			else {
				System.out.println("You didn't pick a right choice! You have to go back now.");
				
				return(grades);
			}
			
			try {
				System.out.println("Enter the score: ");
				tempScore = sc.nextInt();
			} catch (InputMismatchException exc) {
				System.out.println("That's not a number! It must be an integer value.");
				grades[elemNum] = null;
				sc.nextLine();
				
				return(grades);
			}
			
			try {
				System.out.println("Enter the letter of the graded assignment: ");
				tempLetter = sc.next().charAt(0);
			} catch (InputMismatchException exc) {
				System.out.println("That's not a letter! It must be a character value.");
				grades[elemNum] = null;
				sc.nextLine();
				
				return(grades);
			}
			
			try {
				System.out.println("Enter the name of the graded assignment: ");
				tempName = sc.next();
			} catch (InputMismatchException exc) {
				System.out.println("That's not a string! It must be a string.");
				grades[elemNum] = null;
				sc.nextLine();
				
				return(grades);
			}
			
			try {
				System.out.println("Enter the due date of the assignment (m d y): ");
				int tempMonth, tempDay, tempYear;
				tempMonth = sc.nextInt();
				tempDay = sc.nextInt();
				tempYear = sc.nextInt();
				
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
				sc.nextLine();
				grades[elemNum] = null;
				
				return(grades);
			}
			
			grades[elemNum].setDue(tempDate);
			grades[elemNum].setLetter(tempLetter);
			grades[elemNum].setName(tempName);
			grades[elemNum].setScore(tempScore);
			
			if (!grades[elemNum].isValid()) {
				System.out.println("That grade is invalid, so it wasn't added!");
				grades[elemNum] = null;
			} 
		
			return(grades);
		} 
		catch (ArrayIndexOutOfBoundsException exc) {
			throw new GradebookFullException();
		}
		catch (InputMismatchException exc) {
			System.out.println("That input isn't valid! Exiting branch...");
			sc.nextLine();
			
			return(grades);
		}
	}
	
	public void printGrades(AssignmentInterface[] grades, int elemNum) throws GradebookEmptyException {
		if (elemNum == 0) {
			throw new GradebookEmptyException();
		} 
				
		for (int i = 1; i <= elemNum; ++i) {
			System.out.println(i + ". " + grades[i-1].toString());
		}
	}
	
	public AssignmentInterface[] removeGrade(AssignmentInterface[] grades, int elemNum) throws GradebookEmptyException, InvalidGradeException {
		if (elemNum == 0) {
			throw new GradebookEmptyException();
		}
		
		try {
			System.out.println("Enter the name of the assignment you'd like to remove: ");
			String tempName = sc.next();
			
			boolean looper = true;
			int itr = 0;
			
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
		} catch(ArrayIndexOutOfBoundsException exc) {
			throw new InvalidGradeException();
		}
		
		return(grades);
	}
	
	public void printAverage(AssignmentInterface[] grades, int elemNum) throws GradebookEmptyException {
		if (elemNum == 0) {
			throw new GradebookEmptyException();
		}
		
		double average = 0;
		
		for (int i = 0; i < elemNum; ++i) {
			average = average + grades[i].getScore();
		}
		
		average = average / elemNum;
		
		System.out.println("Average score of all grades: " + average);
	}
	
	public void printMaxMin(AssignmentInterface[] grades, int elemNum) throws GradebookEmptyException {
		if (elemNum == 0) {
			throw new GradebookEmptyException();
		}
		
		int max = -1;
		int min = 101;
		
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
	
	public void printQuizAverage(AssignmentInterface[] grades, int elemNum) throws GradebookEmptyException {
		if (elemNum == 0) {
			throw new GradebookEmptyException();
		}
		
		int quizCounter = 0;
		int averageQuestion = 0;
		
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
	
	public void printReadings(AssignmentInterface[] grades, int elemNum) throws GradebookEmptyException {
		if (elemNum == 0) {
			throw new GradebookEmptyException();
		}
		
		ArrayList<String> readings = new ArrayList<String>();
		int disCounter = 0;
		
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
	
	public void printConcepts(AssignmentInterface[] grades, int elemNum) throws GradebookEmptyException {
		if (elemNum == 0) {
			throw new GradebookEmptyException();
		}
		
		ArrayList<String> concepts = new ArrayList<String>();
		int proCounter = 0;
		
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
