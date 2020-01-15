package gr.aueb.mscis.sample.resource;

import static gr.aueb.mscis.sample.resource.GrammateiaUri.AITHOUSES;


import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import gr.aueb.mscis.sample.model.Aithousa;
import gr.aueb.mscis.sample.service.AithousaService;
import gr.aueb.mscis.sample.service.EpoptisService;

import javax.persistence.EntityManager;



@Path(AITHOUSES)
public class AithousaResource extends AbstractResource { 

	@Context
	UriInfo uriInfo; 

	@GET 
	@Produces(MediaType.APPLICATION_JSON)
	public List<AithousaInfo> listAllAithouses(@QueryParam("username") String user,@QueryParam("password") String pass) {
		EntityManager em = getEntityManager();
		EpoptisService es = new EpoptisService(em);
		List<AithousaInfo> aithousaInfo = new ArrayList();
		boolean access = es.logIn(user, pass);
		if(access) {
			AithousaService aithousaService = new AithousaService(em);
			List<Aithousa> aithouses = aithousaService.findAllAithouses();
	
			aithousaInfo = AithousaInfo.wrap(aithouses);
	
			em.close();
			return aithousaInfo;
		}
		em.close();
		return aithousaInfo;

	}

	@GET
	@Path("{aithousaId:[0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public AithousaInfo getAithousaDetails(@PathParam("aithousaId") int aithousaId,@QueryParam("username") String user,@QueryParam("password") String pass) {

		EntityManager em = getEntityManager();
		EpoptisService es = new EpoptisService(em);
		boolean access = es.logIn(user, pass);
		if(access) {
			AithousaService aithousaService = new AithousaService(em);
			Aithousa aithousa = aithousaService.findAithousaById(aithousaId);
	
			AithousaInfo aithousaInfo = AithousaInfo.wrap(aithousa);
			em.close();
	
			return aithousaInfo;
		}
		em.close();
		return new AithousaInfo();
	}
 
	@GET
	@Path("search")
	@Produces(MediaType.APPLICATION_JSON)
	public List<AithousaInfo> searchAithousaByName(@QueryParam("name") String name,@QueryParam("username") String user,@QueryParam("password") String pass) {

		EntityManager em = getEntityManager();
		EpoptisService es = new EpoptisService(em);
		List<AithousaInfo> aithousesInfo = new ArrayList();
		boolean access = es.logIn(user, pass);
		if(access) {
			AithousaService aithousaService = new AithousaService(em);
			List<Aithousa> aithouses = aithousaService.findAithousaByName(name);
	
			aithousesInfo = AithousaInfo.wrap(aithouses);
	
			em.close();
			return aithousesInfo;
		}
		em.close();
		return aithousesInfo;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createAithousa(AithousaInfo aithousaInfo,@QueryParam("username") String user,@QueryParam("password") String pass) {

		EntityManager em = getEntityManager();
		EpoptisService es = new EpoptisService(em);

		boolean access = es.logIn(user, pass);
		if(access) {
			Aithousa aithousa = aithousaInfo.getAithousa(em);
	
			AithousaService aithousaService = new AithousaService(em);
			aithousa = aithousaService.saveAithousa(aithousa);
	
			UriBuilder ub = uriInfo.getAbsolutePathBuilder();
			URI newAithousaUri = ub.path(Integer.toString(aithousa.getId())).build();
	
			em.close();
	
			return Response.created(newAithousaUri).build();
		}
		em.close();
		return Response.status(Status.UNAUTHORIZED).build();
	}

	/**
	 * Update a specific aithousa
	 * 
	 * @param aithousaInfo
	 *            A full representation of the book, including its id should be
	 *            submitted
	 * @return
	 */
	@PUT
	@Path("{aithousaId:[0-9]*}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateAithousa(AithousaInfo aithousaInfo,@QueryParam("username") String user,@QueryParam("password") String pass) {

		EntityManager em = getEntityManager();
		EpoptisService es = new EpoptisService(em);

		boolean access = es.logIn(user, pass);
		if(access) {
			Aithousa aithousa = aithousaInfo.getAithousa(em);
	
			AithousaService aithousaService = new AithousaService(em);
			aithousa = aithousaService.saveAithousa(aithousa);
	
			em.close();
	
			return Response.ok().build();
		}
		em.close();
		return Response.status(Status.UNAUTHORIZED).build(); 
	}

	@DELETE
	@Path("{aithousaId:[0-9]*}")
	public Response deleteAithousa(@PathParam("aithousaId") int aithousaId,@QueryParam("username") String user,@QueryParam("password") String pass) {

		EntityManager em = getEntityManager();
		EpoptisService es = new EpoptisService(em);

		boolean access = es.logIn(user, pass);
		if(access) {
			AithousaService aithousaService = new AithousaService(em);
			
			Aithousa aithousa = aithousaService.findAithousaById(aithousaId);
	
			boolean result = aithousaService.deleteAithousa(aithousa);
			
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

}	