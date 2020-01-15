package gr.aueb.mscis.sample.resource;

import static gr.aueb.mscis.sample.resource.GrammateiaUri.EPOPTEIES;
import static gr.aueb.mscis.sample.resource.GrammateiaUri.epopteiesAddClassUri;
import static gr.aueb.mscis.sample.resource.GrammateiaUri.epopteiesUri;
import static gr.aueb.mscis.sample.resource.GrammateiaUri.epopteiesAnathesiUri;

import java.util.List;
import java.util.Set;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
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
		EpopteiaInfo epopteiaInfo = new EpopteiaInfo(mathima.getId(),programs.get(0).getId(),22,2,2020,18,30,22,2,2020,20,30);

		Response response = target(EPOPTEIES).queryParam("username", "admin").queryParam("password", "qwerty").request().post(Entity.entity(epopteiaInfo, MediaType.APPLICATION_JSON));
		Response response2 = target(EPOPTEIES).queryParam("username", "bad").queryParam("password", "guy").request().post(Entity.entity(epopteiaInfo, MediaType.APPLICATION_JSON));
		

		// Check status and database state
		Assert.assertEquals(201, response.getStatus());
		Assert.assertEquals(401, response2.getStatus());
		List<Epopteia> epopteies = listEpopteies();
		Assert.assertEquals(4,epopteies.size());
	}
	
	@Test
	public void testAddAithousa() {
		
		List<Aithousa> aithousa = findAithousaByName("Πλειάδες");
		List<Epopteia> epopteia = listEpopteies();
		AithousaInfo aithousaInfo = new AithousaInfo(aithousa.get(0));
		
		Assert.assertEquals("Πλειάδες",aithousaInfo.getName());
		
		Response response = target(epopteiesAddClassUri(Integer.toString(epopteia.get(0).getId()))).queryParam("username", "admin").queryParam("password", "qwerty").request().put(Entity.entity(aithousaInfo, MediaType.APPLICATION_JSON));
		Response response2 = target(epopteiesAddClassUri(Integer.toString(epopteia.get(0).getId()))).queryParam("username", "bad").queryParam("password", "guy").request().put(Entity.entity(aithousaInfo, MediaType.APPLICATION_JSON));
		
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(401, response2.getStatus());
		
		Set<Aithousa> found = epopteia.get(0).getAithouses();
		Assert.assertEquals(1,found.size());
	}
	
	@Test
	public void anathesiEpopteias() {
		
		Epoptis epoptis = findEpoptisByMail("testGiannis@aueb.gr");
		EpoptisInfo epoptisInfo = new EpoptisInfo(epoptis);
		
		List<Epopteia> epopteia = listEpopteies();
		
		Response response = target(epopteiesAnathesiUri(Integer.toString(epopteia.get(0).getId()))).queryParam("username", "admin").queryParam("password", "qwerty").request().put(Entity.entity(epoptisInfo, MediaType.APPLICATION_JSON));
		Response response2 = target(epopteiesAnathesiUri(Integer.toString(epopteia.get(0).getId()))).queryParam("username", "bad").queryParam("password", "guy").request().put(Entity.entity(epoptisInfo, MediaType.APPLICATION_JSON));
		
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(401, response2.getStatus());
		epopteia = listEpopteies();
		Set<Epoptis> found = epopteia.get(0).getEpoptis();
		Assert.assertEquals(1,found.size());
	}
}
