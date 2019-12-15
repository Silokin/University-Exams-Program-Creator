package gr.aueb.mscis.sample.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import gr.aueb.mscis.sample.contacts.EmailAddress;
import gr.aueb.mscis.sample.model.Epopteia;
import gr.aueb.mscis.sample.model.Epoptis;
import gr.aueb.mscis.sample.model.EpoptisState;
import gr.aueb.mscis.sample.model.MiDiathesimotita;
import gr.aueb.mscis.sample.model.Movie;
import gr.aueb.mscis.sample.model.Program;
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
	
	
	
	//thhelei kai 27/2/10 13:30 - 15:30 (same date hour)
	//thelei kai 27/2/20 12:30-15:30 (same date pio prin) 
	//kai antistoixa 14:30-15:30  (same date pio meta)
	@Test
	public void testNaMinSympeftoun()
	{
		EpopteiaService service = new EpopteiaService(em); 
		
		Epopteia epopteia1 = new Epopteia(new SimpleCalendar(27,2,20,14,30), new SimpleCalendar(27,2,20,15,30));
		Epopteia newEpopteia = service.saveEpopteia(epopteia1);
		
		Epopteia epopteia2 = new Epopteia(new SimpleCalendar(27,2,20,13,30), new SimpleCalendar(27,2,20,15,30));
		Epopteia newEpopteia2 = service.saveEpopteia(epopteia2);
		
		Assert.assertNotEquals(0, (int) newEpopteia.getId());
		em.close(); // close session
		
		// new session, data will be retrieved from database
		em = JPAUtil.getCurrentEntityManager();	

		EpoptisService serv2 = new EpoptisService(em);
		
		
		//find epoptis by email
		Epoptis epoptis = serv2.findEpoptisByMail(new EmailAddress("testAlex@aueb.gr"));

			
		//find an epopteia absed on id
		Epopteia savedEpopteia = em.find(Epopteia.class, newEpopteia.getId()); 
		Epopteia savedEpopteia2 = em.find(Epopteia.class, newEpopteia2.getId()); 
		
		//check if epopteia is not a null object
		Assert.assertNotNull(savedEpopteia);
		Assert.assertNotNull(savedEpopteia2);
		

		//ekxwrhsh epopteias gia certain epopti
		//tha bei
		savedEpopteia.ekxwrhshEpopteias(epoptis);
		savedEpopteia2.ekxwrhshEpopteias(epoptis);
		
		//expected 1 giati sti mia den mporei, kai i alli sumpeftei me mia alli epopteia
		Assert.assertEquals(1, epoptis.getEpopteies().size());
	}
	
	@Test 
	public void doNotAnathesiIfMiDiathesimotita()
	{
		EpopteiaService service = new EpopteiaService(em); 
		
		Epopteia epopteia1 = new Epopteia(new SimpleCalendar(27,2,20,13,30), new SimpleCalendar(27,2,20,15,30));
		Epopteia newEpopteia = service.saveEpopteia(epopteia1);
		
		Assert.assertNotEquals(0, (int) newEpopteia.getId());
		em.close(); // close session
		
		// new session, data will be retrieved from database
		em = JPAUtil.getCurrentEntityManager();	

		EpoptisService serv2 = new EpoptisService(em);
		
		
		//find epoptis by email
		Epoptis epoptis = serv2.findEpoptisByMail(new EmailAddress("testAlex@aueb.gr"));
		
		MiDiathesimotita mi = new MiDiathesimotita(new SimpleCalendar(27,2,20,00,00));	
		epoptis.addMiDiathesimotita(mi);
		
		//find an epopteia absed on id
		Epopteia savedEpopteia = em.find(Epopteia.class, newEpopteia.getId()); 
		
		//check if epopteia is not a null object
		Assert.assertNotNull(savedEpopteia);
		

		//ekxwrhsh epopteias gia certain epopti
		savedEpopteia.ekxwrhshEpopteias(epoptis);
		
		//expected 1 giati sti mia den mporei, kai i alli sumpeftei me mia alli epopteia
		Assert.assertEquals(0, epoptis.getEpopteies().size());
	}
	
	@Test
	public void anathesi()
	{ 

		EpopteiaService service = new EpopteiaService(em); 
		
		Epopteia epopteia1 = new Epopteia(new SimpleCalendar(27,2,20,13,30), new SimpleCalendar(27,2,20,15,30));
		Epopteia newEpopteia = service.saveEpopteia(epopteia1);
		
		Assert.assertNotEquals(0, (int) newEpopteia.getId());
		em.close(); // close session
		
		// new session, data will be retrieved from database
		em = JPAUtil.getCurrentEntityManager();	

		EpoptisService serv2 = new EpoptisService(em);
		
		
		//find epoptis by email
		Epoptis epoptis = serv2.findEpoptisByMail(new EmailAddress("testAlex@aueb.gr"));
		
		
		//find an epopteia absed on id
		Epopteia savedEpopteia = em.find(Epopteia.class, newEpopteia.getId()); 
		
		//check if epopteia is not a null object
		Assert.assertNotNull(savedEpopteia);
		

		//ekxwrhsh epopteias gia certain epopti
		savedEpopteia.ekxwrhshEpopteias(epoptis);
		
		//expected 1 giati sti mia den mporei, kai i alli sumpeftei me mia alli epopteia
		Assert.assertEquals(1, epoptis.getEpopteies().size());
	}
	
	@Test
	public void getListOfEpopteiesForAnExetastiki()
	{

		EpopteiaService service = new EpopteiaService(em); 
		
		Epopteia epopteia1 = new Epopteia(new SimpleCalendar(27,2,20,13,30), new SimpleCalendar(27,2,20,15,30));
		Epopteia newEpopteia = service.saveEpopteia(epopteia1);
		
		Assert.assertNotEquals(0, (int) newEpopteia.getId());
		em.close(); // close session
		
		// new session, data will be retrieved from database
		em = JPAUtil.getCurrentEntityManager();	
		
		//find an epopteia absed on id
		Epopteia savedEpopteia = em.find(Epopteia.class, newEpopteia.getId()); 
		
		//check if epopteia is not a null object
		Assert.assertNotNull(savedEpopteia);
		
		Program p = new Program(new SimpleCalendar(20,1,20,8,00), new SimpleCalendar(22,2,20,18,00));
		
		//set exetastiki for a certain epopteia
		savedEpopteia.setProgram(p);
		
		//expected 1 giati sti mia den mporei, kai i alli sumpeftei me mia alli epopteia
		Assert.assertEquals(1, p.getEpopteies().size());
	}
	
	
	@Test
	public void testCategoryExists()
	{
		EpoptisService serv2 = new EpoptisService(em);
		//find epoptis by email
		Epoptis epoptis = serv2.findEpoptisByMail(new EmailAddress("testAlex@aueb.gr"));
		

		//category of epoptis exists
		Assert.assertNotNull(epoptis.getCategory());
	}
	
	@Test
	public void testListOfMiDiathesimotita()
	{
	
		EpoptisService serv2 = new EpoptisService(em);
		//find epoptis by email
		Epoptis epoptis = serv2.findEpoptisByMail(new EmailAddress("testAlex@aueb.gr"));
		
		MiDiathesimotita mi = new MiDiathesimotita(new SimpleCalendar(27,2,20,0,0));	
		epoptis.addMiDiathesimotita(mi);
		//category of epoptis exists
		Assert.assertEquals(1,epoptis.getMiDiathesimotita().size());
	}
	
	@Test
	public void testEpoptes_Epopteia_And_Epopteia_Epoptes()
	{
		EpopteiaService service = new EpopteiaService(em); 
		
		Epopteia epopteia1 = new Epopteia(new SimpleCalendar(27,2,20,13,30), new SimpleCalendar(27,2,20,15,30));
		Epopteia newEpopteia = service.saveEpopteia(epopteia1);
		
		Assert.assertNotEquals(0, (int) newEpopteia.getId());
		em.close(); // close session
		
		// new session, data will be retrieved from database
		em = JPAUtil.getCurrentEntityManager();	
		
		//find an epopteia absed on id
		Epopteia savedEpopteia = em.find(Epopteia.class, newEpopteia.getId()); 
		
		//check if epopteia is not a null object
		Assert.assertNotNull(savedEpopteia);
		
		EpoptisService serv2 = new EpoptisService(em);
		//find epoptis by email
		Epoptis epoptis1 = serv2.findEpoptisByMail(new EmailAddress("testTasos@aueb.gr"));
		Epoptis epoptis2 = serv2.findEpoptisByMail(new EmailAddress("testGiannis@aueb.gr"));
		

		savedEpopteia.ekxwrhshEpopteias(epoptis1);
		savedEpopteia.ekxwrhshEpopteias(epoptis2);
		
		Assert.assertEquals(2,savedEpopteia.getEpoptes().size());
		Assert.assertEquals(1,epoptis1.getEpopteies().size());
		Assert.assertEquals(1,epoptis2.getEpopteies().size());
		
		
	}
	
	@Test 
	public void testStateOfEpoptis()
	{
	
		EpopteiaService service = new EpopteiaService(em); 
		
		Epopteia epopteia1 = new Epopteia(new SimpleCalendar(27,2,20,13,30), new SimpleCalendar(27,2,20,15,30));
		Epopteia newEpopteia = service.saveEpopteia(epopteia1);
		
		Epopteia epopteia2 = new Epopteia(new SimpleCalendar(24,2,20,13,30), new SimpleCalendar(24,2,20,15,30));
		Epopteia newEpopteia2 = service.saveEpopteia(epopteia2);
		
		Epopteia epopteia3 = new Epopteia(new SimpleCalendar(25,2,20,13,30), new SimpleCalendar(25,2,20,15,30));
		Epopteia newEpopteia3 = service.saveEpopteia(epopteia3);
		
		Assert.assertNotEquals(0, (int) newEpopteia.getId());
		Assert.assertNotEquals(0, (int) newEpopteia2.getId());
		Assert.assertNotEquals(0, (int) newEpopteia3.getId());
		em.close(); // close session
		
		// new session, data will be retrieved from database
		em = JPAUtil.getCurrentEntityManager();	
		
		//find an epopteia absed on id
		Epopteia savedEpopteia = em.find(Epopteia.class, newEpopteia.getId()); 
		Epopteia savedEpopteia2 = em.find(Epopteia.class, newEpopteia2.getId()); 
		Epopteia savedEpopteia3 = em.find(Epopteia.class, newEpopteia3.getId()); 
		
		//check if epopteia is not a null object
		Assert.assertNotNull(savedEpopteia);
		Assert.assertNotNull(savedEpopteia2);
		Assert.assertNotNull(savedEpopteia3);
		
		EpoptisService serv2 = new EpoptisService(em);
		//find epoptis by email
		Epoptis epoptis = serv2.findEpoptisByMail(new EmailAddress("testAlex@aueb.gr"));
		

		
		savedEpopteia.ekxwrhshEpopteias(epoptis);
		savedEpopteia2.ekxwrhshEpopteias(epoptis);
		savedEpopteia3.ekxwrhshEpopteias(epoptis);
		

		//check if he/she has exceeded max number of epopteies based on category
		//alex = ypopsifia didaktwr --> max = 3 epopteies
		Assert.assertEquals(EpoptisState.UNAVAILABLE,epoptis.getState());
	}
	
