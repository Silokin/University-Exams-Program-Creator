package gr.aueb.mscis.sample.resource;

import static gr.aueb.mscis.sample.resource.GrammateiaUri.EPOPTEIES;
import static gr.aueb.mscis.sample.resource.GrammateiaUri.epopteiesAddClassUri;
import static gr.aueb.mscis.sample.resource.GrammateiaUri.epopteiesUri;
import static gr.aueb.mscis.sample.resource.GrammateiaUri.epopteiesAnathesiUri;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Assert;
import org.junit.Test;

import gr.aueb.mscis.sample.model.Aithousa;
import gr.aueb.mscis.sample.model.Epopteia;
import gr.aueb.mscis.sample.model.Epoptis;
import gr.aueb.mscis.sample.model.Mathima;
import gr.aueb.mscis.sample.model.Program;
import gr.aueb.mscis.sample.persistence.JPAUtil;

public class EpopteiaResourceTest extends GrammateiaResourceTest{
	
	@Override
	protected Application configure() {
		/*
		 * 
		 */
		return new ResourceConfig(EpopteiaResource.class, DebugExceptionMapper.class);
	}
	
	@Test
	public void testListAllEpopteies() {

		List<EpopteiaInfo> epopteies = target(EPOPTEIES).request().get(new GenericType<List<EpopteiaInfo>>() {
		});
		String firstEpopteiaById = Integer.toString(epopteies.get(0).getId());
		Assert.assertEquals(3, epopteies.size());
	}
	
	@Test
	public void testListEpopteiaById() { 

		
		List<EpopteiaInfo> epopteies = target(EPOPTEIES).request().get(new GenericType<List<EpopteiaInfo>>() {
		});

		String firstEpopteiaById = Integer.toString(epopteies.get(0).getId());
		EpopteiaInfo epopteia = target(epopteiesUri(firstEpopteiaById)).request().get(EpopteiaInfo.class);
		Assert.assertNotNull(epopteia);
		Assert.assertEquals(21 , epopteia.getStartD());
	}
	
	@Test
	public void testCreateNewEpopteia() {
		EntityManager em = JPAUtil.getCurrentEntityManager();
		// Create an epoptis info object and submit
		Mathima mathima = findMathimaByName("Επιστήμη των Υπολογιστών",em); 
		List<Program> programs = listPrograms(em);
		EpopteiaInfo epopteiaInfo = new EpopteiaInfo(mathima.getId(),programs.get(0).getId(),22,2,2020,18,30,22,2,2020,20,30);
		em.close();

		Response response = target(EPOPTEIES).queryParam("username", "admin").queryParam("password", "qwerty").request().post(Entity.entity(epopteiaInfo, MediaType.APPLICATION_JSON));
		Response response2 = target(EPOPTEIES).queryParam("username", "bad").queryParam("password", "guy").request().post(Entity.entity(epopteiaInfo, MediaType.APPLICATION_JSON));
		

		// Check status and database state
		Assert.assertEquals(201, response.getStatus());
		Assert.assertEquals(401, response2.getStatus());
		
		EntityManager em2 = JPAUtil.getCurrentEntityManager();
		List<Epopteia> epopteies = listEpopteies(em2);
		Assert.assertEquals(4,epopteies.size());
		em2.close();
	}
	
	@Test
	public void testAddAithousa() {
		
		EntityManager em = JPAUtil.getCurrentEntityManager();
		List<Aithousa> aithousa = findAithousaByName("Πλειάδες",em);
		List<Epopteia> epopteia = listEpopteies(em);
		AithousaInfo aithousaInfo = new AithousaInfo(aithousa.get(0));
		
		Assert.assertEquals("Πλειάδες",aithousaInfo.getName());
		em.close();
		
		Response response = target(epopteiesAddClassUri(Integer.toString(epopteia.get(0).getId()))).queryParam("username", "admin").queryParam("password", "qwerty").request().put(Entity.entity(aithousaInfo, MediaType.APPLICATION_JSON));
		Response response2 = target(epopteiesAddClassUri(Integer.toString(epopteia.get(0).getId()))).queryParam("username", "bad").queryParam("password", "guy").request().put(Entity.entity(aithousaInfo, MediaType.APPLICATION_JSON));
		
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(401, response2.getStatus());
		EntityManager em2 = JPAUtil.getCurrentEntityManager();
		epopteia = listEpopteies(em2);
		Set<Aithousa> found = epopteia.get(0).getAithouses();
		Assert.assertEquals(1,found.size());
		em2.close();
	}
	
	@Test
	public void anathesiEpopteias() {
		EntityManager em = JPAUtil.getCurrentEntityManager();
		Epoptis epoptis = findEpoptisByMail("testGiannis@aueb.gr",em);
		EpoptisInfo epoptisInfo = new EpoptisInfo(epoptis);
		
		List<Epopteia> epopteia = listEpopteies(em);
		em.close();
		
		Response response = target(epopteiesAnathesiUri(Integer.toString(epopteia.get(0).getId()))).queryParam("username", "admin").queryParam("password", "qwerty").request().put(Entity.entity(epoptisInfo, MediaType.APPLICATION_JSON));
		Response response2 = target(epopteiesAnathesiUri(Integer.toString(epopteia.get(0).getId()))).queryParam("username", "bad").queryParam("password", "guy").request().put(Entity.entity(epoptisInfo, MediaType.APPLICATION_JSON));
		
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(401, response2.getStatus());
		
		EntityManager em2 = JPAUtil.getCurrentEntityManager();
		epopteia = listEpopteies(em2);
		Set<Epoptis> found = epopteia.get(0).getEpoptis();
		em2.close();
		Assert.assertEquals(1,found.size());
	}
}
