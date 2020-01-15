package gr.aueb.mscis.sample.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import gr.aueb.mscis.sample.model.Aithousa;

public class AithousaServiceTest extends GrammateiaServiceTest {

	@Test
	public void testSaveAndDeleteAithousa()
	{
		AithousaService service = new AithousaService(em); 
		
		Aithousa aithousa1 = new Aithousa("Βυζάντιο", "2", 30, 2, "Ευελπίδων 1");
		Aithousa newAithousa = service.saveAithousa(aithousa1);
		//oti egine to save
		assertNotNull(newAithousa);
		
		//oti egine i diagrafi!
		assertTrue(service.deleteAithousa(newAithousa));
		
	}
	@Test
	public void findAll() {
		AithousaService as = new AithousaService(em);
		List<Aithousa> al = as.findAllAithouses();
		Assert.assertNotNull(al);
		Assert.assertEquals(3, al.size());
	}
	
	@Test
	public void findAithousaByIdTest() {
		AithousaService as = new AithousaService(em);
		List<Aithousa> al = as.findAllAithouses();
		Aithousa a = as.findAithousaById(al.get(0).getId());
		Assert.assertEquals("Πλειάδες", a.getName());
		assertNotNull("Expected non null aithousa", a);
	}

	@Test
	public void findAithousaByOrofosTest(){
		AithousaService as = new AithousaService(em);
		List<Aithousa> al = as.findAithousaByOrofos("2");
		Assert.assertEquals(1, al.size());
		Assert.assertEquals("Κωνσταντόπουλος", al.get(0).getName());
	}
	
	@Test
	public void findAithousaByNameTest(){
		AithousaService as = new AithousaService(em);
		List<Aithousa> al = as.findAithousaByName("Πλειάδες");
		Assert.assertEquals(1, al.size());
		//Assert.assertEquals("Κωνσταντόπουλος", al.get(0).getId());
	}
	
	@Test
	public void findAithousaByKtirioTest(){
		AithousaService as = new AithousaService(em);
		List<Aithousa> al = as.findAithousaByKtirio("Κεντρικό");
		Assert.assertEquals(1, al.size());
		Assert.assertEquals("Μιλτιάδης", al.get(0).getName());
	}
	
	
}
