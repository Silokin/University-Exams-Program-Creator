package gr.aueb.mscis.sample.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.h2.command.dml.Set;
import org.junit.Assert;
import org.junit.Test;

import gr.aueb.mscis.sample.model.Aithousa;
import gr.aueb.mscis.sample.model.Epopteia;
import gr.aueb.mscis.sample.model.Epoptis;
import gr.aueb.mscis.sample.model.Mathima;
import gr.aueb.mscis.sample.model.Program;
import gr.aueb.mscis.sample.util.SimpleCalendar;

public class ProgramServiceTest extends GrammateiaServiceTest {

	@Test
	public void testSaveAndDeleteProgram()
	{
		ProgramService service = new ProgramService(em); 
		
		Program program1 = new Program(new SimpleCalendar(20,2,20,8,15),new SimpleCalendar(22,2,20,20,15));
  		Program newProgram = service.saveProgram(program1);
	
		assertNotNull(newProgram);
		assertTrue(service.deleteProgram(newProgram));
		
	}
	
	@Test
	public void findAll() {
		
		ProgramService ps = new ProgramService(em);
		List<Program> pl = ps.findAllPrograms();
		assertNotNull(pl);
		assertEquals(1, pl.size());
	}
	
	@Test
	public void findByIdTest() {
		ProgramService ps = new ProgramService(em);
		List<Program> pl = ps.findAllPrograms();
		Program p = ps.findProgramById(pl.get(0).getId());
		assertEquals(new SimpleCalendar(20,1,20,8,15), p.getStarts());
		assertNotNull("Expected non null epoptis", p);
	}
	
	@Test
	public void testFindProgramByStartDate()
	{

		ProgramService service = new ProgramService(em); 
		List<Program> allPrograms = service.findAllPrograms();
		List<Program> savedOne = service.findProgramByStartDate(allPrograms.get(0).getStarts());
		//List<Program> savedP = service.findProgramByStartDate(new SimpleCalendar(20,1,20,8,15));
		assertNotNull(savedOne);
		assertEquals(1, savedOne.size());
		assertEquals(new SimpleCalendar(20,1,20,8,15), savedOne.get(0).getStarts());
	}
	
	@Test
	public void testGetListOfEpopteiesForAnExetastiki()
	{
		ProgramService service = new ProgramService(em); 
		List<Program> allPrograms = service.findAllPrograms();
		List<Epopteia> epopteies = service.getListOfEpopteiesForAnExetastiki(allPrograms.get(0));
		
		assertEquals(3, epopteies.size());
		
	}
	@Test
	public void testKatartisiProgrammatos()
	{
		MathimaService ms = new MathimaService(em);
		AithousaService as = new AithousaService(em);
		EpopteiaService es = new EpopteiaService(em);
		ProgramService ps = new ProgramService(em);
		
		List<Mathima> m = ms.findMathimaBySemester(1);
		List<Aithousa> a = as.findAithousaByKtirio("Ευελπίδων 1");
		List<Epopteia> e = es.findAllEpopteies();
		//Epopteia ep = es.findEpopteiaByStartDate(new SimpleCalendar(21,1,20,13,15));
		List<Program> p = ps.findAllPrograms();
				//ps.findProgramByStartDate(new SimpleCalendar(20,1,20,0,0));
		
		assertNotNull(m.get(0));
		assertNotNull(a.get(0));
		assertNotNull(e.get(0));
		assertNotNull(p.get(0));
		
		Program pr = ps.katartisiProgrammatos(m.get(0), e.get(0), a.get(0), p.get(0));
		assertTrue(pr.getEpopteies().contains(e.get(0)));
	}
//	@Test
//	public void testGetListOfEpoptesInAnExetastiki()
//	{
//		ProgramService service = new ProgramService(em); 
//		List<Program> allPrograms = service.findAllPrograms();
//		List<Epoptis> epoptes =  service.getListOfEpoptesInAnExetastiki(allPrograms.get(0));
//		
//		assertNotNull(epoptes);
//		//assertEquals(3, epoptes.size());
//	}
	
}
