package gr.aueb.mscis.sample.resource;

import static gr.aueb.mscis.sample.resource.GrammateiaUri.MATHIMATA;

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


import gr.aueb.mscis.sample.model.Mathima;
import gr.aueb.mscis.sample.service.EpoptisService;
import gr.aueb.mscis.sample.service.MathimaService;

import javax.persistence.EntityManager;


@Path(MATHIMATA)
public class MathimaResource extends AbstractResource {
  
	@Context
	UriInfo uriInfo; 
 
	//lista me ola ta mathimata
	@GET 
	@Produces(MediaType.APPLICATION_JSON)
	public List<MathimaInfo> listAllMathimata(@QueryParam("username") String user,@QueryParam("password") String pass) {
		EntityManager em = getEntityManager();
		EpoptisService es = new EpoptisService(em);
		List<MathimaInfo> mathimaInfo;
		boolean access = es.logIn(user, pass);
		if(access) {
			MathimaService mathimaService = new MathimaService(em);
			List<Mathima> mathimata = mathimaService.findAllMathimata();
	
			 mathimaInfo = MathimaInfo.wrap(mathimata);
	
			em.close();
			return mathimaInfo;
		}
		em.close();
		return mathimaInfo = new ArrayList();

	}
	
	// lista me plhrofories gia ena mathima
	@GET
	@Path("{mathimaId:[0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public MathimaInfo getMathimaDetails(@PathParam("mathimaId") int mathimaId,@QueryParam("username") String user,@QueryParam("password") String pass) {

		EntityManager em = getEntityManager();
		EpoptisService es = new EpoptisService(em);
		boolean access = es.logIn(user, pass);
		
		if(access) {
		MathimaService mathimaService = new MathimaService(em);
		Mathima mathima = mathimaService.findMathimaById(mathimaId);

		MathimaInfo mathimaInfo = MathimaInfo.wrap(mathima);
		em.close();

		return mathimaInfo;
		}
		em.close();
		return new MathimaInfo();

	} 
	
	//dhmiourghse neo mathima
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createMathima(MathimaInfo mathimaInfo,@QueryParam("username") String user,@QueryParam("password") String pass) {

		EntityManager em = getEntityManager();
		EpoptisService es = new EpoptisService(em);
		boolean access = es.logIn(user, pass);
		
		if(access) {
		Mathima mathima = mathimaInfo.getMathima(em);

		MathimaService mathimaService = new MathimaService(em);
		mathima = mathimaService.saveMathima(mathima);

		UriBuilder ub = uriInfo.getAbsolutePathBuilder();
		URI newMathimaUri = ub.path(Integer.toString(mathima.getId())).build();

		em.close();

		return Response.created(newMathimaUri).build();
		}
		em.close();
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
	//ananewse ena mathima 
	@PUT
	@Path("{mathimaId:[0-9]*}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateMathima(MathimaInfo mathimaInfo,@QueryParam("username") String user,@QueryParam("password") String pass) {

		EntityManager em = getEntityManager();
		EpoptisService es = new EpoptisService(em);
		boolean access = es.logIn(user, pass);
		
		if(access) {
		Mathima mathima = mathimaInfo.getMathima(em);

		MathimaService mathimaService = new MathimaService(em);
		mathima = mathimaService.saveMathima(mathima);

		em.close();

		return Response.ok().build();
		}
		em.close();
		return Response.status(Status.UNAUTHORIZED).build();
	}
	 
	//diegrapse ena mathima
	@DELETE
	@Path("{mathimaId:[0-9]*}")
	public Response deleteMathima(@PathParam("mathimaId") int mathimaId,@QueryParam("username") String user,@QueryParam("password") String pass) {

		EntityManager em = getEntityManager();
		EpoptisService es = new EpoptisService(em);
		boolean access = es.logIn(user, pass);
		
		if(access) {
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
		em.close();
		return Response.status(Status.UNAUTHORIZED).build();
	}
}
