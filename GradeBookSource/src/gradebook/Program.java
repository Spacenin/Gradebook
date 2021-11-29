package gradebook;

import java.time.LocalDate;

//Program grade type
public class Program implements AssignmentInterface {
	private String concept;
	private int score;
	private char letter;
	private String name;
	private LocalDate due;
	
	//Parameterized constructor
	public Program(String concept, int score, char letter, String name, LocalDate date) {
		this.concept = concept;
		this.score = score;
		this.letter = letter;
		this.name = name;
		due = date;
	}
	
	//Default constructor, calling above constructor
	public Program() {
		this(" ", 0, ' ', " ", LocalDate.now());
	}
	
	//Getters
	public String getConcept() {
		return(concept);
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
	
	//Setters
	public void setConcept(String concept) {
		this.concept = concept;
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
	
	//Return grade as a string (for printing)
	public String toString() {
		return("Name: " + name + "\n" + "   Score: " + score + "\n" + "   Letter: " + letter +
				"\n" + "   Due date: " + due.toString() + "\n" + "   Concept: " +
				concept);
	}
	
	//Checks if score and letter match
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
