package gradebook;

import java.time.LocalDate;

public class Discussion implements AssignmentInterface {
	private String reading;
	private int score;
	private char letter;
	private String name;
	private LocalDate due;
	
	Discussion(String reading, int score, char letter, String name, LocalDate date) {
		this.reading = reading;
		this.score = score;
		this.letter = letter;
		this.name = name;
		due = date;
	}
	
	Discussion() {
		this(" ", 0, ' ', " ", LocalDate.now());
	}
	
	public String getReading() {
		return(reading);
	}
	
	public int getScore() {
		return(score);
	}
	
	public char getLetter() {
		return(letter);
	}
	
	public String getName() {
		return(name);
	}
	
	public LocalDate getDue() {
		return(due);
	}
	
	public void setReading(String reading) {
		this.reading = reading;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public void setLetter(char letter) {
		this.letter = letter;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDue(LocalDate date) {
		due = date;
	}
	
	public String toString() {
		return("Name: " + name + "\n" + "   Score: " + score + "\n" + "   Letter: " + letter + 
				"\n" + "   Due date: " + due.toString() + "\n" + "   Reading: " +
				reading);
	}
	
	public boolean isValid() {
		if (letter == 'A' || letter == 'B' || letter == 'C' || letter == 'D' || letter == 'F') {}
		else {
			return(false);
		}
		
		if (score < 0 || score > 100) {
			return(false);
		}
		
		switch (letter) {
			case 'A':
				if (score < 90) {
					return(false);
				}
				
				break;
			case 'B':
				if (score < 80 || score >= 90) {
					return(false);
				}
				
				break;
			case 'C':
				if (score < 70 || score >= 80) {
					return(false);
				}
				
				break;
			case 'D':
				if (score < 60 || score >= 70) {
					return(false);
				}
	
				break;
			case 'F':
				if (score >= 60) {
					return(false);
				}
				
				break;
		}
		
		return(true);
	}
}
