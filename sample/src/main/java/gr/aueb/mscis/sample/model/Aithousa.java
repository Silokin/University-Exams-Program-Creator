package gr.aueb.mscis.sample.model;

import java.util.Date;
import java.util.HashSet;
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
import javax.persistence.Table;

/**
 * Οι αίθουσες μιας Σχολής
 * @author MscIS-AlexGianTas
 */
@Entity
@Table(name = "aithousa")
public class Aithousa {

	@Id //primary key
    @Column(name="id") //onoma column
    @GeneratedValue(strategy = GenerationType.AUTO) //generate automatically
    private int id;
	
	@Column(name="name", length=50, nullable = false)
	private String name;
	
	@Column(name="orofos", length=50, nullable = false)
	private String orofos;
	
	@Column(name="noThesewn", nullable = false)
	private Integer noThesewn;
	
	@Column(name="noEpoptes", nullable = false)
	private Integer noEpoptes;
	
	@Column(name="ktirio", nullable = false)
	private String ktirio;
	
	
	@ManyToMany(mappedBy="aithousa",fetch=FetchType.LAZY, 
	            cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Epopteia> epopteies = new HashSet<Epopteia>();
	 
	
	public Aithousa() {}
	
	public Aithousa(String name, String orofos, Integer noThesewn, Integer noEpoptes, String ktirio)
	{
		this.name = name;
		this.orofos = orofos;
		this.noThesewn = noThesewn;
		this.noEpoptes = noEpoptes;
		this.ktirio = ktirio;
	}
	
	public Integer getId()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getOrofos()
	{
		return orofos;
	}
	
	public void setOrofos(String orofos)
	{
		this.orofos = orofos;
	}
	
	
	public Integer getNoThesewn()
	{
		return noThesewn;
	}
	
	public void setNoThesewn(Integer noThesewn)
	{
		this.noThesewn = noThesewn;
	}
	
	
	public Integer getNoEpoptes()
	{
		return noEpoptes;
	}
	
	public void setNoEpoptes(Integer noEpoptes)
	{
		this.noEpoptes = noEpoptes;
	}
	
	
	public String getKtirio()
	{
		return ktirio;
	}
	
	public void setKtirio(String ktirio)
	{
		this.ktirio = ktirio;
	}
	
	public Set<Epopteia> getEpopteies() {
	        return new HashSet<Epopteia>(epopteies);
	}

	public void addEpopteia(Epopteia epopteia) {
	        if (epopteia != null && this.epopteies.contains(epopteia)==false) {
	        	//elegxoume an sumpimptoun duo epopteies
	        	boolean check = false;
	        	for (Epopteia teia : getEpopteies()) {
	        		check=teia.interval(epopteia);
	        		if(check == true) break;	
	        	}
	        	if(check==false) {
	            epopteia.friendAithousa().add(this);
	            this.epopteies.add(epopteia);
	        	}
	        }
	}
	 
	public void removeEpopteia(Epopteia epopteia) {
	        if (epopteia != null) {
	            epopteia.friendAithousa().remove(this);
	            this.epopteies.remove(epopteia);
	        }
	 }
	 
	 public Set<Epopteia> friendEpopteia()
	 {
		 return epopteies;
	 }

	 @Override
		public String toString() {
			return "Aithousa [id=" + id + ", name=" + name + ", orofos=" + orofos + ", noThesewn=" + noThesewn + ", noEpoptes =" 
					+noEpoptes + ", ktirio="+ ktirio +"]";
		}
}