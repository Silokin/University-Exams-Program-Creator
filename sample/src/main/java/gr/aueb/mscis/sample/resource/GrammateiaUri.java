package gr.aueb.mscis.sample.resource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import gr.aueb.mscis.sample.model.Aithousa;
import gr.aueb.mscis.sample.util.SimpleCalendar;
import gr.aueb.mscis.sample.model.*;
import javax.persistence.EntityManager;
import javax.xml.bind.annotation.XmlRootElement;

public class GrammateiaUri {

	/**
	 * /Aithouses
	 */
	public static final String AITHOUSES = "aithouses";

	public static final String AITHOUSES_SEARCH = "aithouses/search";
	/**
	 * /Epopteies
	 */
	public static final String EPOPTEIES = "epopteies";

	public static final String EPOPTEIS_SEARCH = "epopteies/search";
	/**
	 * /Epoptes
	 */
	public static final String EPOPTES = "epoptes";

	public static final String EPOPTES_SEARCH = "Epoptes/search";

	/**
	 * /aithousa/{id}, <br>
	 * e.g. /aithousa/1
	 */
	public static String aithousaIdUri(String id) {
		return AITHOUSES + "/" + id;
	}

	/**
	 * /?name={name}, <br>
	 * e.g. /aithousa?name=UML
	 */
	public static String aithousaSearchUri(String name) {
		return AITHOUSES_SEARCH + "?name=" + name;
	}
	
	public static String epopteiesUri(String EpopteiaId){
		return EPOPTEIES + "/" + epopteiaId;
	}

}



	