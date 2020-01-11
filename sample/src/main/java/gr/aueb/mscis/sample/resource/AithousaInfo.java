package resource;

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


@XmlRootElement
public class AithousaInfo {

	private Integer id;
	
	private String name;
	
	private String orofos;

	private Integer noThesewn;

	private Integer noEpoptes;

	private String ktirio;

	public AithousaInfo() {
	}

	public AithousaInfo(int id,String name, String orofos, Integer noThesewn, Integer noEpoptes, String ktirio) {
		this(name, orofos, noThesewn, noEpoptes, ktirio);
		this.id = id;

	}
	
	public AithousaInfo(String name, String orofos, Integer noThesewn, Integer noEpoptes, String ktirio) {
		super();
		this.name = name;
		this.orofos = orofos; 
		this.noThesewn = noThesewn;
		this.noEpoptes = noEpoptes;
		this.ktirio = ktirio;
	}

	public AithousaInfo(Aithousa aithousa) {
		name = aithousa.getName();
		orofos = aithousa.getOrofos();
		noThesewn = aithousa.getNoThesewn();
		noEpoptes = aithousa.getNoEpoptes();
		ktirio = aithousa.getKtirio();
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getOrofos() {
		return orofos;
	}


	public void setOrofos(String orofos) {
		this.orofos = orofos;
	}


	public Integer getNoThesewn() {
		return noThesewn;
	}


	public void setNoThesewn(Integer noThesewn) {
		this.noThesewn = noThesewn;
	}


	public Integer getNoEpoptes() {
		return noEpoptes;
	}


	public void setNoEpoptes(Integer noEpoptes) {
		this.noEpoptes = noEpoptes;
	}


	public String getKtirio() {
		return ktirio;
	}


	public void setKtirio(String ktirio) {
		this.ktirio = ktirio;
	}

	public static AithousaInfo wrap(Aithousa b) {
		return new AithousaInfo(b);
	}

	public static List<AithousaInfo> wrap(List<Aithousa> aithouses) {

		List<AithousaInfo> aithousaInfoList = new ArrayList<>();

		for (Aithousa b : aithouses) {
			aithousaInfoList.add(new AithousaInfo(b));
		}

		return aithousaInfoList;

	}
	
	public Aithousa getAithousa(EntityManager em) {

		Aithousa aithousa = null;

		if (id != null) {
			aithousa = em.find(Aithousa.class, id);
		} else {
			aithousa = new Aithousa();
		}

		aithousa.setNoEpoptes(noEpoptes);
		aithousa.setNoThesewn(noThesewn);
		aithousa.setOrofos(orofos);


		return aithousa;
	}
	
	
}




	