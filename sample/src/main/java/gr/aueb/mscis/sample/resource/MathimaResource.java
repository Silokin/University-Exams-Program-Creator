package gr.aueb.mscis.sample.resource;

import static gr.aueb.mscis.sample.resource.GrammateiaUri.MATHIMATA;

import java.net.URI;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;


import gr.aueb.mscis.sample.model.Mathima;

import gr.aueb.mscis.sample.service.MathimaService;

import javax.persistence.EntityManager;


@Path(MATHIMATA)
public class MathimaResource extends AbstractResource {
  
	@Context
	UriInfo uriInfo; 
 
	@GET 
	@Produces(MediaType.APPLICATION_JSON)
	public List<MathimaInfo> listAllMathimata() {
		EntityManager em = getEntityManager();
		MathimaService mathimaService = new MathimaService(em);
		List<Mathima> mathimata = mathimaService.findAllMathimata();

		List<MathimaInfo> mathimaInfo = MathimaInfo.wrap(mathimata);

		em.close();
		return mathimaInfo;

	}
	 
	@GET
	@Path("{mathimaId:[0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public MathimaInfo getMathimaDetails(@PathParam("mathimaId") int mathimaId) {

		EntityManager em = getEntityManager();

		MathimaService mathimaService = new MathimaService(em);
		Mathima mathima = mathimaService.findMathimaById(mathimaId);

		MathimaInfo mathimaInfo = MathimaInfo.wrap(mathima);
		em.close();

		return mathimaInfo;

	} 
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createAithousa(MathimaInfo mathimaInfo) {

		EntityManager em = getEntityManager();

		Mathima mathima = mathimaInfo.getMathima(em);

		MathimaService mathimaService = new MathimaService(em);
		mathima = mathimaService.saveMathima(mathima);

		UriBuilder ub = uriInfo.getAbsolutePathBuilder();
		URI newMathimaUri = ub.path(Integer.toString(mathima.getId())).build();

		em.close();

		return Response.created(newMathimaUri).build();
	}
	
	@PUT
	@Path("{mathimaId:[0-9]*}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateMathima(MathimaInfo mathimaInfo) {

		EntityManager em = getEntityManager();
 
		Mathima mathima = mathimaInfo.getMathima(em);

		MathimaService mathimaService = new MathimaService(em);
		mathima = mathimaService.saveMathima(mathima);

		em.close();

		return Response.ok().build();
	}
	 
	@DELETE
	@Path("{mathimaId:[0-9]*}")
	public Response deleteMathima(@PathParam("mathimaId") int mathimaId) {

		EntityManager em = getEntityManager();
		
		MathimaService mathimaService = new MathimaService(em);
		
		Mathima mathima = mathimaService.findMathimaById(mathimaId);

		boolean result = mathimaService.deleteMathima(mathima);
		
		if (!result) {
			em.close();
			return Response.status(Status.NOT_FOUND).build();
		}

		em.close();
		return Response.ok().build();

	}
}
