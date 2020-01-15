package gr.aueb.mscis.sample.resource;

import gr.aueb.mscis.sample.util.SimpleCalendar;
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

	public static final String EPOPTEIES_SEARCH = "epopteies/search";

	/**
	 * /Epoptes
	 */
	public static final String EPOPTES = "epoptes";

	public static final String EPOPTES_SEARCH = "epoptes/search";
	
	public static final String EPOPTES_ADD_MD = "epoptes/addDate";
	/**
	 * /Program
	 */
	public static final String PROGRAM = "program";

	public static final String PROGRAM_SEARCH = "program/search";
	/**
	 * /mathima
	 */
	public static final String MATHIMATA = "mathimata";

	public static final String MATHIMATA_SEARCH = "mathimata/search";

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
	
	public static String epoptisSearchUri(String name) {
		return EPOPTES_SEARCH + "?name=" + name;
	}
	
	public static String addMDUri (String mail , String pass) {
		return EPOPTES_ADD_MD + "?email=" + mail +"&password=" + pass;
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
	
	public static String epopteiesAddClassUri(String EpopteiaId){
		return epopteiesUri(EpopteiaId) + "/addAithousa";
	}
	
	public static String epopteiesAnathesiUri(String EpopteiaId){
		return epopteiesUri(EpopteiaId) + "/addEpopti";
	}
	
	public static String programIdUri(String id) {
		return PROGRAM + "/" + id;
	}
	
	public static String anaforaEpoptwnUri(String id) {
		return PROGRAM + "/" + id + "/anaforaEpoptwn";
	}
	
	public static String anaforaEpoptiwnUri(String id) {
		return PROGRAM + "/" + id + "/anaforaEpoptiwn";
	}
	
	public static String programSearchUri(SimpleCalendar date) {
		return PROGRAM_SEARCH + "?date=" + date;
	}
	
	/**
	 * /mathima/{id}, <br>
	 * e.g. /mathima/1
	 */
	public static String mathimaIdUri(String id) {
		return MATHIMATA + "/" + id;
	}
	
	/**
	 * /?title={title}, <br>
	 * e.g. /mathima?title=UML
	 */
	public static String mathimaSearchUri(String title) {
		return MATHIMATA_SEARCH + "?title=" + title;
	}

}	