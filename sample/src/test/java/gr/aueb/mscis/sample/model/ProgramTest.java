package gr.aueb.mscis.sample.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;


import org.junit.Before;
import org.junit.Test;
import gr.aueb.mscis.sample.model.Epopteia;
import gr.aueb.mscis.sample.model.Mathima;
import gr.aueb.mscis.sample.model.Program;
import gr.aueb.mscis.sample.util.SimpleCalendar;



public class ProgramTest {

	Mathima mathima;
	Epopteia epopteia;
	Program program;
	
	@Before
    public void setUp() {
        program = new Program(new SimpleCalendar(2020,1,12,11,0),new SimpleCalendar(2020,1,12,15,0));
        epopteia = new Epopteia(new SimpleCalendar(2020,1,12,11,1),new SimpleCalendar(2020,1,12,15,0));
        
        
	}
	 

 
	@Test
    public void testSetEpopteia() {

	
		program.addEpopteia(epopteia);
		Epopteia expected = new Epopteia(new SimpleCalendar(2020,1,12,11,0),new SimpleCalendar(2020,1,12,15,0)); 
		assertNotSame(expected,epopteia); 

    }
 
	
	@Test
    public void testSetEpopteia2() {
 
	
		program.addEpopteia(epopteia);
		Epopteia expected = new Epopteia(new SimpleCalendar(2020,1,12,11,0),new SimpleCalendar(2020,1,12,15,0)); 
		assertFalse(program.getEpopteies().contains(expected)); 

    }
	  
	 
	@Test
    public void testremoveEpopteia() {

	    program.addEpopteia(epopteia);
		program.removeEpopteia(epopteia);
		 
		assertEquals(program.getEpopteies().size(),0); 

    }
	

}


