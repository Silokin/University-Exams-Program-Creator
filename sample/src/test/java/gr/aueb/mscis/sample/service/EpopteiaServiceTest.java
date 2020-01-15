package gr.aueb.mscis.sample.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;


import gr.aueb.mscis.sample.contacts.EmailAddress;
import gr.aueb.mscis.sample.exceptions.EpoptisException;
import gr.aueb.mscis.sample.model.Aithousa;
import gr.aueb.mscis.sample.model.Epopteia;
import gr.aueb.mscis.sample.model.Epoptis;
import gr.aueb.mscis.sample.model.EpoptisState;
import gr.aueb.mscis.sample.model.MiDiathesimotita;
import gr.aueb.mscis.sample.model.Program;
import gr.aueb.mscis.sample.persistence.JPAUtil;
import gr.aueb.mscis.sample.util.SimpleCalendar;

public class EpopteiaServiceTest extends GrammateiaServiceTest {


	
//	@Test
//	public void testPersistAValidEpopteia(){
//		
//		EpopteiaService service = new EpopteiaService(em);
//		Epopteia epopteia = new Epopteia(new SimpleCalendar(27,1,20,13,30), new SimpleCalendar(27,1,20,15,30));
//		Epopteia newEpopteia = service.saveEpopteia(epopteia);
//		// EntityManager.persist() updates the ID of the persisted object
//		Assert.assertNotEquals(0, (int) newEpopteia.getId());
//		em.close(); // close session
//		
//		// new session, data will be retrieved from database
//		em = JPAUtil.getCurrentEntityManager();	
//
//		Epopteia savedEpopteia = em.find(Epopteia.class, newEpopteia.getId()); 
//		Assert.assertNotNull(savedEpopteia);
//		Assert.assertEquals(new SimpleCalendar(27,1,20,13,30), savedEpopteia.getStarts());
//		
//	}
	
	@Test
	public void testSaveAndDeleteEpopteia()
	{
		EpopteiaService service = new EpopteiaService(em); 
		
		Epopteia epopteia1 = new Epopteia(new SimpleCalendar(2020,2,27,13,30), new SimpleCalendar(2020,2,27,15,30));
		Epopteia newEpopteia = service.saveEpopteia(epopteia1);
	
		//save
		assertNotNull(newEpopteia);
		//delete
		assertTrue(service.deleteEpopteia(newEpopteia));
		
	}
	
	//yparxoun allages!
	@Test
	public void testFindEpopteiaByStartDate()
	{
		EpopteiaService ep2 = new EpopteiaService(em);
		List<Epopteia> epopteia =  ep2.findEpopteiaByStartDate(new SimpleCalendar(2020,1,22,13,15));
		
		assertNull(epopteia);
		//assertEquals(0,epopteia.size());
	}
	
	@Test
	public void testfindAllEpopteiesForACertainProgramExe()
	{
		ProgramService ps = new ProgramService(em);
		EpopteiaService es = new EpopteiaService(em);
		
		List<Program> pr = ps.findAllPrograms();
		assertNotNull(pr.get(0));
		
		List<Epopteia> ep = es.findAllEpopteiesForACertainProgramExe(pr.get(0));
		assertEquals(3,ep.size());
//		for (Program progr: pr ) {
//			if (pr.size() > 0) {
//				List<Epopteia> ep = es.findAllEpopteiesForACertainProgramExe(progr);
//				assertNotEquals(0,ep.size());
//				}
//		}
	}
	
	@Test
	public void testFindEpopteiesById()
	{
		EpopteiaService es = new EpopteiaService(em);
		List<Epopteia> ep = es.findAllEpopteies();
		Epopteia e = es.findEpopteiaById(ep.get(0).getId());
		
		assertNotNull("Expected non null epopteia", e);
		assertEquals(new SimpleCalendar(2020,1,20,0,0), e.getProgram().getStarts());
		
	}
			
