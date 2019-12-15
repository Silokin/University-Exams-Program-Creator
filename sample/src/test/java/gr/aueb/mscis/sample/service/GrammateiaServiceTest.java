package gr.aueb.mscis.sample.service;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import gr.aueb.mscis.sample.contacts.EmailAddress;
import gr.aueb.mscis.sample.model.Epopteia;
import gr.aueb.mscis.sample.model.Epoptis;
import gr.aueb.mscis.sample.persistence.Initializer;
import gr.aueb.mscis.sample.persistence.JPAUtil;
import gr.aueb.mscis.sample.util.SimpleCalendar;

public class GrammateiaServiceTest {

	protected EntityManager em;
	Initializer dataHelper;
	
	public GrammateiaServiceTest()
	{
		super();
	}
	@Before
	public void setup(){
		// prepare database for each test
		em = JPAUtil.getCurrentEntityManager();
		dataHelper = new Initializer();
		dataHelper.prepareData();
		
	}
	
	@After
	public void tearDown(){
		em.close();
	}
	
	@Test
	public void testPersistAValidEpopteia(){
		
		EpopteiaService service = new EpopteiaService(em);
		Epopteia epopteia = new Epopteia(new SimpleCalendar(27,1,20,13,30), new SimpleCalendar(27,1,20,15,30));
		Epopteia newEpopteia = service.saveEpopteia(epopteia);
		// EntityManager.persist() updates the ID of the persisted object
		Assert.assertNotEquals(0, (int) newEpopteia.getId());
		em.close(); // close session
		
		// new session, data will be retrieved from database
		em = JPAUtil.getCurrentEntityManager();	

		Epopteia savedEpopteia = em.find(Epopteia.class, newEpopteia.getId()); 
		Assert.assertNotNull(savedEpopteia);
		Assert.assertEquals(new SimpleCalendar(27,1,20,13,30), savedEpopteia.getStarts());
		
	}
	
	@Test 
	public void testAnathesi()
	{
		
		EpopteiaService service = new EpopteiaService(em);
		Epopteia epopteia1 = new Epopteia(new SimpleCalendar(27,2,20,13,30), new SimpleCalendar(27,2,20,15,30));
		Epopteia newEpopteia = service.saveEpopteia(epopteia1);
		
		Epopteia epopteia2 = new Epopteia(new SimpleCalendar(25,2,20,13,30), new SimpleCalendar(25,2,20,15,30));
		Epopteia newEpopteia2 = service.saveEpopteia(epopteia2);
		
		Epopteia epopteia3 = new Epopteia(new SimpleCalendar(24,2,20,13,30), new SimpleCalendar(24,2,20,15,30));
		Epopteia newEpopteia3 = service.saveEpopteia(epopteia3);
		
		Epopteia epopteia4 = new Epopteia(new SimpleCalendar(23,2,20,13,30), new SimpleCalendar(23,2,20,15,30));
		Epopteia newEpopteia4 = service.saveEpopteia(epopteia4);
		
		// EntityManager.persist() updates the ID of the persisted object
		Assert.assertNotEquals(0, (int) newEpopteia.getId());
		em.close(); // close session
		
		// new session, data will be retrieved from database
		em = JPAUtil.getCurrentEntityManager();	

		EpoptisService serv2 = new EpoptisService(em);
		Epoptis epoptis = serv2.findEpoptisByMail(new EmailAddress("testAlex@aueb.gr"));
		
		Epopteia savedEpopteia = em.find(Epopteia.class, newEpopteia.getId()); 
		Epopteia savedEpopteia2 = em.find(Epopteia.class, newEpopteia2.getId()); 
		Epopteia savedEpopteia3 = em.find(Epopteia.class, newEpopteia3.getId()); 
		Epopteia savedEpopteia4 = em.find(Epopteia.class, newEpopteia4.getId()); 
		
		Assert.assertNotNull(savedEpopteia);
		
		savedEpopteia.ekxwrhshEpopteias(epoptis);
		savedEpopteia2.ekxwrhshEpopteias(epoptis);
		savedEpopteia3.ekxwrhshEpopteias(epoptis);
		savedEpopteia4.ekxwrhshEpopteias(epoptis);
		
		//Assert.assertEquals(null,epoptis.getMiDiathesimotita());
		//Assert.assertEquals(0, savedEpopteia.getEpoptes().size());
		Assert.assertEquals(3, epoptis.getEpopteies().size());
		
		
	}
	
}
