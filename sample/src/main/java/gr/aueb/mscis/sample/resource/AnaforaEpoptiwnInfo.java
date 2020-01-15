package gr.aueb.mscis.sample.resource;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import gr.aueb.mscis.sample.model.Epopteia;
import gr.aueb.mscis.sample.model.Epoptis;

@XmlRootElement
public class AnaforaEpoptiwnInfo {
	
	@XmlElement(name="Epopteia")
	private String name;
	
	@XmlElementWrapper(name = "Epoptes")
	@XmlElement(name = "Epoptis")
	private List<String> epoptes = new ArrayList();
	
	public AnaforaEpoptiwnInfo() {
	
	}
	
	public AnaforaEpoptiwnInfo(Epopteia epopteia) {
		this.name = "id="+epopteia.getId()+" "+epopteia.getMathima().getTitle()+" "+epopteia.getStarts().getDayOfMonth()+"/"+epopteia.getStarts().getMonth()+"/"+epopteia.getStarts().getYear()+
					" "+epopteia.getStarts().getHour()+":"+epopteia.getStarts().getMinute()+"-"+epopteia.getEnds().getHour()+":"+epopteia.getEnds().getMinute();
		
		for(Epoptis epoptis : epopteia.getEpoptis()) {
			epoptes.add("id="+epoptis.getId()+" "+epoptis.getName() + " " + epoptis.getSurName());
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
//	public List<String> getEpoptes() {
//		return epoptes;
//	}
//
//	public void setEpoptes(List<String> epoptes) {
//		this.epoptes = epoptes;
//	}

}
