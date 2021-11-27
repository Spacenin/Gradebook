package gradebook;

import java.time.LocalDate;
import java.util.ArrayList;

public class Helpers {
	
	//Sort by score
	public static void sortBookScore(ArrayList<AssignmentInterface> grades) {
		int max = 0;
		int j = 0;
		int maxIndex = 0;
		ArrayList<Integer> printed = new ArrayList<Integer>();
		Boolean[] isPrinted = new Boolean[grades.size()];
		
		for (int i = 0; i < grades.size(); ++i) {
			isPrinted[i] = false;
		}
		
		//Iterate through every grade
		for (int i = 0; i < grades.size(); ++i) {
			max = 0;
			System.out.print(i+1 + ". ");
			
			//Check if each grade is greater than the max
			for (j = 0; j < grades.size(); ++j) {
				if (grades.get(j).getScore() > max) {
					//If score is greater than max, then check if it has been printed
					//If it has not already been printed, set max equal to it
					if (isPrinted[j] == false) {
						maxIndex = j;
						max = grades.get(j).getScore();
					}
				}
			}
			
			//Print out whatever
			System.out.println(grades.get(maxIndex).toString());
			isPrinted[maxIndex] = true;
			
			printed.add(grades.get(maxIndex).getScore());
		}
	}
	
	//Sort based on name
	public static void sortBookName(ArrayList<AssignmentInterface> grades) {
		int j = 0;
		int maxIndex = 0;
		ArrayList<String> printed = new ArrayList<String>();
		Boolean[] isPrinted = new Boolean[grades.size()];
		
		for (int i = 0; i < grades.size(); ++i) {
			isPrinted[i] = false;
		}
		
		//Iterate through every grade
		for (int i = 0; i < grades.size(); ++i) {
			String max = new String("");
			System.out.print(i+1 + ". ");
			
			//Check if each grade is greater than the max
			for (j = 0; j < grades.size(); ++j) {
				if (grades.get(j).getName().compareTo(max) > 0) {
					//If name is greater than max, then check if it has been printed
					//If it has not already been printed, set max equal to it
					if (isPrinted[j] == false) {
						maxIndex = j;
						max = grades.get(j).getName();
					}
				}
			}
			
			//Print out whatever
			System.out.println(grades.get(maxIndex).toString());
			isPrinted[maxIndex] = true;
			
			printed.add(grades.get(maxIndex).getName());
		}
	}
	
	//Sort based on date
		public static void sortBookDate(ArrayList<AssignmentInterface> grades) {
			int j = 0;
			int maxIndex = 0;
			ArrayList<LocalDate> printed = new ArrayList<LocalDate>();
			Boolean[] isPrinted = new Boolean[grades.size()];
			
			for (int i = 0; i < grades.size(); ++i) {
				isPrinted[i] = false;
			}
			
			//Iterate through every grade
			for (int i = 0; i < grades.size(); ++i) {
				LocalDate max = LocalDate.of(1, 1, 1);
				System.out.print(i+1 + ". ");
				
				//Check if each grade is greater than the max
				for (j = 0; j < grades.size(); ++j) {
					if (grades.get(j).getDue().compareTo(max) > 0) {
						//If date is greater than max, then check if it has been printed
						//If it has not already been printed, set max equal to it
						if (isPrinted[j] == false) {
							maxIndex = j;
							max = grades.get(j).getDue();
						}
					}
				}
				
				//Print out whatever
				System.out.println(grades.get(maxIndex).toString());
				isPrinted[maxIndex] = true;
				
				printed.add(grades.get(maxIndex).getDue());
			}
		}
	
	
}
