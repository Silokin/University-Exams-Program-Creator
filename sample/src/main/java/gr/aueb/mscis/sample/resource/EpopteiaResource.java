package gr.aueb.mscis.sample.resource;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import gr.aueb.mscis.sample.model.Epopteia;
import gr.aueb.mscis.sample.service.EpopteiaService;
import static gr.aueb.mscis.sample.resource.GrammateiaUri.EPOPTEIES;

import java.net.URI;

@Path(EPOPTEIES)
public class EpopteiaResource extends AbstractResource{

	@Context
	UriInfo uriInfo;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createEpoptis(EpopteiaInfo epopteiaInfo) {

		EntityManager em = getEntityManager();

		Epopteia epopteia = epopteiaInfo.getEpopteia(em);

		EpopteiaService epopteiaService = new EpopteiaService(em);
		epopteia = epopteiaService.saveEpopteia(epopteia);

		UriBuilder ub = uriInfo.getAbsolutePathBuilder();
		URI newEpopteiaUri = ub.path(Integer.toString(epopteia.getId())).build();

		em.close();

		return Response.created(newEpopteiaUri).build();
	}
	
}
