package gradebook;

public class GradebookEmptyException extends Exception {
	//Prints out error message if gradebook is empty
	GradebookEmptyException() {
		System.out.println("What are you doing?? The list is empty!");
	}
}
