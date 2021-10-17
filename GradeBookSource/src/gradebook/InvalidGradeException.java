package gradebook;

public class InvalidGradeException extends Exception {
	//Prints out error message if grade isn't in the gradebook
	InvalidGradeException() {
		System.out.println("That grade doesn't exist!");
	}
}
