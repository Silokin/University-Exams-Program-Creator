package gr.aueb.mscis.sample.resource;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import static gr.aueb.mscis.sample.resource.GrammateiaUri.PROGRAM;
import static gr.aueb.mscis.sample.resource.GrammateiaUri.programIdUri;
import static gr.aueb.mscis.sample.resource.GrammateiaUri.anaforaEpoptwnUri;
import static gr.aueb.mscis.sample.resource.GrammateiaUri.anaforaEpoptiwnUri;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Assert;
import org.junit.Test;


import gr.aueb.mscis.sample.model.Program;



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
		List<ProgramInfo> programs = target(PROGRAM).queryParam("username", "admin").queryParam("password", "qwerty").request().get(new GenericType<List<ProgramInfo>>() {
		});
		List<ProgramInfo> programs2 = target(PROGRAM).queryParam("username", "bad").queryParam("password", "guy").request().get(new GenericType<List<ProgramInfo>>() {
		});

		String firstProgramById = Integer.toString(programs.get(0).getId());
		ProgramInfo program = target(programIdUri(firstProgramById)).queryParam("username", "admin").queryParam("password", "qwerty").request().get(ProgramInfo.class);
		ProgramInfo program2 = target(programIdUri(firstProgramById)).queryParam("username", "bad").queryParam("password", "guy").request().get(ProgramInfo.class);
		Assert.assertNotNull(program);
		Assert.assertEquals(20, program.getStartD());
		Assert.assertNull(program2.getId());
		Assert.assertTrue(programs2.isEmpty());	
	}
	 
	@Test 
	public void testListAllPrograms2() {

		List<ProgramInfo> programs = target(PROGRAM).queryParam("username", "admin").queryParam("password", "qwerty").request().get(new GenericType<List<ProgramInfo>>() {
		});
		List<ProgramInfo> programs2 = target(PROGRAM).queryParam("username", "admin").queryParam("password", "guy").request().get(new GenericType<List<ProgramInfo>>() {
		});
		Assert.assertEquals(1, programs.size());
		Assert.assertTrue(programs2.isEmpty());
	}

	@Test
	public void testCreateNewProgram() {

		  
  
		// Create a program info object and submit
		ProgramInfo programInfo = new ProgramInfo(29,1,2020,13,2,2020);

		Response response = target(PROGRAM).queryParam("username", "admin").queryParam("password", "qwerty").request().post(Entity.entity(programInfo, MediaType.APPLICATION_JSON));
		Response response2 = target(PROGRAM).queryParam("username", "bad").queryParam("password", "guy").request().post(Entity.entity(programInfo, MediaType.APPLICATION_JSON));

		// Check status and database state
		Assert.assertEquals(201, response.getStatus());
		Assert.assertEquals(401, response2.getStatus());
		List<Program> foundPrograms = listPrograms();
		Assert.assertNotEquals(1, foundPrograms.size());
		Assert.assertEquals(2, foundPrograms.size());

	}

	@Test
	public void testDeleteNonExistingProgram() {

		Response response = target(programIdUri(Integer.toString(Integer.MAX_VALUE))).queryParam("username", "admin").queryParam("password", "qwerty").request().delete();
		Response response2 = target(programIdUri(Integer.toString(Integer.MAX_VALUE))).queryParam("username", "bad").queryParam("password", "guy").request().delete();

		Assert.assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
		Assert.assertEquals(401, response2.getStatus());
	}
	
	@Test
	public void testAnaforaEpoptwn() {
		
		List<Program> programs = listPrograms();
		
		Response response = target(anaforaEpoptwnUri(Integer.toString(programs.get(0).getId()))).queryParam("username", "admin").queryParam("password", "qwerty").request().get();
		Response response2 = target(anaforaEpoptwnUri(Integer.toString(programs.get(0).getId()))).queryParam("username", "admin").queryParam("password", "guy").request().get();
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(200, response2.getStatus());
		//System.out.println(response.readEntity(String.class));
	}
	
	@Test
	public void testAnaforaEpoptiwn() {
		
		List<Program> programs = listPrograms();
		
		Response response = target(anaforaEpoptiwnUri(Integer.toString(programs.get(0).getId()))).queryParam("username", "admin").queryParam("password", "qwerty").request().get();
		Response response2 = target(anaforaEpoptiwnUri(Integer.toString(programs.get(0).getId()))).queryParam("username", "admin").queryParam("password", "guy").request().get();
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(200, response2.getStatus());
		//System.out.println(response.readEntity(String.class));
	}
}
