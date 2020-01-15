package gr.aueb.mscis.sample.resource;

import javax.persistence.EntityManager;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Assert;
import org.junit.Test;

import gr.aueb.mscis.sample.model.Epoptis;
import gr.aueb.mscis.sample.persistence.JPAUtil;

import static gr.aueb.mscis.sample.resource.GrammateiaUri.epoptisIdUri;

import java.util.List;

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
	public void testListEpoptisById() { 

	
		List<EpoptisInfo> epoptes = target(EPOPTES).request().get(new GenericType<List<EpoptisInfo>>() {
		});
 
		String firstEpoptisById = Integer.toString(epoptes.get(0).getId());
		EpoptisInfo epoptis = target(epoptisIdUri(firstEpoptisById)).request().get(EpoptisInfo.class);
		Assert.assertNotNull(epoptis);
		Assert.assertEquals("Alex", epoptis.getName());
	} 
	
	@Test
	public void testListAllEpoptes() {

		List<EpoptisInfo> epoptes = target(EPOPTES).request().get(new GenericType<List<EpoptisInfo>>() {
		});
		Assert.assertEquals(3, epoptes.size());
	}
	
	@Test
	public void testCreateNewEpoptis() {
		EntityManager em = JPAUtil.getCurrentEntityManager();
		
		// Create an epoptis info object and submit
		EpoptisInfo epoptisInfo = new EpoptisInfo("Dimitris", "Nikolis", "6985674589", "dnikolis@aueb.gr", "gav123","ΥΔ");

		Response response = target(EPOPTES).queryParam("username", "admin").queryParam("password", "qwerty").request().post(Entity.entity(epoptisInfo, MediaType.APPLICATION_JSON));
		Response response2 = target(EPOPTES).queryParam("username", "bad").queryParam("password", "guy").request().post(Entity.entity(epoptisInfo, MediaType.APPLICATION_JSON));

		// Check status and database state
		Assert.assertEquals(201, response.getStatus());
		Assert.assertEquals(401, response2.getStatus());
		Epoptis foundEpoptis = findEpoptisByMail("dnikolis@aueb.gr",em);
		Assert.assertEquals(epoptisInfo.getEmail(), foundEpoptis.getEmail().getAddress());
		em.close();
	}

	@Test
	public void testUpdateEpoptis() {
		EntityManager em = JPAUtil.getCurrentEntityManager();
		
		// Find an epoptis and update its email
		Epoptis epoptis = findEpoptisByMail("testGiannis@aueb.gr",em);
		Assert.assertEquals(epoptis.getEmail().getAddress(),"testGiannis@aueb.gr");
		EpoptisInfo epoptisInfo = EpoptisInfo.wrap(epoptis);
		em.close();
		epoptisInfo.setEmail("inikolis@aueb.gr");

		// Submit the updated representation
		Response response = target(epoptisIdUri(Integer.toString(epoptisInfo.getId()))).queryParam("username", "admin").queryParam("password", "qwerty").request()
				.put(Entity.entity(epoptisInfo, MediaType.APPLICATION_JSON));
		Response response2 = target(epoptisIdUri(Integer.toString(epoptisInfo.getId()))).queryParam("username", "bad").queryParam("password", "guy").request()
				.put(Entity.entity(epoptisInfo, MediaType.APPLICATION_JSON));

		// assertion on request status and database state
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(401, response2.getStatus());
		EntityManager em2 = JPAUtil.getCurrentEntityManager();
		Epoptis foundEpoptis = findEpoptisByMail("inikolis@aueb.gr",em2);
		em2.close();
		Assert.assertEquals("inikolis@aueb.gr", foundEpoptis.getEmail().getAddress());
	}

	@Test
	public void testDeleteExistingEpoptis() {
		EntityManager em = JPAUtil.getCurrentEntityManager();
		
		Epoptis epoptis = findEpoptisByMail("testGiannis@aueb.gr",em);
		
		Response response = target(epoptisIdUri(Integer.toString(epoptis.getId()))).queryParam("username", "admin").queryParam("password", "qwerty").request().delete();
		Response response2 = target(epoptisIdUri(Integer.toString(epoptis.getId()))).queryParam("username", "bad").queryParam("password", "guy").request().delete();

		// assertion on request status and database state
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(401, response2.getStatus());
		Epoptis foundEpoptis = findEpoptisByMail("testGiannis@aueb.gr",em);
		Assert.assertNotNull(foundEpoptis.getEmail());
		em.close();

	}

	@Test
	public void testDeleteNonExistingEpoptis() {

		Response response = target(epoptisIdUri(Integer.toString(Integer.MAX_VALUE))).queryParam("username", "admin").queryParam("password", "qwerty").request().delete();
		Response response2 = target(epoptisIdUri(Integer.toString(Integer.MAX_VALUE))).queryParam("username", "bad").queryParam("password", "guy").request().delete();

		Assert.assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
		Assert.assertEquals(401, response2.getStatus());
	}

	@Test
	public void testAddMiDiathesimotita() {
		
		MiDiathesimotitaInfo mdi = new MiDiathesimotitaInfo(8,2,2020);
		
		
		Response response = target(EPOPTES_ADD_MD).queryParam("email","testGiannis@aueb.gr").queryParam("password", "1234").request().put(Entity.entity(mdi,MediaType.APPLICATION_JSON));
		
		EntityManager em = JPAUtil.getCurrentEntityManager();
		Epoptis epoptis = findEpoptisByMail("testGiannis@aueb.gr",em);
		Assert.assertEquals(epoptis.getEmail().getAddress(),"testGiannis@aueb.gr");
		
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(epoptis.getMiDiathesimotita().isEmpty(),false);
		em.close();

	}
	
	@Test
	public void testAddMiDiathesimotitaWrong() {
		
		MiDiathesimotitaInfo mdi = new MiDiathesimotitaInfo(8,2,2020);
		
		Response response = target(EPOPTES_ADD_MD).queryParam("email","testGiannis@aueb.gr").queryParam("password", "5678").request().put(Entity.entity(mdi,MediaType.APPLICATION_JSON));
		
		Assert.assertEquals(401, response.getStatus());

	}

	
}
