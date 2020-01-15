package gr.aueb.mscis.sample.resource;

import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;


import static gr.aueb.mscis.sample.resource.GrammateiaUri.MATHIMATA;

import static gr.aueb.mscis.sample.resource.GrammateiaUri.mathimaIdUri;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Assert;
import org.junit.Test;



import gr.aueb.mscis.sample.model.*;
import gr.aueb.mscis.sample.persistence.JPAUtil;
import gr.aueb.mscis.sample.service.MathimaService;



public class MathimaResourceTest extends GrammateiaResourceTest {

	@Override
	protected Application configure() {
		/*
		 *  
		 */
		return new ResourceConfig(MathimaResource.class, DebugExceptionMapper.class);
	}
	 
	@Test
	public void testListMathimaById() { 

		// get all Mathimata
		List<MathimaInfo> mathimata = target(MATHIMATA).queryParam("username", "admin").queryParam("password", "qwerty").request().get(new GenericType<List<MathimaInfo>>() {
		});
		List<MathimaInfo> mathimata2 = target(MATHIMATA).queryParam("username", "bad").queryParam("password", "guy").request().get(new GenericType<List<MathimaInfo>>() {
		});
  
		String firstMathimaById = Integer.toString(mathimata.get(0).getId());
		MathimaInfo mathima = target(mathimaIdUri(firstMathimaById)).queryParam("username", "admin").queryParam("password", "qwerty").request().get(MathimaInfo.class);
		MathimaInfo mathima2 = target(mathimaIdUri(firstMathimaById)).queryParam("username", "bad").queryParam("password", "guy").request().get(MathimaInfo.class);
		Assert.assertNotNull(mathima);
		Assert.assertNull(mathima2.getId());
		Assert.assertTrue(mathimata2.isEmpty());	
	}
	
	@Test
	public void testListAllMathimata() {

		List<MathimaInfo> mathimata = target(MATHIMATA).queryParam("username", "admin").queryParam("password", "qwerty").request().get(new GenericType<List<MathimaInfo>>() {
		});
		List<MathimaInfo> mathimata2 = target(MATHIMATA).queryParam("username", "bad").queryParam("password", "guy").request().get(new GenericType<List<MathimaInfo>>() {
		});
		Assert.assertEquals(4, mathimata.size());
		Assert.assertEquals(0, mathimata2.size());
	}

	@Test 
	public void testUpdateMathima() {

		
		List<Mathima> mathimata = findMathimaByTitle("Επιστήμη των Υπολογιστών");
		Assert.assertEquals(1, mathimata.size());
		MathimaInfo mathimaInfo = MathimaInfo.wrap(mathimata.get(0));
		mathimaInfo.setTitle("Επιστήμη των Υπολογιστών2");

		Response response = target(mathimaIdUri(Integer.toString(mathimaInfo.getId()))).queryParam("username", "admin").queryParam("password", "qwerty").request()
				.put(Entity.entity(mathimaInfo, MediaType.APPLICATION_JSON));
		Response response2 = target(mathimaIdUri(Integer.toString(mathimaInfo.getId()))).queryParam("username", "bad").queryParam("password", "guy").request()
				.put(Entity.entity(mathimaInfo, MediaType.APPLICATION_JSON));

		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(401, response2.getStatus());
		List<Mathima> foundMathimata = findMathimaByTitle("Επιστήμη των Υπολογιστών2");
		Assert.assertEquals(1, foundMathimata.size());

	}
	
	@Test
	public void testDeleteNonExistingMathima() {

		Response response = target(mathimaIdUri(Integer.toString(Integer.MAX_VALUE))).queryParam("username", "admin").queryParam("password", "qwerty").request().delete();
		Response response2 = target(mathimaIdUri(Integer.toString(Integer.MAX_VALUE))).queryParam("username", "bad").queryParam("password", "guy").request().delete();

		Assert.assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
		Assert.assertEquals(401, response2.getStatus());

	}

	
	@Test
	public void testCreateNewMathima() {

		
		MathimaInfo mathimaInfo = new MathimaInfo("vash",1,"kot");

		Response response = target(MATHIMATA).queryParam("username", "admin").queryParam("password", "qwerty").request().post(Entity.entity(mathimaInfo, MediaType.APPLICATION_JSON));
		Response response2 = target(MATHIMATA).queryParam("username", "bad").queryParam("password", "guy").request().post(Entity.entity(mathimaInfo, MediaType.APPLICATION_JSON));

		
		Assert.assertEquals(201, response.getStatus());
		Assert.assertEquals(401, response2.getStatus());
		List<Mathima> foundMathimata = findMathimaByTitle("vash");
		Assert.assertEquals(1, foundMathimata.size());

	}
	
	
	public List<Mathima> findMathimaByTitle(String title) {
		EntityManager em = JPAUtil.getCurrentEntityManager();
		
		MathimaService service = new MathimaService(em);
		List<Mathima> mathimata = service.findMathimaByTitle(title);
		
		em.close();
		return mathimata;
	}
} 
