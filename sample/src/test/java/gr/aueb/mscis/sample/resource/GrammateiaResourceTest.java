package gr.aueb.mscis.sample.resource;


import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.spi.TestContainerFactory;

import gr.aueb.mscis.sample.contacts.EmailAddress;
import gr.aueb.mscis.sample.model.*;

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
	
	
	public List<Aithousa> findAithousaByName(String name,EntityManager em) {
		//EntityManager em = JPAUtil.getCurrentEntityManager();
		
		AithousaService service = new AithousaService(em);
		List<Aithousa> aithouses = service.findAithousaByName(name);
		
		//em.close();
		
		return aithouses;
	}
	
	public List<Program> listPrograms(EntityManager em) {
		//EntityManager em = JPAUtil.getCurrentEntityManager();
		
		ProgramService service = new ProgramService(em);
		List<Program> programs = service.findAllPrograms();
		
		
		//em.close(); 
		return programs; 
	}
	
	public List<Epopteia> listEpopteies(EntityManager em) {
		//EntityManager em = JPAUtil.getCurrentEntityManager();
		
		EpopteiaService service = new EpopteiaService(em);
		List<Epopteia> epopteies = service.findAllEpopteies();
		
		//em.close(); 
		return epopteies; 
	}
	
	public Epoptis findEpoptisByMail(String mail,EntityManager em) {
		//EntityManager em = JPAUtil.getCurrentEntityManager();
		
		EpoptisService service = new EpoptisService(em);
		Epoptis epoptis = service.findEpoptisByMail(new EmailAddress(mail));
		
		//em.close();
		
		return epoptis;
	}
	
	public Mathima findMathimaByName(String name,EntityManager em) {
		//EntityManager em = JPAUtil.getCurrentEntityManager();
		
		MathimaService ms = new MathimaService(em);
		List<Mathima> mathima = ms.findMathimaByTitle(name);
		
		//em.close();
		return mathima.get(0);
	}
	
	public List<Mathima> findAllMathimata() {
	EntityManager em = JPAUtil.getCurrentEntityManager();
	
	MathimaService service = new MathimaService(em);
	List<Mathima> mathimata = service.findAllMathimata();
	
	em.close();
	 
	return mathimata;
	
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
