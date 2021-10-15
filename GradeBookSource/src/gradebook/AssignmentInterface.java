package gradebook;

import java.time.LocalDate;

public interface AssignmentInterface {
	int getScore();
	char getLetter();
	String getName();
	LocalDate getDue();
	
	void setScore(int score);
	void setLetter(char letter);
	void setName(String name);
	void setDue(LocalDate date);
	
	boolean isValid();
	String toString();
}
