package tests;

import org.junit.*;
import gradebook.*;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class AddTest {
	private static Menu m = new Menu();
	private static ArrayList<AssignmentInterface> grades = new ArrayList<AssignmentInterface>();
	
	@Before
	public void init() {
		grades = new ArrayList<AssignmentInterface>();
	}
	
	@Test
	public void testAddEmpty() {
		System.out.println("Add empty");
		AssignmentInterface q = new Quiz(12, 98, 'A', "testAdd", LocalDate.of(2001, 12, 27));
		
		grades.add(m.addGrade());
		
		assertEquals("The only element in grades should be equal to this", q.getDue(), grades.get(0).getDue());
		assertEquals("The only element in grades should be equal to this", q.getName(), grades.get(0).getName());
		assertEquals("The only element in grades should be equal to this", q.getScore(), grades.get(0).getScore());
		assertEquals("The only element in grades should be equal to this", q.getLetter(), grades.get(0).getLetter());
		assertEquals("The only element in grades should be equal to this", ((Quiz)q).getQNumber(), ((Quiz)grades.get(0)).getQNumber());
	}
	
	@Test
	public void testAddWithOne() {
		System.out.println("Add multiple");
		AssignmentInterface q = new Quiz(12, 98, 'A', "testAdd", LocalDate.of(2001, 12, 27));
		AssignmentInterface p = new Program("JUnit", 80, 'B', "testAdd2", LocalDate.of(2013, 1, 20));
		
		grades.add(m.addGrade());
		grades.add(m.addGrade());
		
		assertEquals("Both elements should be equal", q.getDue(), grades.get(0).getDue());
		assertEquals("Both elements should be equal", q.getName(), grades.get(0).getName());
		assertEquals("Both elements should be equal", q.getScore(), grades.get(0).getScore());
		assertEquals("Both elements should be equal", q.getLetter(), grades.get(0).getLetter());
		assertEquals("Both elements should be equal", ((Quiz)q).getQNumber(), ((Quiz)grades.get(0)).getQNumber());
		
		assertEquals("Both elements should be equal", p.getDue(), grades.get(1).getDue());
		assertEquals("Both elements should be equal", p.getName(), grades.get(1).getName());
		assertEquals("Both elements should be equal", p.getScore(), grades.get(1).getScore());
		assertEquals("Both elements should be equal", p.getLetter(), grades.get(1).getLetter());
		assertEquals("Both elements should be equal", ((Program)p).getConcept(), ((Program)grades.get(1)).getConcept());
	}
	
	@Test
	public void testAddRedundant() {
		System.out.println("Add multiple redundant");
		AssignmentInterface p = new Program("JUnit", 80, 'B', "testAdd2", LocalDate.of(2013, 1, 20));
		AssignmentInterface p2 = new Program("JUnit", 80, 'B', "testAdd2", LocalDate.of(2013, 1, 20));
		
		grades.add(m.addGrade());
		grades.add(m.addGrade());
		
		assertEquals("Both elements should be equal, despite being redundantly added", p.getDue(), grades.get(0).getDue());
		assertEquals("Both elements should be equal, despite being redundantly added", p.getName(), grades.get(0).getName());
		assertEquals("Both elements should be equal, despite being redundantly added", p.getScore(), grades.get(0).getScore());
		assertEquals("Both elements should be equal, despite being redundantly added", p.getLetter(), grades.get(0).getLetter());
		assertEquals("Both elements should be equal, despite being redundantly added", ((Program)p).getConcept(), ((Program)grades.get(0)).getConcept());
		
		assertEquals("Both elements should be equal, despite being redundantly added", p2.getDue(), grades.get(1).getDue());
		assertEquals("Both elements should be equal, despite being redundantly added", p2.getName(), grades.get(1).getName());
		assertEquals("Both elements should be equal, despite being redundantly added", p2.getScore(), grades.get(1).getScore());
		assertEquals("Both elements should be equal, despite being redundantly added", p2.getLetter(), grades.get(1).getLetter());
		assertEquals("Both elements should be equal, despite being redundantly added", ((Program)p2).getConcept(), ((Program)grades.get(1)).getConcept());
	}
	
	@Test
	public void testAddFailed() {
		System.out.println("Test add failure");
		
		grades.add(m.addGrade());
		
		assertNull("The gradebook should be empty if the add grades failed", grades.get(0));
	}
	
	@Test
	public void testAddThenFail() {
		System.out.println("Test add then fail");
		
		grades.add(m.addGrade());
		grades.add(m.addGrade());
		
		assertNull("The second element should be null, and first not null", grades.get(1));
		assertNotNull("The second element should be null, and first not null", grades.get(0));
	}
}
