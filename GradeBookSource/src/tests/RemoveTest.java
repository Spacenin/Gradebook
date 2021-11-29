package tests;

import org.junit.*;
import gradebook.*;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RemoveTest {
	private static Menu m = new Menu();
	private static ArrayList<AssignmentInterface> grades = new ArrayList<AssignmentInterface>();
	
	@Before
	public void init() {
		grades = new ArrayList<AssignmentInterface>();
	}
	
	@Test
	public void testRemoveEmpty() {
		System.out.println("Test gradebook is empty against remove");
		
		GradebookEmptyException exc = assertThrows(GradebookEmptyException.class, () -> {
			m.removeGrade(grades);
		}); 
		
		assertEquals("Assert that the gradebook is still empty", 0, grades.size());
	}
	
	@Test
	public void testRemoveNotThere() {
		System.out.println("Test grade not there");
		
		grades.add(new Quiz(12, 98, 'A', "testOfRemove", LocalDate.of(2005, 8, 13)));
		
		InvalidGradeException exc = assertThrows(InvalidGradeException.class, () -> {
			m.removeGrade(grades);
		});
		
		assertEquals("Assert that the gradebook still has one element", 1, grades.size());
	}
	
	@Test
	public void testRemoveRedundant() {
		System.out.println("Test to remove a redundant grade");
		
		grades.add(new Program("JUnit", 80, 'B', "testOfRemove", LocalDate.of(2007, 4, 21)));
		grades.add(new Program("JUnit", 80, 'B', "testOfRemove", LocalDate.of(2007, 4, 21)));
		grades.add(new Discussion("dictionary", 76, 'C', "testOfRemove2", LocalDate.of(2001, 3, 12)));
		
		try {
			m.removeGrade(grades);
		} catch (Exception exc) {
			System.out.println(exc);
		}
		
		assertEquals("There should only be one copy of testOfRemove", 2, grades.size());
		assertEquals("There should only be one copy of testOfRemove", "JUnit", ((Program)grades.get(0)).getConcept());
		assertEquals("There should only be one copy of testOfRemove", LocalDate.of(2007, 4, 21), grades.get(0).getDue());
		assertEquals("There should only be one copy of testOfRemove", 80, grades.get(0).getScore());
		assertEquals("There should only be one copy of testOfRemove", 'B', grades.get(0).getLetter());
		assertEquals("There should only be one copy of testOfRemove", "testOfRemove", grades.get(0).getName());
	}
	
	@Test
	public void testRemoveNormalToZero() {
		System.out.println("Test to remove grade normally to zero");
		
		grades.add(new Discussion("Dictionary", 76, 'C', "testOfRemove", LocalDate.of(2004, 4, 24)));
		
		try {
			m.removeGrade(grades);
		} catch (Exception exc) {
			System.out.println(exc);
		}
		
		assertEquals("The gradebook should now be empty", 0, grades.size());
	}
	
	@Test
	public void testRemoveNormal() {
		System.out.println("Test to remove grade normal");
		
		grades.add(new Discussion("Dictionary", 76, 'C', "testOfRemove", LocalDate.of(2004, 4, 24)));
		grades.add(new Quiz(14, 40, 'F', "testOfRemove2", LocalDate.of(2008, 8, 28)));
		
		try {
			m.removeGrade(grades);
		} catch (Exception exc) {
			System.out.println(exc);
		}
		
		assertEquals("The gradebook should only have one element", 1, grades.size());
		
		assertEquals("There should only be this element", 14, ((Quiz)grades.get(0)).getQNumber());
		assertEquals("There should only be this element", 40, grades.get(0).getScore());
		assertEquals("There should only be this element", 'F', grades.get(0).getLetter());
		assertEquals("There should only be this element", "testOfRemove2", grades.get(0).getName());
		assertEquals("There should only be this element", LocalDate.of(2008, 8, 28), grades.get(0).getDue());
	}
}
