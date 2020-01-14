package gr.aueb.mscis.sample.resource;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;

import gr.aueb.mscis.sample.model.Epopteia;
import gr.aueb.mscis.sample.model.Epoptis;

@XmlRootElement(name="epoptis")
public class AnaforaEpoptwnInfo {
	
	private String name;
	
	@XmlList
	private List<String> epopteies;
	
	public AnaforaEpoptwnInfo() {
		
	}
	
	public AnaforaEpoptwnInfo(Epoptis epoptis,int id) {
		this.name = epoptis.getName() + " " + epoptis.getSurName();
		for(Epopteia epopteia : epoptis.getEpopteies()) {
			if(epopteia.getProgram().getId() == id)
			this.epopteies.add(epopteia.getMathima().getTitle()+" "+epopteia.getStarts().toString());
		}
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	//@XmlElementWrapper(name = "epopteies")
	//@XmlElement(name = "epopteia")
	public List<String> getEpopteies() {
		return epopteies;
	}

	public void setEpopteies(List<String> epopteies) {
		this.epopteies = epopteies;
	}

}
