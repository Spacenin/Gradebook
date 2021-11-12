package gradebook;

import java.util.ArrayList;

public class Helpers {
	
	//Sort by score
	public static ArrayList<AssignmentInterface> sortBookScore(ArrayList<AssignmentInterface> grades) {
		ArrayList<AssignmentInterface> gradesSorted = new ArrayList<AssignmentInterface>(grades.size());
		int max = 0;
		int j = 0;
		
		System.out.println(grades.size());
		
		//Iterate through and find greatest grade, add it to the sorted list, then remove it
		for (int i = 0; i < gradesSorted.size(); ++i) {
			max = grades.get(0).getScore();
			
			for (j = 0; j < grades.size(); ++j) {
				if (grades.get(j).getScore() > max) {
					max = grades.get(j).getScore();
				}
			}
		
			gradesSorted.set(i, (grades.get(j)));
			
			grades.remove(j);
		}
		
		return(gradesSorted);
	}
	
}
