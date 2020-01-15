package gr.aueb.mscis.sample.resource;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
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
import javax.ws.rs.core.Response.Status;

import gr.aueb.mscis.sample.model.Epopteia;
import gr.aueb.mscis.sample.model.Epoptis;
import gr.aueb.mscis.sample.service.EpopteiaService;
import gr.aueb.mscis.sample.service.EpoptisService;

import static gr.aueb.mscis.sample.resource.GrammateiaUri.EPOPTEIES;

import java.net.URI;

@Path(EPOPTEIES)
public class EpopteiaResource extends AbstractResource{

	@Context
	UriInfo uriInfo;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createEpoptis(EpopteiaInfo epopteiaInfo,@QueryParam("username") String user,@QueryParam("password") String pass) {

		EntityManager em = getEntityManager();
		EpoptisService es = new EpoptisService(em);
		boolean access = es.logIn(user, pass);
		if(access) {
		Epopteia epopteia = epopteiaInfo.getEpopteia(em);

		EpopteiaService epopteiaService = new EpopteiaService(em);
		epopteia = epopteiaService.saveEpopteia(epopteia);

		UriBuilder ub = uriInfo.getAbsolutePathBuilder();
		URI newEpopteiaUri = ub.path(Integer.toString(epopteia.getId())).build();

		em.close();

		return Response.created(newEpopteiaUri).build();
		}
		em.close();
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@PUT
	@Path("{epopteiaId:[0-9]*}/addAithousa")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addAithousa(AithousaInfo aithousaInfo,@PathParam("epopteiaId") int id,@QueryParam("username") String user,@QueryParam("password") String pass) {
		
		EntityManager em = getEntityManager();
		EpoptisService es = new EpoptisService(em);
		boolean access = es.logIn(user, pass);
		if(access) {
		EpopteiaService epopteiaService = new EpopteiaService(em);
		Epopteia epopteia = epopteiaService.findEpopteiaById(id);
		epopteiaService.addAithousa(id, aithousaInfo.getId());
		epopteiaService.saveEpopteia(epopteia);
		
		em.close();
		
		return Response.ok().build();
		}
		em.close();
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
	@PUT
	@Path("{epopteiaId:[0-9]*}/addEpopti")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response anathesiEpoptias(EpoptisInfo epoptisInfo,@PathParam("epopteiaId") int id,@QueryParam("username") String user,@QueryParam("password") String pass) {
		
		EntityManager em = getEntityManager();
		EpoptisService es = new EpoptisService(em);
		boolean access = es.logIn(user, pass);
		if(access) {
		Epoptis epoptis = es.findEpoptisById(epoptisInfo.getId());
		
		EpopteiaService eps = new EpopteiaService(em);
		Epopteia epopteia =eps.findEpopteiaById(id);
		
		eps.anathesiEpopteias(epoptis, epopteia);
		eps.saveEpopteia(epopteia);
		
		em.close();
		
		return Response.ok().build();
		}
		em.close();
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
}
