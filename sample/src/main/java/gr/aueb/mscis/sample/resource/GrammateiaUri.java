package gr.aueb.mscis.sample.resource;


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

	public static final String EPOPTES_SEARCH = "epoptes/search";

	/**
	 * /aithousa/{id}, <br>
	 * e.g. /aithousa/1
	 */
	public static String aithousaIdUri(String id) {
		return AITHOUSES + "/" + id;
	}
	
	/**
	 * /epoptes/{id}, <br>
	 * e.g. /epoptes/1
	 */
	public static String epoptisIdUri(String id) {
		return EPOPTES + "/" + id;
	}
 
	/**
	 * /?name={name}, <br>
	 * e.g. /aithousa?name=UML
	 */
	public static String aithousaSearchUri(String name) {
		return AITHOUSES_SEARCH + "?name=" + name;
	}
	
	public static String epopteiesUri(String EpopteiaId){
		return EPOPTEIES + "/" + EpopteiaId;
	}

}



	