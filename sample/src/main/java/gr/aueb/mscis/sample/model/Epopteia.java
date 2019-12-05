package gr.aueb.mscis.sample.model;

import java.security.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import gr.aueb.mscis.sample.util.SimpleCalendar;

public class Epopteia {


	@Id
    @Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
	
	@org.hibernate.annotations.Type(
            type="gr.aueb.mscis.sample.persistence.SimpleCalendarCustomType")
	@Column(name="starts")
    private SimpleCalendar starts;
    
	@org.hibernate.annotations.Type(
            type="gr.aueb.mscis.sample.persistence.SimpleCalendarCustomType")
    @Column(name="ends")
    private SimpleCalendar ends;
	
    
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, 
            fetch=FetchType.LAZY)
    @JoinTable(name="epopteia_aithousa", 
            joinColumns = {@JoinColumn(name="epopteia_id")},
            inverseJoinColumns = {@JoinColumn(name="aithousa_id")})
    private Set<Aithousa> aithouses = new HashSet<Aithousa>();
	
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, 
            fetch=FetchType.LAZY)
    @JoinTable(name="epopteia_epoptis", 
            joinColumns = {@JoinColumn(name="epopteia_id")},
            inverseJoinColumns = {@JoinColumn(name="epoptis_id")})
    private Set<Epoptis> epoptes = new HashSet<Epoptis>();
	
	
	//leei ti mathima ehei pou epopteyei I KATHE EPOPTEIA POU EPEKSERGAZOMASTE
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mathimaid")
    private Mathima mathima;


	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="exetastikiid")
	private ProgramExe pex;
	
	
	public Epopteia()
	{}
	
	public Epopteia(SimpleCalendar starts, SimpleCalendar ends)
	{
		this.starts = starts;
		this.ends = ends;
	
	}
	
	public void setProgramExe(ProgramExe pex)
	{
		if (this.pex != null) {
            this.pex.friendEpopteies().remove(this);
        }
        this.pex = pex;
        
        if (this.pex != null) {
            this.pex.friendEpopteies().add(this);
        }
	}
	
	public ProgramExe getProgramExe()
	{
		return pex;
	}
	
	public Integer getId()
	{
		return id;
	}
	
	public SimpleCalendar getStarts()
	{
		return starts;
	}
	
	public SimpleCalendar getEnds()
	{
		return ends;
	}
	
	//orizw mathima gia ti sugkekrimeni epopteia
	public void setMathima(Mathima mathima)
	{
		if (mathima!= null)
			this.mathima = mathima;
		/*if (mathima!=null)
			mathima.setEpopteia(this);*/
	}
	
//	
//	public void removeMathima(Mathima mathima)
//	{
//		if (mathima!=null)
//			this.mathima = null;
//	}
	
	public Set<Aithousa> getAithouses() {
	        return new HashSet<Aithousa>(aithouses);
	}
	
	//vale aithouses gia multiple different epopteiess
	//dld gia epopteia 20/2/20 vale 2-3 diaforetikes aithouses
	//prosoxi na ginete add aithousa (elegxos) mono AN DEN YPARXEI idi
	public void addAithousa(Aithousa aithousa) {
	        if (aithousa != null) {
	        	aithousa.friendEpopteia().add(this);
	            this.aithouses.add(aithousa);
	        }
	}
	
	//vgale aithousa (elegxos) MONO an yparxei idi
	
	public void removeAithousa(Aithousa aithousa) {
	        if (aithousa != null) {
	            aithousa.friendEpopteia().remove(this);
	            this.aithouses.remove(aithousa);
	        }
	 }
 
	Set<Aithousa> friendAithousa() {
	    return aithouses;
	}
	

	//prosthese epopti stin epopteia
	//elexe omws mipws eisai full? (elegxos1)
	//elexe an yparxei idi o epoptis? (elegxos2)
	public void addEpopti(Epoptis epopti) {
	        if (epopti != null) {
	        	epopti.friendEpopteia().add(this);
	            this.epoptes.add(epopti);
	        }
	}
	
	//kane remove epopti apo epopteia
	//elexe omws mipws den yparxei kaneis? (elegxos1)
	//mipws den yphrxe pote o sugkekrimenos epoptis? (elegxos2)
	public void removeEpopti(Epoptis epopti) {
	        if (epopti != null) {
	        	epopti.friendEpopteia().remove(this);
	            this.epoptes.remove(epopti);
	        }
	 }
 
	Set<Epoptis> friendEpoptis() {
	    return epoptes;
	}
	

	public Set<Epoptis> getEpoptes() {
	        return new HashSet<Epoptis>(epoptes);
	}

	@Override
	public String toString() {
		return "Epopteia [id=" + id + ", starts=" + starts + ", ends=" + ends + "]";
	}
	
}