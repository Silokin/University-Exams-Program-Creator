package gr.aueb.mscis.sample.resource;

import static gr.aueb.mscis.sample.resource.GrammateiaUri.EPOPTEIES;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Assert;
import org.junit.Test;

import gr.aueb.mscis.sample.model.Epopteia;
import gr.aueb.mscis.sample.model.Mathima;
import gr.aueb.mscis.sample.model.Program;

public class EpopteiaResourceTest extends GrammateiaResourceTest{
	
	@Override
	protected Application configure() {
		/*
		 * 
		 */
		return new ResourceConfig(EpopteiaResource.class, DebugExceptionMapper.class);
	}
	
	@Test
	public void testCreateNewEpopteia() {

		// Create an epoptis info object and submit
		Mathima mathima = findMathimaByName("Επιστήμη των Υπολογιστών"); 
		List<Program> programs = listPrograms();
		EpopteiaInfo epopteiaInfo = new EpopteiaInfo(mathima.getId(),programs.get(0).getId(),22,2,20,18,30,22,2,20,20,30);

		Response response = target(EPOPTEIES).request().post(Entity.entity(epopteiaInfo, MediaType.APPLICATION_JSON));

		// Check status and database state
		Assert.assertEquals(201, response.getStatus());
		List<Epopteia> epopteies = listEpopteies();
		Assert.assertEquals(4,epopteies.size());

	}

}