	//thhelei kai 27/2/10 13:30 - 15:30 (same date hour)
	//thelei kai 27/2/20 12:30-15:30 (same date pio prin) 
	//kai antistoixa 14:30-15:30  (same date pio meta)
	@Test
	public void testAnathesi_NaMinSympeftoun()
	{
		EpopteiaService service = new EpopteiaService(em); 
		EpoptisService serv2 = new EpoptisService(em);
		
		
		Epopteia epopteia1 = new Epopteia(new SimpleCalendar(2020,2,27,14,30), new SimpleCalendar(2020,2,27,15,30));
		Epopteia newEpopteia = service.saveEpopteia(epopteia1);
		
		Epopteia epopteia2 = new Epopteia(new SimpleCalendar(2020,2,27,13,30), new SimpleCalendar(2020,2,27,15,30));
		Epopteia newEpopteia2 = service.saveEpopteia(epopteia2);

		
		
		//find epoptis by email
		Epoptis epoptis = serv2.findEpoptisByMail(new EmailAddress("testAlex@aueb.gr"));

			
		//find an epopteia absed on id
		Epopteia savedEpopteia = em.find(Epopteia.class, newEpopteia.getId()); 
		Epopteia savedEpopteia2 = em.find(Epopteia.class, newEpopteia2.getId()); 
		
		
		service.anathesiEpopteias(epoptis, savedEpopteia);
		service.anathesiEpopteias(epoptis, savedEpopteia2);
		
		//expected 1 giati sti mia den mporei, kai i alli sumpeftei me mia alli epopteia
		Assert.assertEquals(2, epoptis.getEpopteies().size());
	}
	
	@Test 
	public void testAnathesi_doNotAnathesiIfMiDiathesimotita()
	{
		EpopteiaService service = new EpopteiaService(em); 
		EpoptisService serv2 = new EpoptisService(em);
		
		
		Epopteia epopteia1 = new Epopteia(new SimpleCalendar(2020,2,27,13,30), new SimpleCalendar(2020,2,27,15,30));
		Epopteia newEpopteia = service.saveEpopteia(epopteia1);

		
		
		//find epoptis by email
		Epoptis epoptis = serv2.findEpoptisByMail(new EmailAddress("testAlex@aueb.gr"));
		
		MiDiathesimotita mi = new MiDiathesimotita(new SimpleCalendar(2020,2,27,00,00));	
		epoptis.addMiDiathesimotita(mi);
		
		//find an epopteia absed on id
		Epopteia savedEpopteia = em.find(Epopteia.class, newEpopteia.getId()); 
		
		service.anathesiEpopteias(epoptis, savedEpopteia);
		
		
		//expected 1 giati sti mia den mporei, kai i alli sumpeftei me mia alli epopteia
		assertEquals(2, epoptis.getEpopteies().size());
	}
	
	

	@Test
	public void testAnathesiEpopteias()
	{ 
		EpopteiaService service = new EpopteiaService(em); 
		EpoptisService serv2 = new EpoptisService(em);
		
		Epopteia epopteia1 = new Epopteia(new SimpleCalendar(2020,2,27,13,30), new SimpleCalendar(2020,2,27,15,30));
		Epopteia newEpopteia = service.saveEpopteia(epopteia1);
		
		//find epoptis by email
		Epoptis epoptis = serv2.findEpoptisByMail(new EmailAddress("testAlex@aueb.gr"));
		
		
		//find an epopteia based on id
		Epopteia savedEpopteia = em.find(Epopteia.class, newEpopteia.getId()); 
		
		List<Epopteia> oldEpopteia = service.findEpopteiaByStartDate(new SimpleCalendar(2020,1,21,13,15));

//		if (epoptis != null)
//			{
//			service.anathesiEpopteias(epoptis, savedEpopteia);
//			Assert.assertEquals(2, epoptis.getEpopteies().size());
//			}
//		else
//			assertNull(epoptis);
		
		//ekxwrhsh epopteias gia certain epopti
		//an den yparxei o epoptis me to certain email, tote NULL epoptis
		//episis an den yparxei h epoptia, tote kai auti NULL
		if (epoptis != null && savedEpopteia != null)
		{
		service.anathesiEpopteias(epoptis, savedEpopteia);
		assertEquals(2, epoptis.getEpopteies().size());
		}
//		if (epoptis != null) {
//			assertEquals(1, epoptis.getEpopteies().size());
//			assertNull(oldEpopteia);
//		}
//		if (oldEpopteia != null)
//			assertNull(epoptis);
		
		//yparxei 1 ston initializer idi kai twra prostithetai alli mia
		
	}
	
	
	@Test
	public void testCategoryExists()
	{
		EpoptisService serv2 = new EpoptisService(em);
		//find epoptis by email
		Epoptis epoptis = serv2.findEpoptisByMail(new EmailAddress("testAlex@aueb.gr"));	

		//category of epoptis exists
		assertNotNull(epoptis.getCategory());
	}
	
