package gradebook;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.time.DateTimeException;
import java.util.Scanner;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Set of menu functions called by the main driver
public class Menu {
	private Scanner sc = new Scanner(System.in);
	
	//Add grade option
	public AssignmentInterface addGrade() {
		try {
			int userChoice;
			int tempScore;
			char tempLetter;
			String tempName;
			LocalDate tempDate;
			AssignmentInterface thisGrade;
			
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
				thisGrade = new Discussion();
				
				System.out.println("Enter the associated reading: ");
				String tempReading = sc.nextLine();
				((Discussion)thisGrade).setReading(tempReading);
			}
			
			//Sets the grade to add as a program and sets concept
			else if (userChoice == 2) {
				thisGrade = new Program();
				
				System.out.println("Enter the concept being tested: ");
				String tempConcept = sc.nextLine();
				((Program)thisGrade).setConcept(tempConcept);
			}
			
			//Sets the grade to add as a quiz and sets question number
			else if (userChoice == 3) {
				thisGrade = new Quiz();
				
				System.out.println("Enter the number of questions in the quiz: ");
				int tempQNum = sc.nextInt();
				sc.nextLine();

				((Quiz)thisGrade).setQNumber(tempQNum);
			}
			
			//Skips this grade if user did not enter one of the three
			else {
				System.out.println("You didn't pick a right choice! You have to go back now.");
				
				return(null);
			}
			
			//Enter the score of the grade and catch exception if non integer, setting the grade to null
			try {
				System.out.println("Enter the score: ");
				tempScore = sc.nextInt();
				sc.nextLine(); 					//Had to include nextLine after each single variable scan, as it would leave lingering newline char
			} catch (InputMismatchException exc) {
				System.out.println("That's not a number! It must be an integer value.");
				sc.nextLine();
				
				return(null);
			}
			
			//Enter the letter of the grade and catch exception if non character, setting the grade to null
			try {
				System.out.println("Enter the letter of the graded assignment: ");
				tempLetter = sc.next().charAt(0);
				sc.nextLine();
			} catch (InputMismatchException exc) {
				System.out.println("That's not a letter! It must be a character value.");
				sc.nextLine();
				
				return(null);
			}
			
			//Enter the name of the grade and catch exception if the non string, setting the grade to null
			try {
				System.out.println("Enter the name of the graded assignment: ");
				tempName = sc.nextLine();
			} catch (InputMismatchException exc) {
				System.out.println("That's not a string! It must be a string.");
				sc.nextLine();
				
				return(null);
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
				sc.nextLine();
				
				return(null);
			}
			catch (DateTimeException exc) {
				System.out.println("That date isn't right! Exiting branch...");
				
				return(null);
			}
			
			//Sets each variable
			thisGrade.setDue(tempDate);
			thisGrade.setLetter(tempLetter);
			thisGrade.setName(tempName);
			thisGrade.setScore(tempScore);
			
			//Checks that score and letter are valid combination
			if (!thisGrade.isValid()) {
				System.out.println("That grade is invalid, so it wasn't added!");
				
				return(null);
			}
			
			else {
				return(thisGrade);
			}
		}
		//Blanket case exception if input doesn't match
		catch (InputMismatchException exc) {
			System.out.println("That input isn't valid! Exiting branch...");
			sc.nextLine();
			
			return(null);
		}
	}
	
	//Loop through and print each grade
	public void printGrades(ArrayList<AssignmentInterface> grades) throws GradebookEmptyException {
		//Check if gradebook is empty
		if (grades.size() == 0) {
			throw new GradebookEmptyException();
		} 
		
		boolean looper = true;
		int userChoice;
		
		while (looper) {
			//Allow user to sort by option
			System.out.println("Enter how to sort grades by: ");
			System.out.println("1. Score (numeric)");
			System.out.println("2. Letter");
			System.out.println("3. Alphabetical name");
			System.out.println("4. Due date");
			System.out.print("--> ");
			
			try {
				userChoice = sc.nextInt();
				sc.nextLine();
			} catch (InputMismatchException exc) {
				System.out.println("Error, that was an invalid option! Please try again...");
				
				sc = new Scanner(System.in);
				userChoice = -1;
			}
			
			switch (userChoice) {
				case -1:
					break;
				case 1:
				case 2:
					Helpers.sortBookScore(grades);
					
					looper = false;
					break;
				case 3:
					Helpers.sortBookName(grades);
					
					looper = false;
					break;
				case 4:
					Helpers.sortBookDate(grades);
					
					looper = false;
					break;
				default:
					System.out.println("Invalid option! Try again...");
					
					break;
			}
		}
	}
	
	//Remove a specified grade by name
	public ArrayList<AssignmentInterface> removeGrade(ArrayList<AssignmentInterface> grades) throws GradebookEmptyException, InvalidGradeException {
		//Check if gradebook is empty
		if (grades.size() == 0) {
			throw new GradebookEmptyException();
		}
		
		sc = new Scanner(System.in);
		
		//Loop through to find the grade and remove it
		try {
			System.out.println("Names of all assignments:");
			
			//Displays currently stored names
			for (int i = 0; i < grades.size(); ++i) {
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
	
	//Mainly calls printFile in fileIO and checks if empty
	public void printFile(ArrayList<AssignmentInterface> grades) throws GradebookEmptyException {
		if (grades.size() == 0) {
			throw new GradebookEmptyException();
		}
		
		//Gets filename and calls fileIO print
		try {
			System.out.println("Enter the filename to print to <without the .txt extension>: ");
			
			String fileName = sc.nextLine();
			
			fileName = fileName + ".txt";
			
			FileIO.printToFile(fileName, grades);
		} catch (Exception exc) {
			System.out.println(exc);
		}
	}
	
	//Mainly calls FileIO's readFile, but gets fileName from user
	public ArrayList<AssignmentInterface> readFile() {
		ArrayList<AssignmentInterface> grades = new ArrayList<AssignmentInterface>();
		
		try {
			System.out.println("Enter the filename to read from <without the .txt extension>: ");
			
			String fileName = sc.nextLine();
			
			fileName = fileName + ".txt";
			
			grades = FileIO.readFile(fileName);
		} catch (Exception exc) {
			System.out.println(exc);
		}
		
		return(grades);
	}
	
	//Sends all of grades to MySQL database
	public void toMySQL(ArrayList<AssignmentInterface> grades) throws GradebookEmptyException {
		if (grades.size() == 0) {
			throw new GradebookEmptyException();
		}
		
		Connection dbConnect = DBUtil.getConnection();
		
		//Create table if it does not exist
		try {
			String createTable = "CREATE TABLE IF NOT EXISTS gradebook (" +
					"score int," +
				    "letter char," +
				    "gradeName varchar(100)," +
				    "dueDate varchar(100)," +
				    "concept varchar(100)," +
				    "reading varchar(100)," +
				    "qNumber int" +
				");";
			
			PreparedStatement ps = dbConnect.prepareStatement(createTable);
			ps.executeUpdate();
			
			String insert = "INSERT INTO gradebook VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement insertPS = dbConnect.prepareStatement(insert);
				
			//Add each grade, depending on its type
			for (int i = 0; i < grades.size(); ++i) {
				insertPS.setInt(1, grades.get(i).getScore());
				insertPS.setString(2, String.valueOf(grades.get(i).getLetter()));
				insertPS.setString(3, grades.get(i).getName());
				insertPS.setString(4, grades.get(i).getDue().toString());
				
				if (grades.get(i) instanceof Program) {
					insertPS.setString(5, ((Program)grades.get(i)).getConcept());
					insertPS.setNull(6, java.sql.Types.VARCHAR);
					insertPS.setNull(7, java.sql.Types.INTEGER);
				}
				
				else if (grades.get(i) instanceof Discussion) {
					insertPS.setNull(5, java.sql.Types.VARCHAR);
					insertPS.setString(6, ((Discussion)grades.get(i)).getReading());
					insertPS.setNull(7, java.sql.Types.INTEGER);
				}
				
				else {
					insertPS.setNull(5, java.sql.Types.VARCHAR);
					insertPS.setNull(6, java.sql.Types.VARCHAR);
					insertPS.setInt(7, ((Quiz)grades.get(i)).getQNumber());
				}
				
				insertPS.executeUpdate();
			}
		} catch (SQLException exc) {
			System.out.println(exc);
		}
	}
	
	//Gets all unadded grades from the SQL database and adds them to the local gradebook
	public ArrayList<AssignmentInterface> fromMySQL(ArrayList<AssignmentInterface> grades) {
		ArrayList<AssignmentInterface> gradesFrom = new ArrayList<AssignmentInterface>();
		Connection myDB = DBUtil.getConnection();
		
		try {
			PreparedStatement ps = myDB.prepareStatement("SELECT * FROM gradebook;");
			
			ResultSet selected = ps.executeQuery();
			
			//Get each grade from the SQL database
			while (selected.next()) {
				AssignmentInterface tempGrade;
				
				int tempScore = selected.getInt("score");
				char tempLetter = selected.getString("letter").charAt(0);
				String tempName = selected.getString("gradeName");
				LocalDate tempDate = LocalDate.parse(selected.getString("dueDate"));
				
				String tempConcept = selected.getString("concept");
				String tempReading = selected.getString("reading");
				int tempQNum = selected.getInt("qNumber");
				
				if (tempConcept != null) {
					tempGrade = new Program();
					
					((Program)tempGrade).setConcept(tempConcept);
				}
				
				else if (tempReading != null) {
					tempGrade = new Discussion();
					
					((Discussion)tempGrade).setReading(tempReading);
				}
				
				else {
					tempGrade = new Quiz();
					
					((Quiz)tempGrade).setQNumber(tempQNum);
				}
				
				tempGrade.setScore(tempScore);
				tempGrade.setLetter(tempLetter);
				tempGrade.setName(tempName);
				tempGrade.setDue(tempDate);
				
				gradesFrom.add(tempGrade);
			}
			
			//Check if local grades is greater than or less than SQL grades, and add or remove as needed
			if (grades.size() != gradesFrom.size()) {
				if (grades.size() < gradesFrom.size()) {					
					for (int i = grades.size(); i < gradesFrom.size(); ++i) {
						grades.add(gradesFrom.get(i));
					}
				}
				
				if (gradesFrom.size() < grades.size()) {	
					int gradesSized = grades.size();
					
					for (int i = gradesFrom.size(); i < gradesSized; ++i) {
						grades.remove(grades.size() - 1);
					}
				}
			}
		} catch (Exception exc) {
			System.out.println(exc);
		}
		
		return(grades);
	}
	
	//Search for all grades matching certain criteria
	public void searchMySQL() {
		boolean looper = true;
		Connection myDB = DBUtil.getConnection();
		int userChoice = 0;
		String searchQuery = "SELECT * FROM gradebook;";
		
		while (looper) {
			System.out.println("Enter how you would like to search by: ");
			System.out.println("1. Search for all quizzes");
			System.out.println("2. Search for all programs");
			System.out.println("3. Search for all discussions");
			System.out.println("4. Search for all grades within a score range");
			System.out.println("5. Search for all grades within a due date range");
			System.out.println("6. Search for all grades with an even score");
			System.out.print("--> ");
			
			//Gets user choice of how to search SQL database
			try {
				userChoice = sc.nextInt();
			} catch (InputMismatchException exc) {
				System.out.println("Error, that's not an integer!");
				
				sc = new Scanner(System.in);
				
				userChoice = 0;
			}
			
			switch (userChoice) {
				case 0:
					break;
				//Search for all quizzes
				case 1:
					searchQuery = "SELECT * FROM gradebook WHERE qNumber IS NOT NULL;";
					looper = false;
					
					break;
				//Search for all programs
				case 2:
					searchQuery = "SELECT * FROM gradebook WHERE concept IS NOT NULL;";
					looper = false;
					
					break;
				//Search for all discussion
				case 3:
					searchQuery = "SELECT * FROM gradebook WHERE reading IS NOT NULL;";
					looper = false;
					
					break;
				//Search for grade range
				case 4:
					int lower, upper;
					
					System.out.print("Enter the upper and lower range for scores: ");
					
					try {
						upper = sc.nextInt();
						lower = sc.nextInt();
					} catch (InputMismatchException exc) {
						System.out.println("Error, that's not an integer!");
						sc = new Scanner(System.in);
						
						break;
					}
					
					searchQuery = "SELECT * FROM gradebook WHERE score BETWEEN " + lower + " AND " + upper + ";";
					looper = false;
					
					break;
				//Search for due date range
				case 5:
					//Gets the upper limit due date from the user
					int upperYear, upperDay, upperMonth;
					
					System.out.print("Enter the upper date (M D Y): ");
					
					try {
						upperMonth = sc.nextInt();
						upperDay = sc.nextInt();
						upperYear = sc.nextInt();
					} catch (InputMismatchException exc) {
						System.out.println("Error, that's not an integer!");
						sc = new Scanner(System.in);
						
						break;
					}
					
					//Gets the lower limit due date from the user
					int lowerYear, lowerDay, lowerMonth;
					
					System.out.print("Enter the lower date (M D Y): ");
					
					try {
						lowerMonth = sc.nextInt();
						lowerDay = sc.nextInt();
						lowerYear = sc.nextInt();
					} catch (InputMismatchException exc) {
						System.out.println("Error, that's not an integer!");
						sc = new Scanner(System.in);
						
						break;
					} 
					
					LocalDate upperLimit = LocalDate.of(upperYear, upperMonth, upperDay);
					LocalDate lowerLimit = LocalDate.of(lowerYear, lowerMonth, lowerDay);
					
					//Get all due dates into arrayList
					String getDatesQuery = "SELECT dueDate FROM gradebook;";
					ArrayList<LocalDate> dates = new ArrayList<LocalDate>();
					ArrayList<LocalDate> dates2 = new ArrayList<LocalDate>();
					ArrayList<AssignmentInterface> gradesSearched = new ArrayList<AssignmentInterface>();
					
					try {
						PreparedStatement ps = myDB.prepareStatement(getDatesQuery);
						ResultSet rs = ps.executeQuery();
						
						while (rs.next()) {
							dates.add(LocalDate.parse(rs.getString("dueDate")));
						}
					} catch (SQLException exc) {
						System.out.println(exc);
						
						break;
					}
					
					//Add dates that fall into range
					for (int i = 0; i < dates.size(); ++i) {
						if ((dates.get(i).compareTo(lowerLimit) >= 0) && (dates.get(i).compareTo(upperLimit) <= 0)) {
							dates2.add(dates.get(i));
						}
					}
					
					//Select all data that corresponds with the dates
					for (int i = 0; i < dates2.size(); ++i) {
						String getThisOne = "SELECT * FROM gradebook WHERE dueDate = \"" + dates2.get(i).toString() + "\";";
						
						try {
							PreparedStatement ps = myDB.prepareStatement(getThisOne);
							ResultSet rs = ps.executeQuery();
							
							while (rs.next()) {
								AssignmentInterface tempGrade;
								
								int tempScore = rs.getInt("score");
								char tempLetter = rs.getString("letter").charAt(0);
								String tempName = rs.getString("gradeName");
								LocalDate tempDue = LocalDate.parse(rs.getString("dueDate"));
								
								String tempConcept = rs.getString("concept");
								String tempReading = rs.getString("reading");
								int tempQNum = rs.getInt("qNumber");
								
								if (tempConcept != null) {
									tempGrade = new Program();
									((Program)tempGrade).setConcept(tempConcept);
								}
								
								else if (tempReading != null) {
									tempGrade = new Discussion();
									((Discussion)tempGrade).setReading(tempReading);
								}
								
								else {
									tempGrade = new Quiz();
									((Quiz)tempGrade).setQNumber(tempQNum);
								}
								
								tempGrade.setDue(tempDue);
								tempGrade.setLetter(tempLetter);
								tempGrade.setName(tempName);
								tempGrade.setScore(tempScore);
								
								gradesSearched.add(tempGrade);
							}
						} catch (SQLException exc) {
							System.out.println(exc);
						}
					}
					
					//Print it out
					for (int i = 0; i < gradesSearched.size(); ++i) {
						System.out.println(i+1 + ". " + gradesSearched.get(i).toString());
					}
					
					//Must leave this function call so it doesn't reach below query
					return;
				//Search for grades with even score
				case 6:
					searchQuery = "SELECT * FROM gradebook WHERE score % 2 = 0;";
					looper = false;
					
					break;
				default:
					System.out.println("That's an invalid choice!");
					
					break;
			}
		}
		
		ArrayList<AssignmentInterface> grades = new ArrayList<AssignmentInterface>();
		
		//Get each grade from the database as needed
		try {
			PreparedStatement ps = myDB.prepareStatement(searchQuery);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				AssignmentInterface tempGrade;
				
				int tempScore = rs.getInt("score");
				char tempLetter = rs.getString("letter").charAt(0);
				String tempName = rs.getString("gradeName");
				LocalDate tempDue = LocalDate.parse(rs.getString("dueDate"));
				
				String tempConcept = rs.getString("concept");
				String tempReading = rs.getString("reading");
				int tempQNum = rs.getInt("qNumber");
				
				if (tempConcept != null) {
					tempGrade = new Program();
					((Program)tempGrade).setConcept(tempConcept);
				}
				
				else if (tempReading != null) {
					tempGrade = new Discussion();
					((Discussion)tempGrade).setReading(tempReading);
				}
				
				else {
					tempGrade = new Quiz();
					((Quiz)tempGrade).setQNumber(tempQNum);
				}
				
				tempGrade.setDue(tempDue);
				tempGrade.setLetter(tempLetter);
				tempGrade.setName(tempName);
				tempGrade.setScore(tempScore);
				
				grades.add(tempGrade);
			}
			
			//Print out each grade
			for (int i = 0; i < grades.size(); ++i) {
				System.out.println(i+1 + ". " + grades.get(i).toString());
			}
		} catch (SQLException exc) {
			System.out.println(exc);
		}
	}
}