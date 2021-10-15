package gradebook;

public class InvalidGradeException extends Exception {
	InvalidGradeException() {
		System.out.println("That grade doesn't exist!");
	}
}
