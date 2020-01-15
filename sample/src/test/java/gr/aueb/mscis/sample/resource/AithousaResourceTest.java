package gr.aueb.mscis.sample.resource;


import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;


import static gr.aueb.mscis.sample.resource.GrammateiaUri.AITHOUSES;
import static gr.aueb.mscis.sample.resource.GrammateiaUri.AITHOUSES_SEARCH;
import static gr.aueb.mscis.sample.resource.GrammateiaUri.aithousaIdUri;
import static org.junit.Assert.assertNotNull;


import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Assert;
import org.junit.Test;


import gr.aueb.mscis.sample.model.*;



public class AithousaResourceTest extends GrammateiaResourceTest {
	
	@Override
	protected Application configure() {
		/*
		 * 
		 */
		return new ResourceConfig(AithousaResource.class, DebugExceptionMapper.class);
	}

	
	
	
	@Test
	public void testListAithousaById() { 

		// get all aithouses
		List<AithousaInfo> aithouses = target(AITHOUSES).queryParam("username", "admin").queryParam("password", "qwerty").request().get(new GenericType<List<AithousaInfo>>() {
		});
		List<AithousaInfo> aithouses2 = target(AITHOUSES).queryParam("username", "bad").queryParam("password", "guy").request().get(new GenericType<List<AithousaInfo>>() {
		});

		String firstAithousaById = Integer.toString(aithouses.get(0).getId());
		AithousaInfo aithousa = target(aithousaIdUri(firstAithousaById)).queryParam("username", "admin").queryParam("password", "qwerty").request().get(AithousaInfo.class);
		AithousaInfo aithousa2 = target(aithousaIdUri(firstAithousaById)).queryParam("username", "bad").queryParam("password", "guy").request().get(AithousaInfo.class);
		Assert.assertNotNull(aithousa);
		Assert.assertEquals("Πλειάδες", aithousa.getName());
		Assert.assertNull(aithousa2.getId());
		Assert.assertTrue(aithouses2.isEmpty());	
	}


	@Test
	public void testListAllAithouses() {

		List<AithousaInfo> aithouses = target(AITHOUSES).queryParam("username", "admin").queryParam("password", "qwerty").request().get(new GenericType<List<AithousaInfo>>() {
		});
		List<AithousaInfo> aithouses2 = target(AITHOUSES).queryParam("username", "bad").queryParam("password", "guy").request().get(new GenericType<List<AithousaInfo>>() {
		});
		Assert.assertEquals(3, aithouses.size());
		Assert.assertTrue(aithouses2.isEmpty());
	}

	@Test
	public void testUpdateAithousa() {

		// Find an aithousa and update its name
		List<Aithousa> aithouses = findAithousaByName("Πλειάδες");
		Assert.assertEquals(1, aithouses.size());
		AithousaInfo aithousaInfo = AithousaInfo.wrap(aithouses.get(0));
		aithousaInfo.setName("Πλειάδες2");

		// Submit the updated representation
		Response response = target(aithousaIdUri(Integer.toString(aithousaInfo.getId()))).queryParam("username", "admin").queryParam("password", "qwerty").request()
				.put(Entity.entity(aithousaInfo, MediaType.APPLICATION_JSON));
		Response response2 = target(aithousaIdUri(Integer.toString(aithousaInfo.getId()))).queryParam("username", "bad").queryParam("password", "guy").request()
				.put(Entity.entity(aithousaInfo, MediaType.APPLICATION_JSON));

		// assertion on request status and database state
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(401, response2.getStatus());
		List<Aithousa> foundAithouses = findAithousaByName("Πλειάδες2");
		Assert.assertEquals(1, foundAithouses.size());

	}

	@Test
	public void testDeleteExistingAithousa() {
		// Find an aithousa and update its title
		List<Aithousa> aithouses = findAithousaByName("Πλειάδες");
		Assert.assertEquals(1, aithouses.size());
		Aithousa aithousa = aithouses.get(0);

		// Submit the updated representation
		Response response = target(aithousaIdUri(Integer.toString(aithousa.getId()))).queryParam("username", "admin").queryParam("password", "qwerty").request().delete();
		Response response2 = target(aithousaIdUri(Integer.toString(aithousa.getId()))).queryParam("username", "bad").queryParam("password", "guy").request().delete();

		// assertion on request status and database state
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(401, response2.getStatus());
		List<Aithousa> foundAithouses = findAithousaByName("Πλειάδες");
		Assert.assertEquals(0, foundAithouses.size());

	}
	
	@Test
	public void testDeleteNonExistingAithousa() {

		Response response = target(aithousaIdUri(Integer.toString(Integer.MAX_VALUE))).queryParam("username", "admin").queryParam("password", "qwerty").request().delete();
		Response response2 = target(aithousaIdUri(Integer.toString(Integer.MAX_VALUE))).queryParam("username", "bad").queryParam("password", "guy").request().delete();

		Assert.assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
		Assert.assertEquals(401, response2.getStatus());

	}
 
	@Test
	public void testSearchAithousaByName() {
		System.out.println(GrammateiaUri.aithousaSearchUri("Πλειάδες"));
		List<AithousaInfo> aithouses = target(AITHOUSES_SEARCH).queryParam("name", "Πλειάδες").queryParam("username", "admin").queryParam("password", "qwerty").request()
				.get(new GenericType<List<AithousaInfo>>() {
				});
		List<AithousaInfo> aithouses2 = target(AITHOUSES_SEARCH).queryParam("name", "Πλειάδες").queryParam("username", "bad").queryParam("password", "guy").request()
				.get(new GenericType<List<AithousaInfo>>() {
				});

		Assert.assertEquals(1, aithouses.size());
		Assert.assertTrue(aithouses2.isEmpty());
	}

	@Test
	public void testCreateNewAithousa() {

		

		// Create an aithousa info object and submit
		AithousaInfo aithousaInfo = new AithousaInfo("mes","des",1,2,"fes");

		Response response = target(AITHOUSES).queryParam("username", "admin").queryParam("password", "qwerty").request().post(Entity.entity(aithousaInfo, MediaType.APPLICATION_JSON));
		Response response2 = target(AITHOUSES).queryParam("username", "bad").queryParam("password", "guy").request().post(Entity.entity(aithousaInfo, MediaType.APPLICATION_JSON));

		// Check status and database state
		Assert.assertEquals(201, response.getStatus());
		Assert.assertEquals(401, response2.getStatus());
		List<Aithousa> foundAithouses = findAithousaByName("mes");
		Assert.assertEquals(1, foundAithouses.size());

	}
	
	@Test
	public void testListAithousaById2() { 

		// get all aithouses
		List<AithousaInfo> aithouses = target(AITHOUSES).queryParam("username", "admin").queryParam("password", "qwerty").request().get(new GenericType<List<AithousaInfo>>() {
		});
		List<AithousaInfo> aithouses2 = target(AITHOUSES).queryParam("username", "bad").queryParam("password", "guy").request().get(new GenericType<List<AithousaInfo>>() {
		});
		AithousaInfo firstAithousa = aithouses.get(0);
		assertNotNull(firstAithousa);
		Assert.assertTrue(aithouses2.isEmpty());
	}
}
