package gr.aueb.mscis.sample.service;

import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import gr.aueb.mscis.sample.contacts.EmailAddress;
import gr.aueb.mscis.sample.contacts.EmailMessage;
import gr.aueb.mscis.sample.model.Epoptis;
import gr.aueb.mscis.sample.model.Program;
import gr.aueb.mscis.sample.util.SimpleCalendar;
import gr.aueb.mscis.sample.util.SystemDateStub;


public class NotificationServiceTest extends GrammateiaServiceTest {

	private EmailProviderStub provider;

	@Before
	public void beforeDatabasePreparation() {
		provider = new EmailProviderStub();
	}

	@After
	public void afterTearDown() {
		SystemDateStub.reset();
	}

	/**
	 * Πραγματοποιούμε δύο δανεισμούς. Για τον πρώτο έχει παρέλθει η προθεσμία
	 * επιστροφής, ενώ για το δεύτερο όχι. Περιμένουμε την αποστολή μόνο ενός
	 * μηνύματος καθυστέρησης για τον πρώτο δανεισμό.
	 */
	@Test
	public void sendMessageOnOverdueJpa() {
		// Ρυθμίζουμε την ημερομηνία του συστήματος
		// 1 μήνα πριν την εξεταστική

		//setSystemDateTo20thDecember2019();
		setSystemDateTo20thJanuary2020();
//		borrowUMLUserGuideToDiamantidis();
//
//		// Ρυθμίζουμε την ημερομηνία του συστήματος για
//		// την 1η Σεπτεμβρίου 20007 και δανειζουμε ένα αντίτυπο
//
//		setSystemDateTo1stSeptember2007();
//		borrowRefactoringToGiakoumakis();
//
//		// ρυθμίζουμε την ημερομηνία για την 1η Νοεμβρίου
//		setSystemDateTo1stNovember2007();
		
		Calendar date = Calendar.getInstance();
		ProgramService ps = new ProgramService(em);
		List<Program> p = ps.findAllPrograms();
		
		NotificationService service = new NotificationService();
		service.setProvider(provider);
		if (p.get(0) != null)
			service.notifyEpoptes(p.get(0), date.get(Calendar.DAY_OF_MONTH), date.get(Calendar.MONTH), date.get(Calendar.YEAR) );

		// empty persistence context, in order to retrieve again from database
		em.clear();

		EpoptisService ep = new EpoptisService(em);
		Epoptis alex = ep.findEpoptisByMail(new EmailAddress("testAlex@aueb.gr"));
		//ta emails einai osa kai oi epoptes!!
		//ara 3
		Assert.assertEquals(3, provider.allMessages.size());
		
		if (provider.allMessages.size() >= 1)
			{
			EmailMessage message = provider.getAllEmails().get(1);
			Assert.assertEquals(alex.getEmail(), message.getTo());
			}
	}

	public void sendMessageOnOverdue() {

	}

	//ena mina prin tin eksetastiki
	private void setSystemDateTo20thDecember2019() {
		SystemDateStub.setStub(new SimpleCalendar(20, 12, 19, 0, 0));
	}

	//tin imera tis eksetastikis
	private void setSystemDateTo20thJanuary2020() {
		SystemDateStub.setStub(new SimpleCalendar(20, 1, 20, 0, 0));
	}
//
//	private void setSystemDateTo1stSeptember2007() {
//		SystemDateStub.setStub(new SimpleCalendar(2007, 9, 1));
//	}

//	private void borrowUMLUserGuideToDiamantidis() {
//		LoanService service = new LoanService(em);
//		service.findBorrower(Initializer.DIAMANTIDIS_ID);
//		service.borrow(Initializer.UML_USER_GUIDE_ID1);
//	}
//
//	private void borrowRefactoringToGiakoumakis() {
//		LoanService service = new LoanService(em);
//		service.findBorrower(Initializer.GIAKOUMAKIS_ID);
//		service.borrow(Initializer.UML_REFACTORING_ID);
//	}

}