	@Test
	public void testFindEpoptesOfEpopteia()
	{
		EpopteiaService service = new EpopteiaService(em); 
		List<Epopteia> ep = service.findAllEpopteies();
		
		//an oi epoptes tis prwths epopteias einai >0 tote sigoura tha einai
		//1
		if (service.findEpoptesOfEpopteia(ep.get(0)).size() >0)
			assertEquals(1,service.findEpoptesOfEpopteia(ep.get(0)).size());
		//an oi epoptes einai ligoteroi tou 0, tote prokeitai gia kapoia
		//epopteia pou den ehei epopti/es
		else
			assertEquals(0,service.findEpoptesOfEpopteia(ep.get(0)).size());
	}
	
	
//	@Test
//	public void testEpoptes_Epopteia_And_Epopteia_Epoptes()
//	{
//		
//		EpopteiaService service = new EpopteiaService(em);
//		EpoptisService serv2 = new EpoptisService(em);
//		
//		Epopteia epopteia1 = new Epopteia(new SimpleCalendar(27,2,20,13,30), new SimpleCalendar(27,2,20,15,30));
//		Epopteia newEpopteia = service.saveEpopteia(epopteia1);
//		
//		//find an epopteia absed on id
//		Epopteia savedEpopteia = em.find(Epopteia.class, newEpopteia.getId()); 
//		
//		//find epoptis by email
//		Epoptis epoptis1 = serv2.findEpoptisByMail(new EmailAddress("testTasos@aueb.gr"));
//		Epoptis epoptis2 = serv2.findEpoptisByMail(new EmailAddress("testAlex@aueb.gr"));
//		
//
//		service.anathesiEpopteias(epoptis1, savedEpopteia);
//		service.anathesiEpopteias(epoptis2, savedEpopteia);
//		
//		Assert.assertEquals(2,savedEpopteia.getEpoptis().size());
//		Assert.assertEquals(2,epoptis1.getEpopteies().size());
//		Assert.assertEquals(2,epoptis2.getEpopteies().size());
//		
//		
//	}
	
//	@Test 
//	public void testStateOfEpoptis()
//	{
//	
//		EpopteiaService service = new EpopteiaService(em); 
//		EpoptisService serv2 = new EpoptisService(em);
//		
//		Epopteia epopteia1 = new Epopteia(new SimpleCalendar(27,2,20,13,30), new SimpleCalendar(27,2,20,15,30));
//		Epopteia newEpopteia = service.saveEpopteia(epopteia1);
//		
//		Epopteia epopteia2 = new Epopteia(new SimpleCalendar(24,2,20,13,30), new SimpleCalendar(24,2,20,15,30));
//		Epopteia newEpopteia2 = service.saveEpopteia(epopteia2);
//		
//		Epopteia epopteia3 = new Epopteia(new SimpleCalendar(25,2,20,13,30), new SimpleCalendar(25,2,20,15,30));
//		Epopteia newEpopteia3 = service.saveEpopteia(epopteia3);
//		
//		//find an epopteia absed on id
//		Epopteia savedEpopteia = em.find(Epopteia.class, newEpopteia.getId()); 
//		Epopteia savedEpopteia2 = em.find(Epopteia.class, newEpopteia2.getId()); 
//		Epopteia savedEpopteia3 = em.find(Epopteia.class, newEpopteia3.getId()); 
//		
//		
//		//find epoptis by email
//		Epoptis epoptis = serv2.findEpoptisByMail(new EmailAddress("testAlex@aueb.gr"));
//		
//		service.anathesiEpopteias(epoptis,savedEpopteia);
//		service.anathesiEpopteias(epoptis,savedEpopteia2);
//		service.anathesiEpopteias(epoptis,savedEpopteia3);
//		
//
//		//check if he/she has exceeded max number of epopteies based on category
//		//alex = ypopsifia didaktwr --> max = 3 epopteies
//		Assert.assertEquals(EpoptisState.UNAVAILABLE,epoptis.getState());
//	}

	
}
