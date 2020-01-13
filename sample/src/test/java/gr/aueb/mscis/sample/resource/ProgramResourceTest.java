package gr.aueb.mscis.sample.resource;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import static gr.aueb.mscis.sample.resource.GrammateiaUri.AITHOUSES;
import static gr.aueb.mscis.sample.resource.GrammateiaUri.PROGRAM;
import static gr.aueb.mscis.sample.resource.GrammateiaUri.PROGRAM_SEARCH;
import static gr.aueb.mscis.sample.resource.GrammateiaUri.aithousaIdUri;
import static gr.aueb.mscis.sample.resource.GrammateiaUri.programIdUri;
import static org.junit.Assert.assertNotNull;
import static gr.aueb.mscis.sample.resource.GrammateiaUri.programSearchUri;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Assert;
import org.junit.Test;


import gr.aueb.mscis.sample.model.Program;
import gr.aueb.mscis.sample.model.Aithousa;
import gr.aueb.mscis.sample.model.Epopteia;
import gr.aueb.mscis.sample.model.Epoptis;
import gr.aueb.mscis.sample.util.SimpleCalendar;



public class ProgramResourceTest extends GrammateiaResourceTest {

	@Override
	protected Application configure() { 
		/*
		 * 
		 */ 
		return new ResourceConfig(ProgramResource.class, DebugExceptionMapper.class);
	}
	
	 
	@Test 
	public void testListAllPrograms() { 

		// get all programs 
		List<ProgramInfo> programs = target(PROGRAM).request().get(new GenericType<List<ProgramInfo>>() {
		});

		String firstProgramById = Integer.toString(programs.get(0).getId());
		ProgramInfo program = target(programIdUri(firstProgramById)).request().get(ProgramInfo.class);
		Assert.assertNotNull(program);
		Assert.assertEquals(20, program.getStartD());
	}
	 
	@Test 
	public void testListAllPrograms2() {

		List<ProgramInfo> programs = target(PROGRAM).request().get(new GenericType<List<ProgramInfo>>() {
		});
		Assert.assertEquals(1, programs.size());
	}

	@Test
	public void testCreateNewProgram() {

		  
  
		// Create a program info object and submit
		ProgramInfo programInfo = new ProgramInfo(29,1,2020,13,2,2020);

		Response response = target(PROGRAM).request().post(Entity.entity(programInfo, MediaType.APPLICATION_JSON));

		// Check status and database state
		Assert.assertEquals(201, response.getStatus());
		List<Program> foundPrograms = listPrograms();
		Assert.assertNotEquals(1, foundPrograms.size());

	}

	@Test
	public void testDeleteNonExistingProgram() {

		Response response = target(programIdUri(Integer.toString(Integer.MAX_VALUE))).request().delete();

		Assert.assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());

	}	
}
