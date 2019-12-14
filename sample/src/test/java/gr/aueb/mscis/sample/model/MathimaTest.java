package gr.aueb.mscis.sample.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import gr.aueb.mscis.sample.model.Epopteia;
import gr.aueb.mscis.sample.model.Mathima;

public class MathimaTest {

	Mathima mathima;
	Epopteia epopteia;
	
	@Before
    public void setUp() {
        mathima = new Mathima("vash",1,"kot");
        epopteia = new Epopteia();
        
	}
	 
	

 
	@Test
    public void testSemester() {


        mathima.setSemester(1);
        assertEquals(mathima.getSemester(),1);

    }
 
	@Test
    public void testTitle() {

        mathima.setTitle("vash");
        assertTrue(mathima.getTitle().equals("vash")); 

    }
	 
	@Test
    public void testTeacher() {

		 mathima.setTitle("kot");
	     assertTrue(mathima.getTeacher().equals("kot"));

    }
	
	@Test
    public void testEquals() {

        String title = "vash";
        String teacher = "kot";
        int semester = 1;
        Mathima mathima1 = new Mathima();
        Mathima mathima2 = new Mathima();
        mathima1.setTitle(title);
        mathima1.setSemester(semester);
        mathima1.setTeacher(teacher);
        mathima2.setTitle(title);
        mathima2.setSemester(semester);
        mathima2.setTeacher(teacher);
        
        assertTrue(mathima1.equals(mathima2));

    }
	
	@Test
    public void testNotEquals() {

        String title1 = "vash";
        String title2 = "vas";
        String teacher1 = "ko";
        String teacher2 = "koth";
        int semester1 = 2;
        int semester2 = 3;
        Mathima mathima1 = new Mathima();
        Mathima mathima2 = new Mathima();
        mathima1.setTitle(title1);
        mathima1.setSemester(semester1);
        mathima1.setTeacher(teacher1);
        mathima2.setTitle(title2);
        mathima2.setSemester(semester2);
        mathima2.setTeacher(teacher2);
        
        assertFalse(mathima1.equals(mathima2));
    }
	 
	@Test
    public void testEqualsNull() {

        String title = null;
        String teacher = null;
        int semester = 0;
        Mathima mathima1 = new Mathima();
        Mathima mathima2 = new Mathima();
        mathima1.setTitle(title);
        mathima1.setSemester(semester);
        mathima1.setTeacher(teacher);
        mathima2.setTitle(title);
        mathima2.setSemester(semester);
        mathima2.setTeacher(teacher);
        
        assertTrue(mathima1.equals(mathima2));

    }
	
	@Test
    public void testNotEqualsNull2() {

        String title1 = "vash";
        String title2 = null;
        String teacher1 = "ko";
        String teacher2 = null;
        int semester1 = 2;
        int semester2 = 0;
        Mathima mathima1 = new Mathima();
        Mathima mathima2 = new Mathima();
        mathima1.setTitle(title1);
        mathima1.setSemester(semester1);
        mathima1.setTeacher(teacher1);
        mathima2.setTitle(title2);
        mathima2.setSemester(semester2);
        mathima2.setTeacher(teacher2);
        
        assertFalse(mathima1.equals(mathima2));
    }
	
	@Test
    public void testNotEqualsNull3() {

        
        String teacher1 = "kot";
        String teacher2 = null;
        Mathima mathima1 = new Mathima();
        Mathima mathima2 = new Mathima();
        mathima1.setTeacher(teacher1);
        mathima2.setTeacher(teacher2);
        
        assertFalse(mathima1.equals(mathima2));
    }
	@Test
    public void testNotEqualsNull4() {

        String title1 = "vash";
        String title2 = null;
        Mathima mathima1 = new Mathima();
        Mathima mathima2 = new Mathima();
        mathima1.setTitle(title1);
        mathima2.setTitle(title2);
        
        assertFalse(mathima1.equals(mathima2));
    }
	@Test
    public void testNotEqualsNull5() {

        String title1 = null;
        String title2 = "vash";
        Mathima mathima1 = new Mathima();
        Mathima mathima2 = new Mathima();
        mathima1.setTitle(title1);
        mathima2.setTitle(title2);
        
        assertFalse(mathima1.equals(mathima2));
    }
	
	@Test
    public void testNotEqualsNull6() {

        String teacher1 = null;
        String teacher2 = "kot";
        Mathima mathima1 = new Mathima();
        Mathima mathima2 = new Mathima();
        mathima1.setTeacher(teacher1); 
        mathima2.setTeacher(teacher2);
        
        assertFalse(mathima1.equals(mathima2));
    }
	 @Test
	    public void testToString()
	    {
	        Mathima mathima = new Mathima();	
	        String expected = "Mathima [id=0, title=null, semester=0, teacher=null, epopteia=null]";
	        assertFalse(mathima.toString().equals(expected));
	    }


}


