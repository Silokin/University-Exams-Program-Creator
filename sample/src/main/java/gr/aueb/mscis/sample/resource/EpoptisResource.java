package gr.aueb.mscis.sample.resource;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import gr.aueb.mscis.sample.contacts.EmailAddress;
import gr.aueb.mscis.sample.model.Epoptis;
import gr.aueb.mscis.sample.model.MiDiathesimotita;
import gr.aueb.mscis.sample.service.EpoptisService;
import gr.aueb.mscis.sample.util.SimpleCalendar;

import javax.ws.rs.core.Response.Status;

import static gr.aueb.mscis.sample.resource.GrammateiaUri.EPOPTES;

import java.net.URI;

@Path(EPOPTES)
public class EpoptisResource extends AbstractResource {
	
	@Context
	UriInfo uriInfo;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createEpoptis(EpoptisInfo epoptisInfo,@QueryParam("username") String user,@QueryParam("password") String pass) {

		EntityManager em = getEntityManager();
		EpoptisService es = new EpoptisService(em);
		boolean access = es.logIn(user, pass);
		if(access) {
		Epoptis epoptis = epoptisInfo.getEpoptis(em);

		epoptis = es.saveEpoptis(epoptis);

		UriBuilder ub = uriInfo.getAbsolutePathBuilder();
		URI newEpoptisUri = ub.path(Integer.toString(epoptis.getId())).build();

		em.close();

		return Response.created(newEpoptisUri).build();
		}
		em.close();
		return Response.status(Status.UNAUTHORIZED).build();
	}

	/**
	 * Update a specific epoptis
	 * 
	 * @param epoptisInfo
	 *            A full representation of epoptis, including its id should be
	 *            submitted
	 * @return
	 */
	@PUT
	@Path("{epoptisId:[0-9]*}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateBook(EpoptisInfo epoptisInfo,@QueryParam("username") String user,@QueryParam("password") String pass) {

		EntityManager em = getEntityManager();
		EpoptisService es = new EpoptisService(em);
		boolean access = es.logIn(user, pass);
		if(access) {
		Epoptis epoptis = epoptisInfo.getEpoptis(em);

		epoptis = es.saveEpoptis(epoptis);

		em.close();

		return Response.ok().build();
		}
		em.close();
		return Response.status(Status.UNAUTHORIZED).build();
	}

	@DELETE
	@Path("{epoptisId:[0-9]*}")
	public Response deleteEpoptis(@PathParam("epoptisId") int epoptisId,@QueryParam("username") String user,@QueryParam("password") String pass) {

		EntityManager em = getEntityManager();
		EpoptisService es = new EpoptisService(em);
		boolean access = es.logIn(user, pass);
		if(access) {
		Epoptis epoptis = es.findEpoptisById(epoptisId);
		boolean result = es.deleteEpoptis(epoptis);
		
		if (!result) {
			em.close();
			return Response.status(Status.NOT_FOUND).build();
		}

		em.close();
		return Response.ok().build();
		}
		em.close();
		return Response.status(Status.UNAUTHORIZED).build();

	}
	
	@PUT
	@Path("addDate")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addMiDiathesimotita(MiDiathesimotitaInfo mdi,@QueryParam("email") String mail,@QueryParam("password") String pass) {

		EntityManager em = getEntityManager();
		EpoptisService epoptisService = new EpoptisService(em);
		if(epoptisService.logIn(mail, pass)) {
		
			Epoptis epoptis = epoptisService.findEpoptisByMail(new EmailAddress(mail));
			MiDiathesimotita md = new MiDiathesimotita(new SimpleCalendar(mdi.getYear(),mdi.getMonth(),mdi.getDay(),0,0));
			epoptis.addMiDiathesimotita(md);

			epoptis = epoptisService.saveEpoptis(epoptis);
			
			em.close();

			return Response.ok().build();
		}
	
				
		em.close();

		return Response.status(Status.UNAUTHORIZED).build();
	}

}
