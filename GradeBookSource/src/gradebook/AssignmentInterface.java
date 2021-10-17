package gradebook;

import java.time.LocalDate;

//Generalized interface to be implemented by each grade type
public interface AssignmentInterface {
	//Getters
	int getScore();
	char getLetter();
	String getName();
	LocalDate getDue();
	
	//Setters
	void setScore(int score);
	void setLetter(char letter);
	void setName(String name);
	void setDue(LocalDate date);
	
	//Check if score and letter match
	boolean isValid();
	//Return grade and values as a string (for printing)
	String toString();
}
