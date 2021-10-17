package gradebook;

public class GradebookFullException extends Exception {
	//Prints out error message if the gradebook is full
	GradebookFullException() {
		System.out.println("You can't do that, the gradebook is full!");
	}
}
