package gr.aueb.mscis.sample.service;

import org.junit.Test;

import gr.aueb.mscis.sample.model.Program;

public class KatartisiProgrammatosTest extends GrammateiaServiceTest {
	
	@Test
	public void KatartisiProgrammatos() {
		ProgramService ps = new ProgramService(em);
		Program p = ps.findProgramById(1);
		
	}

}
