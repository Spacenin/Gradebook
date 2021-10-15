package gradebook;

public class GradebookFullException extends Exception {
	GradebookFullException() {
		System.out.println("You can't do that, the gradebook is full!");
	}
}
