package gr.aueb.mscis.sample.resource;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.spi.TestContainerFactory;

import gr.aueb.mscis.sample.contacts.EmailAddress;
import gr.aueb.mscis.sample.model.*;

import gr.aueb.mscis.sample.resource.*;
import gr.aueb.mscis.sample.persistence.*;
import gr.aueb.mscis.sample.service.*;




public abstract class GrammateiaResourceTest extends JerseyTest {

	Initializer dataHelper;

	public GrammateiaResourceTest() {
		super();
	}

	public GrammateiaResourceTest(TestContainerFactory testContainerFactory) {
		super(testContainerFactory);
	}

	public GrammateiaResourceTest(Application jaxrsApplication) {
		super(jaxrsApplication);
	}

	@Override
	public void setUp() throws Exception {
		super.setUp();
		dataHelper = new Initializer();
		dataHelper.prepareData();
	}

	public List<Mathima> listMathimata() {
		EntityManager em = JPAUtil.getCurrentEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		List<Mathima> mathimata = em.createQuery("select m from Mathima m").getResultList();
		
		tx.commit();
		em.close();
		return mathimata;
	}
	
	public List<Aithousa> findAithousaByName(String name) {
		EntityManager em = JPAUtil.getCurrentEntityManager();
		
		AithousaService service = new AithousaService(em);
		List<Aithousa> aithouses = service.findAithousaByName(name);
		
		em.close();
		
		return aithouses;
	}
	
	public Epoptis findEpoptisByMail(String mail) {
		EntityManager em = JPAUtil.getCurrentEntityManager();
		
		EpoptisService service = new EpoptisService(em);
		Epoptis epoptis = service.findEpoptisByMail(new EmailAddress(mail));
		
		em.close();
		
		return epoptis;
	}

//	protected List<Borrower> findBorrowerByLastName(String lastName) {
//		EntityManager em = JPAUtil.getCurrentEntityManager();
//	
//		BorrowerService service = new BorrowerService(em);
//	
//		List<Borrower> publishers = service.findBorrowersByLastName(lastName);
//	
//		em.close();
//		return publishers;
//	}
//
//	protected Loan findPendingLoanForItem(int itemId) {
//	
//		EntityManager em = JPAUtil.getCurrentEntityManager();
//		LoanService service = new LoanService(em);
//	
//		Loan loan = service.findPendingLoan(itemId);
//		em.close();
//		return loan;
//	}
	
}
