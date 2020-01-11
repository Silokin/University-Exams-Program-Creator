package gr.aueb.mscis.sample.resource;

import static gr.aueb.mscis.sample.resource.GrammateiaUri.AITHOUSES;
import static gr.aueb.mscis.sample.resource.GrammateiaUri.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import gr.aueb.mscis.sample.service.*;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
import gr.aueb.mscis.sample.util.SimpleCalendar;
import gr.aueb.mscis.sample.model.*;
import javax.persistence.EntityManager;
import javax.xml.bind.annotation.XmlRootElement;


@Path(AITHOUSES)
public class AithousaResource extends AbstractResource {

	@Context
	UriInfo uriInfo;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<AithousaInfo> listAllAithouses() {
		EntityManager em = getEntityManager();
		AithousaService aithousaService = new AithousaService(em);
		List<Aithousa> aithouses = aithousaService.findAllAithouses();

		List<AithousaInfo> aithousaInfo = AithousaInfo.wrap(aithouses);

		em.close();
		return aithousaInfo;

	}

	@GET
	@Path("{aithousaId:[0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public AithousaInfo getAithousaDetails(@PathParam("aithousaId") int aithousaId) {

		EntityManager em = getEntityManager();

		AithousaService aithousaService = new AithousaService(em);
		Aithousa aithousa = aithousaService.findAithousaById(aithousaId);

		AithousaInfo aithousaInfo = AithousaInfo.wrap(aithousa);
		em.close();

		return aithousaInfo;

	}

	@GET
	@Path("search")
	@Produces(MediaType.APPLICATION_JSON)
	public List<AithousaInfo> searchAithousaByName(@QueryParam("name") String name) {

		EntityManager em = getEntityManager();
		AithousaService aithousaService = new AithousaService(em);
		List<Aithousa> aithouses = aithousaService.(name);

		List<AithousaInfo> aithousesInfo = AithousaInfo.wrap(aithouses);

		em.close();
		return aithousesInfo;

	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createAithousa(AithousaInfo aithousaInfo) {

		EntityManager em = getEntityManager();

		Aithousa aithousa = aithousaInfo.getAithousa(em);

		AithousaService aithousaService = new AithousaService(em);
		aithousaService.saveAithousa(aithousa);

		UriBuilder ub = uriInfo.getAbsolutePathBuilder();
		URI newAithousaUri = ub.path(Integer.toString(aithousa.getId())).build();

		em.close();

		return Response.created(newAithousaUri).build();
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
	public Response updateBook(AithousaInfo aithousaInfo) {

		EntityManager em = getEntityManager();

		Aithousa aithousa = aithousaInfo.getAithousa(em);

		AithousaService aithousaService = new AithousaService(em);
		aithousaService.saveAithousa(aithousa);

		em.close();

		return Response.ok().build();
	}

	@DELETE
	@Path("{aithousaId:[0-9]*}")
	public Response deleteBook(@PathParam("aithousaId") int aithousaId) {

		EntityManager em = getEntityManager();
		
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

}




	