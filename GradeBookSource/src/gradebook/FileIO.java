package gradebook;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class FileIO {
	
	public static void printToFile(String fileName, ArrayList<AssignmentInterface> grades) {
		Path filePath = Paths.get("GradeTextFiles/" + fileName);
		
		//Check if exists
		if (Files.notExists(filePath)) {
			try {
				Files.createFile(filePath);
			} catch (IOException exc) {
				System.out.println(exc);
			}
		}
		
		File gradeFile = filePath.toFile();
		
		//Write to file for every grade
		try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(gradeFile)))) {
			for (int i = 0; i < grades.size(); ++i) {
				if (grades.get(i) instanceof Quiz) {
					writer.print("Quiz\t");
				}
				
				else if (grades.get(i) instanceof Program) {
					writer.print("Program\t");
				}
				
				else if (grades.get(i) instanceof Discussion) {
					writer.print("Discussion\t");
				}
				
				writer.print(grades.get(i).getName() + "\t" + grades.get(i).getScore() + "\t" + grades.get(i).getLetter() + "\t" + grades.get(i).getDue().toString());
				
				if (grades.get(i) instanceof Quiz) {
					writer.println("\t" + ((Quiz)grades.get(i)).getQNumber());
					System.out.println(((Quiz)grades.get(i)).getQNumber());
				}
				
				else if (grades.get(i) instanceof Program) {
					writer.println("\t" + ((Program)grades.get(i)).getConcept());
				}
				
				else if (grades.get(i) instanceof Discussion) {
					writer.println("\t" + ((Discussion)grades.get(i)).getReading());
				}
			}
		} catch (IOException exc) {
			System.out.println(exc);
		}
	}
	
	//Reads file and returns read gradebook
	public static ArrayList<AssignmentInterface> readFile(String fileName) {
		ArrayList<AssignmentInterface> grades = new ArrayList<AssignmentInterface>();
		AssignmentInterface tempGrade = null;
		
		Path filePath = Paths.get("GradeTextFiles/" + fileName);
		
		//If file doesn't exist, return null arrayLists
		if (Files.notExists(filePath)) {
			System.out.println("That file does not exist! Please try again...");
			
			return(null);
		}
		
		//Otherwise, read each line and parse the string into grades
		else {
			File gradesFile = filePath.toFile();
			
			try (BufferedReader read = new BufferedReader(new FileReader(gradesFile))) {
				String readedLine;
				
				while ((readedLine = read.readLine()) != null) {
					String[] parsed = readedLine.split("\t");
					
					if (parsed[0].equals("Quiz")) {
						tempGrade = new Quiz();
						
						((Quiz)tempGrade).setQNumber(Integer.valueOf(parsed[5]));
					}
					
					else if (parsed[0].equals("Discussion")) {
						tempGrade = new Discussion();
						
						((Discussion)tempGrade).setReading(parsed[5]);
					}
					
					else if (parsed[0].equals("Program")) {
						tempGrade = new Program();
						
						((Program)tempGrade).setConcept(parsed[5]);
					}
					
					else {
						System.out.println("Error, the file format was incorrect!");
					}
					
					tempGrade.setName(parsed[1]);
					tempGrade.setScore(Integer.valueOf(parsed[2]));
					tempGrade.setLetter(parsed[3].charAt(0));
					
					LocalDate tempDate = LocalDate.parse(parsed[4]);
					
					tempGrade.setDue(tempDate);
					
					grades.add(tempGrade);
				}
			} catch (IOException exc) {
				System.out.println(exc);
			}
		}
		
		return(grades);
	}
	
}
