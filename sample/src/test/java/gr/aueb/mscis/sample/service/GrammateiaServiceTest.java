package gr.aueb.mscis.sample.service;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import gr.aueb.mscis.sample.model.Epopteia;
import gr.aueb.mscis.sample.model.Movie;
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
}