//	@SuppressWarnings("unchecked")
//	@Test
//	public void testAnathesiJpa() {
//
//		EpopteiaService service = new EpopteiaService(em); 
//		service.anathesiEpopteias(epoptisid, epopteia_id);
//		
//		loanService.findBorrower(Initializer.DIAMANTIDIS_ID);
//		Assert.assertNotNull(loanService.borrow(Initializer.UML_DISTILLED_ID1));
//
//		em.clear();
//		List<Loan> loanList = em.createQuery("select l from Loan l").getResultList();
//
//		Loan loan = loanList.get(0);
//
//		Assert.assertTrue(loan.isPending());
//		Assert.assertEquals(Initializer.UML_DISTILLED_ID1, loan.getItem().getItemNumber());
//		Assert.assertEquals(ItemState.LOANED, loan.getItem().getState());
//
//	}

	
//	@Test
//	public void testAnathesi()
//	{
//		
//		EpopteiaService service = new EpopteiaService(em); 
//		//update epopteia or create 
//		Epopteia epopteia1 = new Epopteia(new SimpleCalendar(27,2,20,13,30), new SimpleCalendar(27,2,20,15,30));
//		Epopteia newEpopteia = service.saveEpopteia(epopteia1);
//		
//		Epopteia epopteia2 = new Epopteia(new SimpleCalendar(25,2,20,13,30), new SimpleCalendar(25,2,20,15,30));
//		Epopteia newEpopteia2 = service.saveEpopteia(epopteia2);
//		
//		Epopteia epopteia3 = new Epopteia(new SimpleCalendar(24,2,20,13,30), new SimpleCalendar(24,2,20,15,30));
//		Epopteia newEpopteia3 = service.saveEpopteia(epopteia3);
//		
//		Epopteia epopteia4 = new Epopteia(new SimpleCalendar(23,2,20,13,30), new SimpleCalendar(23,2,20,15,30));
//		Epopteia newEpopteia4 = service.saveEpopteia(epopteia4);
//		
//		//same as 4, tryin' to ekxwrw se enan pou ehei akrivws tin idia mer akia wra
//		Epopteia epopteia5 = new Epopteia(new SimpleCalendar(23,2,20,13,30), new SimpleCalendar(23,2,20,15,30));
//		Epopteia newEpopteia5 = service.saveEpopteia(epopteia5);
//		
//		//einai sto endiameso tis 3
//		Epopteia epopteia6 = new Epopteia(new SimpleCalendar(27,2,20,12,30), new SimpleCalendar(23,2,20,15,30));
//		Epopteia newEpopteia6 = service.saveEpopteia(epopteia6);
//				
//		
//		
//		// EntityManager.persist() updates the ID of the persisted object
//		//vheck if epopteia has been saved in db
//		Assert.assertNotEquals(0, (int) newEpopteia.getId());
//		em.close(); // close session
//		
//		// new session, data will be retrieved from database
//		em = JPAUtil.getCurrentEntityManager();	
//
//		EpoptisService serv2 = new EpoptisService(em);
//		
//		
//		//find epoptis by email
//		Epoptis epoptis = serv2.findEpoptisByMail(new EmailAddress("testAlex@aueb.gr"));
//		
//		MiDiathesimotita mi = new MiDiathesimotita(new SimpleCalendar(24,2,20,13,30));
//		
//		epoptis.addMiDiathesimotita(mi);
//		
//		//find an epopteia absed on id
//		Epopteia savedEpopteia = em.find(Epopteia.class, newEpopteia.getId()); 
//		Epopteia savedEpopteia2 = em.find(Epopteia.class, newEpopteia2.getId()); 
//		Epopteia savedEpopteia3 = em.find(Epopteia.class, newEpopteia3.getId()); 
//		Epopteia savedEpopteia4 = em.find(Epopteia.class, newEpopteia4.getId()); 
//		Epopteia savedEpopteia5 = em.find(Epopteia.class, newEpopteia5.getId());
//		Epopteia savedEpopteia6 = em.find(Epopteia.class, newEpopteia6.getId());  
//		
//		
//		//check if epopteia is not a null object
//		Assert.assertNotNull(savedEpopteia);
//		Assert.assertNotNull(savedEpopteia2);
//		Assert.assertNotNull(savedEpopteia3);
//		Assert.assertNotNull(savedEpopteia4);
//		Assert.assertNotNull(savedEpopteia5);
//		Assert.assertNotNull(savedEpopteia6);
//		
//		
//		//ekxwrhsh epopteias gia certain epopti
//		//tha bei
//		savedEpopteia.ekxwrhshEpopteias(epoptis);
//		//savedEpopteia2.ekxwrhshEpopteias(epoptis);
//		
//		//auti edw i epopteia sumpiptei me ti MI DIATHESIMOTITA EPOPTI!!
//		savedEpopteia3.ekxwrhshEpopteias(epoptis);
//		
//		//tha bei giati ein monadiki
//		savedEpopteia4.ekxwrhshEpopteias(epoptis);
//		
//		//same as epopteia 4 (den tha mpei)
//		savedEpopteia5.ekxwrhshEpopteias(epoptis);
//		
//		//den tha mpei giati einai sto endiameso tis 1 h prin tin 1
//		savedEpopteia6.ekxwrhshEpopteias(epoptis);
//		
//		Program p = new Program(new SimpleCalendar(20,1,20,8,00), new SimpleCalendar(22,2,20,18,00));
//		
//		//set exetastiki for a certain epopteia
//		savedEpopteia.setProgram(p);
//		savedEpopteia2.setProgram(p);
//
//		//ategory of epoptis exists
//		Assert.assertNotNull(epoptis.getCategory());
//		
//		//get mi diathesimotita of an epoptis
//		//Assert.assertEquals(0,epoptis.getMiDiathesimotita());
//		
//		//get epoptes of a certain epopteia
//		//Assert.assertEquals(0, savedEpopteia.getEpoptes().size());
//		
//		//get epopteies of epoptis
//		//enw ekxwrw 4, ehei 3, kai einai ystera UNAIVALABLE, dioti i mia sumpeftei me 
//		//mi diathesimottia
//		//Assert.assertEquals(3, epoptis.getEpopteies().size());
//		
//		//expected 1 giati sti mia den mporei, kai i alli sumpeftei me mia alli epopteia
//		Assert.assertEquals(2, epoptis.getEpopteies().size());
//		
//		//get epopteies of a certain exetastiki
//		Assert.assertEquals(2,p.getEpopteies().size());
//		
//		//check if he/she has exceeded max number of epopteies based on category
//		//Assert.assertEquals(EpoptisState.UNAVAILABLE,epoptis.getState());
//		
//		//Assert.assertEquals();
//		
//	}
	
}
