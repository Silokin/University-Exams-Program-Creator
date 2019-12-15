package gr.aueb.mscis.sample.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import gr.aueb.mscis.sample.model.Epoptis;
import gr.aueb.mscis.sample.model.Program;
import gr.aueb.mscis.sample.util.SimpleCalendar;


public class ProgramServiceTest extends GrammateiaServiceTest {
	
	@Test
	public void findAll() {
		
		ProgramService ps = new ProgramService(em);
		List<Program> pl = ps.findAllPrograms();
		Assert.assertNotNull(pl);
		Assert.assertEquals(1, pl.size());
	}
	
	@Test
	public void findByIdTest() {
		ProgramService ps = new ProgramService(em);
		List<Program> pl = ps.findAllPrograms();
		Program p = ps.findProgramById(pl.get(0).getId());
		Assert.assertEquals(new SimpleCalendar(20,1,20,8,15), p.getStart());
		Assert.assertNotNull("Expected non null epoptis", p);
	}

}
