package gr.aueb.mscis.sample.service;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import gr.aueb.mscis.sample.contacts.EmailAddress;

import gr.aueb.mscis.sample.model.Epopteia;
import gr.aueb.mscis.sample.model.Epoptis;
import gr.aueb.mscis.sample.model.EpoptisCategory;
import gr.aueb.mscis.sample.model.MiDiathesimotita;
import gr.aueb.mscis.sample.util.SimpleCalendar;

public class EpoptisServiceTest extends GrammateiaServiceTest {

//	@SuppressWarnings("deprecation")
//	@Test
//	public void testfindEpopteiesByEpoptisIdd()
//	{
//		EpoptisService ep = new EpoptisService(em);
//		
//		Epoptis epoptis = ep.findEpoptisByMail(new EmailAddress("testAlex@aueb.gr"));
//		
//		List<Epopteia> epopteies = ep.findEpopteiesByEpoptisId(epoptis);
//		
//		Assert.assertEquals(1, epopteies.size());
//	}
	
//	@Test
//	public void testFindEpoptisByMail()
//	{
//
//		EpoptisService ep = new EpoptisService(em);
//		Epoptis epoptis = ep.findEpoptisByMail(new EmailAddress("testAlex@aueb.gr"));
//		assertNotNull("ola good", epoptis);
//		
//	}


	@Test
	public void testSaveAndDeleteEpoptisCategory()
	{
		EpoptisCategoryService service = new EpoptisCategoryService(em); 
		
		EpoptisCategory category = new EpoptisCategory("Επικούρος Καθηγητής",1);
		EpoptisCategory newCategory = service.saveEpoptisCategory(category);
		
		//oti egine to save
		assertNotNull(newCategory);
		//oti egine i diagrafi!
		assertTrue(service.deleteEpoptisCategory(newCategory));
		
	}
	
	@Test
	public void testFindEpoptisCategoryById() {
		EpoptisCategoryService ecs = new EpoptisCategoryService(em);
		List<EpoptisCategory> categories = ecs.findAllEpoptisCategories();
		EpoptisCategory category = ecs.findEpoptisCategoryById(categories.get(0).getId());
		assertNotNull("Expected non null epoptis", category);
	}
	
@Test
	public void testSaveAndDeleteMiDiathesimotita()
	{
		EpoptisService service = new EpoptisService(em); 
		
		List<Epoptis> epoptis = service.findAllEpoptes();
		MiDiathesimotita date = new MiDiathesimotita(new SimpleCalendar(25,1,2020,0,0));
		MiDiathesimotita newMiDiathesimotita = null;
		newMiDiathesimotita= service.saveMiDiathesimotita(date);
		//oti egine to save
		assertNotNull(newMiDiathesimotita);
		//oti egine i diagrafi!
		assertTrue(service.deleteMiDiathesimotita(newMiDiathesimotita));
		
		
		
	}
	
	@Test
	public void testSaveAndDeleteEpoptis()
	{
		EpoptisService service = new EpoptisService(em); 
		
		Epoptis epoptis1 = new Epoptis("OPA", "AUEB", new EmailAddress("testAUEB@aueb.gr"),null,"1234");
		Epoptis newEpoptis = service.saveEpoptis(epoptis1);
		
		//oti egine to save
		assertNotNull(newEpoptis);
		//oti egine i diagrafi!
		assertTrue(service.deleteEpoptis(newEpoptis));
		
	}
	
	@Test
	public void testFindAllEpoptes() {
		
		EpoptisService es = new EpoptisService(em);
		List<Epoptis> el = es.findAllEpoptes();
		assertNotNull(el);
		assertEquals(3, el.size());
	}
	
	@Test
	public void testFindEpoptisById() {
		EpoptisService es = new EpoptisService(em);
		List<Epoptis> el = es.findAllEpoptes();
		Epoptis e = es.findEpoptisById(el.get(0).getId());
		//assertEquals("Alex", e.getName());
		assertNotNull("Expected non null epoptis", e);
	}
	
	@Test
	public void findEpoptisByMailTest() {
		EpoptisService es = new EpoptisService(em);
		EmailAddress email = new EmailAddress("testGiannis@aueb.gr");
		Epoptis e = es.findEpoptisByMail(email);
		assertEquals("Giannis", e.getName());
		assertNotNull("Expected non null epoptis", e);
	}
	
	@Test
	public void testFindMiDiathesimotitaByEpoptis()
	{
		EpoptisService ep = new EpoptisService(em);
		Epoptis epoptis = ep.findEpoptisByMail(new EmailAddress("testAlex@aueb.gr"));
		List<MiDiathesimotita> mi_diath = ep.findMiDiathesimotitaOfEpoptis(epoptis);
		assertNotNull(mi_diath);
		assertEquals(1, mi_diath.size());
	}
	
	@Test
	public void testFindEpopteiesEpopti()
	{
		EpoptisService ep = new EpoptisService(em);
		Epoptis epoptis = ep.findEpoptisByMail(new EmailAddress("testAlex@aueb.gr"));
		List<Epopteia> epopteies = ep.findEpopteiesEpopti(epoptis);
		assertNotNull(epopteies);
		assertEquals(1,epopteies.size());
	}
	
//	@Test
//	public void testLogin()
//	{
//		EpoptisService ep = new EpoptisService(em);
//		//Epoptis epoptis = ep.findEpoptisByMail(new EmailAddress("testAlex@aueb.gr"));
//		//lathos email
//		assertFalse(ep.login(epoptis,new EmailAddress("testalex@aueb.gr"),"1234"));
//		//swsto email
//		assertTrue(ep.login(epoptis,new EmailAddress("testAlex@aueb.gr"),"1234"));
//		
//	}
}
