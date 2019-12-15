package gr.aueb.mscis.sample.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import gr.aueb.mscis.sample.model.Mathima;

public class MathimaServiceTest extends GrammateiaServiceTest {
	
	@Test
	public void findAll() {
		MathimaService ms = new MathimaService(em);
		List<Mathima> ml = ms.findAllMathimata();
		Assert.assertNotNull(ml);
		Assert.assertEquals(4, ml.size());
	}
	
	@Test
	public void findByIdTest() {
		MathimaService ms = new MathimaService(em);
		List<Mathima> ml = ms.findAllMathimata();
		Mathima m = ms.findMathimaById(ml.get(0).getId());
		Assert.assertEquals("Επιστήμη των Υπολογιστών", m.getTitle());
		assertNotNull("Expected non null mathima", m);
	}
	
	@Test
    public void testFindMathimaByTitle(){
		MathimaService ms = new MathimaService(em);
		List<Mathima> ml = ms.findMathimaByTitle("Κρυπτογραφία");
		assertEquals(1, ml.size());
		Assert.assertEquals("Κρυπτογραφία", ml.get(0).getTitle());
		
    }
	
    @Test 
    public void testFindMathimaBySemester(){
    	MathimaService ms = new MathimaService(em);
		List<Mathima> ml = ms.findMathimaBySemester(2);
		assertEquals(1, ml.size());
		Assert.assertEquals(2, ml.get(0).getSemester());
    }

}
