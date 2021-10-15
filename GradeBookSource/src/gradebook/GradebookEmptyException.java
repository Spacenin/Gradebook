package gradebook;

public class GradebookEmptyException extends Exception {
	GradebookEmptyException() {
		System.out.println("What are you doing?? The list is empty!");
	}
}
