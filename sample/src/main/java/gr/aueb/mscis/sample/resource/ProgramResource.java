package gr.aueb.mscis.sample.resource;

import static gr.aueb.mscis.sample.resource.GrammateiaUri.AITHOUSES;
import static gr.aueb.mscis.sample.resource.GrammateiaUri.PROGRAM;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import gr.aueb.mscis.sample.persistence.JPAUtil;
import java.net.URI;
import gr.aueb.mscis.sample.resource.AbstractResource;
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
import gr.aueb.mscis.sample.model.Program;
import gr.aueb.mscis.sample.service.AithousaService;
import gr.aueb.mscis.sample.service.ProgramService;

import javax.persistence.EntityManager;




@Path(PROGRAM)
public class ProgramResource extends AbstractResource {

	
	@Context 
	UriInfo uriInfo;

	@GET 
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProgramInfo> listAllPrograms() {
		EntityManager em = getEntityManager();
		ProgramService programService = new ProgramService(em);
		List<Program> programs = programService.findAllPrograms();

		List<ProgramInfo> programInfo = ProgramInfo.wrap(programs);

		em.close();
		return programInfo;

	}
	
	@GET
	@Path("{programId:[0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public ProgramInfo getProgramDetails(@PathParam("programId") int programId) {

		EntityManager em = getEntityManager();

		ProgramService programService = new ProgramService(em);
		Program program = programService.findProgramById(programId);

		ProgramInfo programInfo = ProgramInfo.wrap(program);
		em.close();

		return programInfo;

	} 
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createProgram(ProgramInfo programInfo) {

		EntityManager em = getEntityManager();

		Program program = programInfo.getProgram(em);

		ProgramService programService = new ProgramService(em);
		program = programService.saveProgram(program);

		UriBuilder ub = uriInfo.getAbsolutePathBuilder();
		URI newProgramUri = ub.path(Integer.toString(program.getId())).build();

		em.close();

		return Response.created(newProgramUri).build();
	}
	
//	@PUT
//	@Path("{ProgramId:[0-9]*}")
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Response updateProgram(ProgramInfo programInfo) {
//
//		EntityManager em = getEntityManager();
// 
//		Program program = programInfo.getProgram(em);
//
//		ProgramService programService = new ProgramService(em);
//		program = programService.saveProgram(program);
//
//		em.close();
//
//		return Response.ok().build();
//	}
 
	@DELETE
	@Path("{programId:[0-9]*}")
	public Response deleteProgram(@PathParam("programId") int programId) {

		EntityManager em = getEntityManager();
		
		ProgramService programService = new ProgramService(em);
		
		Program program = programService.findProgramById(programId);

		boolean result = programService.deleteProgram(program);
		
		if (!result) {
			em.close();
			return Response.status(Status.NOT_FOUND).build();
		}

		em.close();
		return Response.ok().build();

	}
	

 







}
