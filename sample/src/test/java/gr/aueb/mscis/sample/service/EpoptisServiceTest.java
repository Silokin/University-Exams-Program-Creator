package gr.aueb.mscis.sample.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import gr.aueb.mscis.sample.contacts.EmailAddress;
import gr.aueb.mscis.sample.model.Epoptis;

public class EpoptisServiceTest extends GrammateiaServiceTest {

	@Test
	public void findAll() {
		
		EpoptisService es = new EpoptisService(em);
		List<Epoptis> el = es.findAllEpoptes();
		Assert.assertNotNull(el);
		Assert.assertEquals(3, el.size());
	}
	
	@Test
	public void findByIdTest() {
		EpoptisService es = new EpoptisService(em);
		List<Epoptis> el = es.findAllEpoptes();
		Epoptis e = es.findEpoptisById(el.get(0).getId());
		Assert.assertEquals("Alex", e.getName());
		Assert.assertNotNull("Expected non null epoptis", e);
	}
	
	@Test
	public void findByMailTest() {
		EpoptisService es = new EpoptisService(em);
		EmailAddress email = new EmailAddress("testGiannis@aueb.gr");
		Epoptis e = es.findEpoptisByMail(email);
		Assert.assertEquals("Giannis", e.getName());
		Assert.assertNotNull("Expected non null epoptis", e);
	}
	
}
