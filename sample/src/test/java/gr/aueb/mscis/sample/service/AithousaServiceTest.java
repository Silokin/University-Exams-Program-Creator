package gr.aueb.mscis.sample.service;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import gr.aueb.mscis.sample.model.Aithousa;

import org.junit.Assert;

public class AithousaServiceTest extends GrammateiaServiceTest {
	
	@Test
	public void findAll() {
		AithousaService as = new AithousaService(em);
		List<Aithousa> al = as.findAllAithouses();
		Assert.assertNotNull(al);
		Assert.assertEquals(3, al.size());
	}
	
	@Test
	public void findByIdTest() {
		AithousaService as = new AithousaService(em);
		List<Aithousa> al = as.findAllAithouses();
		Aithousa a = as.findAithousaById(al.get(0).getId());
		Assert.assertEquals("Πλειάδες", a.getName());
		assertNotNull("Expected non null aithousa", a);
	}

}
