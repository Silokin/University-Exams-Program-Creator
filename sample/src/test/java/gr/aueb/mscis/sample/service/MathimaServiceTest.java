package gr.aueb.mscis.sample.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import gr.aueb.mscis.sample.model.Aithousa;
import gr.aueb.mscis.sample.model.Mathima;

public class MathimaServiceTest extends GrammateiaServiceTest {

	@Test
	public void testSaveAndDeleteMathima()
	{
		MathimaService service = new MathimaService(em); 
		
		Mathima mathima1 =new Mathima("Γαλλικά 8",1,"Βουγιουκλίδου");
		Mathima newMathima = service.saveMathima(mathima1);
		//oti egine to save
		assertNotNull(newMathima);
		
		//oti egine i diagrafi!
		assertTrue(service.deleteMathima(newMathima));
		
	}
	@Test
	public void testFindMathimaById() {

		MathimaService service = new MathimaService(em);
		List<Mathima> allMathimata = service.findAllMathimata();
		
		Mathima m = service.findMathimaById(allMathimata.get(0).getId());
		
		Assert.assertNotNull("Expected non null mathima", m);
	}
	
	
	@Test
	public void testFindAllMathima()
	{
		MathimaService ms = new MathimaService(em);
		List<Mathima> ml = ms.findAllMathimata();
		Assert.assertNotNull(ml);
		Assert.assertEquals(4, ml.size());
	}
	
	@Test 
	public void testFindMathimaByTitle()
	{
		MathimaService ms = new MathimaService(em);
		List<Mathima> ml = ms.findMathimaByTitle("Κρυπτογραφία");
		Assert.assertEquals(1, ml.size());
		Assert.assertEquals("Κρυπτογραφία", ml.get(0).getTitle());
	}
	
	@Test 
	public void testFindMathimaBySemester()
	{
		
		MathimaService ms = new MathimaService(em);
		List<Mathima> ml = ms.findMathimaBySemester(2);
		Assert.assertEquals(1, ml.size());
		Assert.assertEquals(2, ml.get(0).getSemester());
	}
}
