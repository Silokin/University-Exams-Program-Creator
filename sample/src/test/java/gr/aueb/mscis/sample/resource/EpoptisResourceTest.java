package gr.aueb.mscis.sample.resource;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Assert;
import org.junit.Test;

import gr.aueb.mscis.sample.model.Epoptis;

import static gr.aueb.mscis.sample.resource.GrammateiaUri.epoptisIdUri;
import static gr.aueb.mscis.sample.resource.GrammateiaUri.EPOPTES;
import static gr.aueb.mscis.sample.resource.GrammateiaUri.EPOPTES_ADD_MD;


public class EpoptisResourceTest extends GrammateiaResourceTest{

	@Override
	protected Application configure() {
		/*
		 * 
		 */
		return new ResourceConfig(EpoptisResource.class, DebugExceptionMapper.class);
	}
	
	@Test
	public void testCreateNewEpoptis() {

		// Create an epoptis info object and submit
		EpoptisInfo epoptisInfo = new EpoptisInfo("Dimitris", "Nikolis", "6985674589", "dnikolis@aueb.gr", "gav123","ΥΔ");

		Response response = target(EPOPTES).request().post(Entity.entity(epoptisInfo, MediaType.APPLICATION_JSON));

		// Check status and database state
		Assert.assertEquals(201, response.getStatus());
		Epoptis foundEpoptis = findEpoptisByMail("dnikolis@aueb.gr");
		Assert.assertEquals(epoptisInfo.getEmail(), foundEpoptis.getEmail().getAddress());

	}

	@Test
	public void testUpdateEpoptis() {

		// Find an epoptis and update its email
		Epoptis epoptis = findEpoptisByMail("testGiannis@aueb.gr");
		Assert.assertEquals(epoptis.getEmail().getAddress(),"testGiannis@aueb.gr");
		EpoptisInfo epoptisInfo = EpoptisInfo.wrap(epoptis);
		epoptisInfo.setEmail("inikolis@aueb.gr");

		// Submit the updated representation
		Response response = target(epoptisIdUri(Integer.toString(epoptisInfo.getId()))).request()
				.put(Entity.entity(epoptisInfo, MediaType.APPLICATION_JSON));

		// assertion on request status and database state
		Assert.assertEquals(200, response.getStatus());
		Epoptis foundEpoptis = findEpoptisByMail("inikolis@aueb.gr");
		Assert.assertEquals("inikolis@aueb.gr", foundEpoptis.getEmail().getAddress());
	}

	@Test
	public void testDeleteExistingEpoptis() {

		Epoptis epoptis = findEpoptisByMail("testGiannis@aueb.gr");
		
		Response response = target(epoptisIdUri(Integer.toString(epoptis.getId()))).request().delete();

		// assertion on request status and database state
		Assert.assertEquals(200, response.getStatus());
		Epoptis foundEpoptis = findEpoptisByMail("testGiannis@aueb.gr");
		Assert.assertNotNull(foundEpoptis.getEmail());

	}

	@Test
	public void testDeleteNonExistingEpoptis() {

		Response response = target(epoptisIdUri(Integer.toString(Integer.MAX_VALUE))).request().delete();

		Assert.assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());

	}

	@Test
	public void testAddMiDiathesimotita() {
		
		MiDiathesimotitaInfo mdi = new MiDiathesimotitaInfo(8,2,2020);
		
		
		Response response = target(EPOPTES_ADD_MD).queryParam("email","testGiannis@aueb.gr").queryParam("password", "1234").request().put(Entity.entity(mdi,MediaType.APPLICATION_JSON));
		
		Epoptis epoptis = findEpoptisByMail("testGiannis@aueb.gr");
		Assert.assertEquals(epoptis.getEmail().getAddress(),"testGiannis@aueb.gr");
		
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(epoptis.getMiDiathesimotita().isEmpty(),false);

	}
	
	@Test
	public void testAddMiDiathesimotitaWrong() {
		
		MiDiathesimotitaInfo mdi = new MiDiathesimotitaInfo(8,2,2020);
		
		Response response = target(EPOPTES_ADD_MD).queryParam("email","testGiannis@aueb.gr").queryParam("password", "5678").request().put(Entity.entity(mdi,MediaType.APPLICATION_JSON));
		
		Assert.assertEquals(401, response.getStatus());

	}

	
}
