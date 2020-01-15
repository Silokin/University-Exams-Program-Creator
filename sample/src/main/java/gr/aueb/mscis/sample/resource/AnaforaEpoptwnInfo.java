package gr.aueb.mscis.sample.resource;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;

import gr.aueb.mscis.sample.model.Epopteia;
import gr.aueb.mscis.sample.model.Epoptis;

@XmlRootElement
public class AnaforaEpoptwnInfo {
	
	@XmlElement(name="Epoptis")
	private String name;
	
	@XmlElementWrapper(name = "Epopteies")
	@XmlElement(name = "Epopteia")
	private List<String> epopteies = new ArrayList();
	
	public AnaforaEpoptwnInfo() {
		
	}
	
	public AnaforaEpoptwnInfo(Epoptis epoptis,int id) {
		this.name = "id="+epoptis.getId()+" "+epoptis.getName() + " " + epoptis.getSurName();
		for(Epopteia epopteia : epoptis.getEpopteies()) {
			if(epopteia.getProgram().getId() == id)
				this.epopteies.add("id="+epopteia.getId()+" "+epopteia.getMathima().getTitle()+" "+epopteia.getStarts().getDayOfMonth()+"/"+epopteia.getStarts().getMonth()+"/"+epopteia.getStarts().getYear()+
						" "+epopteia.getStarts().getHour()+":"+epopteia.getStarts().getMinute()+"-"+epopteia.getEnds().getHour()+":"+epopteia.getEnds().getMinute());
		}
	}
	
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//	
//
//	public List<String> getEpopteies() {
//		return epopteies;
//	}
//
//	public void setEpopteies(List<String> epopteies) {
//		this.epopteies = epopteies;
//	}

}
