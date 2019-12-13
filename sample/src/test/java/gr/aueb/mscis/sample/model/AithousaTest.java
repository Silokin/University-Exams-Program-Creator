package gr.aueb.mscis.sample.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import org.junit.Before;
import org.junit.Test;



import gr.aueb.mscis.sample.model.Epopteia;
import gr.aueb.mscis.sample.model.Aithousa;

public class AithousaTest {

	Aithousa aithousa;
	Epopteia epopteia;
	
	@Before
    public void setUp() {
        aithousa = new Aithousa("mes","des",1,2,"fes");
        epopteia = new Epopteia();
        
	}
	
	


	@Test
    public void testName() {

        aithousa.setName("mes");
        assertTrue(aithousa.getName().equals("mes"));

    }
 
	@Test
    public void testOrofos() {

        aithousa.setOrofos("des");
        assertTrue(aithousa.getOrofos().equals("des"));

    }
	
	@Test
    public void testNoThesewn() {


        aithousa.setNoThesewn(1);
        assertTrue(aithousa.getNoThesewn().equals(1));
    }
	
	@Test
    public void testSemester() {


        aithousa.setNoEpoptes(1);
        assertTrue(aithousa.getNoEpoptes().equals(1));
        
	}
	
    @Test
     public void testKtirio() {

         aithousa.setKtirio("fes");
         assertTrue(aithousa.getKtirio().equals("fes"));

        }
         
    @Test
    public void testToString()
    {
        Aithousa aithousa = new Aithousa();	        
        assertFalse(aithousa.equals(aithousa.toString()));
    }
    
    
	
}


