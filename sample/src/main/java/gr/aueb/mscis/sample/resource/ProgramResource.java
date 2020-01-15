package gr.aueb.mscis.sample.resource;

import static gr.aueb.mscis.sample.resource.GrammateiaUri.PROGRAM;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.net.URI;
import gr.aueb.mscis.sample.resource.AbstractResource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import gr.aueb.mscis.sample.model.Epopteia;
import gr.aueb.mscis.sample.model.Epoptis;
import gr.aueb.mscis.sample.model.Program;
import gr.aueb.mscis.sample.service.EpopteiaService;
import gr.aueb.mscis.sample.service.EpoptisService;
import gr.aueb.mscis.sample.service.ProgramService;

import javax.persistence.EntityManager;




@Path(PROGRAM)
public class ProgramResource extends AbstractResource {

	
	@Context 
	UriInfo uriInfo;

	//lista me ola ta programata
	@GET 
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProgramInfo> listAllPrograms(@QueryParam("username") String user,@QueryParam("password") String pass) {
		EntityManager em = getEntityManager();
		EpoptisService es = new EpoptisService(em);
		boolean access = es.logIn(user, pass);
		if(access) {
			ProgramService programService = new ProgramService(em);
			List<Program> programs = programService.findAllPrograms();
	
			List<ProgramInfo> programInfo = ProgramInfo.wrap(programs);
			em.close();
			return programInfo;
		}
		em.close();
		List<ProgramInfo> programInfo = new ArrayList<ProgramInfo>();
		return programInfo;

	}
	
	//leptomeries gia ena programa me sugkekrimeno id
	@GET
	@Path("{programId:[0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public ProgramInfo getProgramDetails(@PathParam("programId") int programId,@QueryParam("username") String user,@QueryParam("password") String pass) {

		EntityManager em = getEntityManager();
		EpoptisService es = new EpoptisService(em);
		boolean access = es.logIn(user, pass);
		if(access) {
			ProgramService programService = new ProgramService(em);
			Program program = programService.findProgramById(programId);
	
			ProgramInfo programInfo = ProgramInfo.wrap(program);
			em.close();
	
			return programInfo;
		}
		em.close();

		return new ProgramInfo();
	} 
	
	//paragogh anaforas epoptwn me tis epopties tou ka8enos
	@GET
	@Path("{programId:[0-9]*}/anaforaEpoptwn")
	@Produces(MediaType.APPLICATION_JSON)
	public List<AnaforaEpoptwnInfo> getAnaforaEpoptwn(@PathParam("programId") int programId,@QueryParam("username") String user,@QueryParam("password") String pass) {
		EntityManager em = getEntityManager();
		EpoptisService es = new EpoptisService(em);
		List<AnaforaEpoptwnInfo> stoixeia = new ArrayList();
		boolean access = es.logIn(user, pass);
		if(access) {
			List<Epoptis> epoptes = es.findAllEpoptes();
			for(Epoptis epoptis : epoptes) {
				stoixeia.add(new AnaforaEpoptwnInfo(epoptis,programId));
			}
			em.close();
			return stoixeia;
		}
		
		em.close();
		return stoixeia;
	}
	
	//paragogh anaforas epoptiwn me tous epoptes kathemias
	@GET
	@Path("{programId:[0-9]*}/anaforaEpoptiwn")
	@Produces(MediaType.APPLICATION_JSON)
	public List<AnaforaEpoptiwnInfo> getAnaforaEpoptiwn(@PathParam("programId") int programId,@QueryParam("username") String user,@QueryParam("password") String pass) {
		EntityManager em = getEntityManager();
		EpoptisService es = new EpoptisService(em);
		List<AnaforaEpoptiwnInfo> stoixeia = new ArrayList();
		boolean access = es.logIn(user, pass);
		if(access) {
			EpopteiaService eps = new EpopteiaService(em);
			List<Epopteia> epopteies = eps.findAllEpopteies();
			for(Epopteia epopteia : epopteies) {
				if(epopteia.getProgram().getId()==programId)
					stoixeia.add(new AnaforaEpoptiwnInfo(epopteia));
			}
			em.close();
			return stoixeia;
		}
		em.close();
		return stoixeia;
	} 
	
	//dhmiourgia neou programmatos
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createProgram(ProgramInfo programInfo,@QueryParam("username") String user,@QueryParam("password") String pass) {

		EntityManager em = getEntityManager();
		EpoptisService es = new EpoptisService(em);
		boolean access = es.logIn(user, pass);
		if(access) {
			Program program = programInfo.getProgram(em);
	
			ProgramService programService = new ProgramService(em);
			program = programService.saveProgram(program);
	
			UriBuilder ub = uriInfo.getAbsolutePathBuilder();
			URI newProgramUri = ub.path(Integer.toString(program.getId())).build();
	
			em.close();
	
			return Response.created(newProgramUri).build();
		}
		em.close();
		return Response.status(Status.UNAUTHORIZED).build();
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
 
	//diagrafi enos programmatos me vash to id
	@DELETE
	@Path("{programId:[0-9]*}")
	public Response deleteProgram(@PathParam("programId") int programId,@QueryParam("username") String user,@QueryParam("password") String pass) {

		EntityManager em = getEntityManager();
		EpoptisService es = new EpoptisService(em);
		boolean access = es.logIn(user, pass);
		if(access) {
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
		em.close();
		return Response.status(Status.UNAUTHORIZED).build();
	}
	

 







}
