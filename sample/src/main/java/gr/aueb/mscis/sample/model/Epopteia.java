package gr.aueb.mscis.sample.model;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import gr.aueb.mscis.sample.util.SimpleCalendar;

/**
 * Οι εποπτείες που αναθέτονται από την Γραμματεία σε Επόπτες συγκεκριμένων κατηγοριών,
 * δηλαδή Προσωπικό Τμήματος και Υποψήφιος Διδάκτωρ
 * @author MscIS-AlexGianTas
 */
@Entity
@Table(name="epopteia")
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
    private Set<Aithousa> aithousa = new HashSet<Aithousa>();
	
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, 
            fetch=FetchType.LAZY)
    @JoinTable(name="epopteia_epoptis", 
            joinColumns = {@JoinColumn(name="epopteia_id")},
            inverseJoinColumns = {@JoinColumn(name="epoptis_id")})
    private Set<Epoptis> epoptis = new HashSet<Epoptis>();
	
	
	//leei ti mathima ehei pou epopteyei I KATHE EPOPTEIA POU EPEKSERGAZOMASTE
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="mathimaid")
    private Mathima mathima;


	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="exetastikiid")
	private Program program;
	
	public Epopteia()
	{}
	
	public Epopteia(SimpleCalendar starts, SimpleCalendar ends)
	{
		this.starts = starts;
		this.ends = ends;
	
	}
	
	public Program getProgram()
	{
		return program;
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
		if (this.mathima!= null) {
			this.mathima.setEpopteia(null);
		this.mathima = mathima;
		if(mathima!=null)
			this.mathima.setEpopteia(this);
		}
	}
	
	
	public void removeMathimaDirect()
	{
		if (this.mathima!=null)
			this.mathima.removeEpopteiaIndirect();
			this.mathima = null;
			
	}
	
	public void removeMathimaIndirect()
	{
		if (this.mathima!=null)
			this.mathima = null;
			
	}
	
	
	public Set<Aithousa> getAithouses() {
	        return new HashSet<Aithousa>(aithousa);
	}
	
	//vale aithouses gia multiple different epopteiess
	//dld gia epopteia 20/2/20 vale 2-3 diaforetikes aithouses
	//prosoxi na ginete add aithousa (elegxos) mono AN DEN YPARXEI idi
	//elegxei pws h ai8ousa einai dia8esimh 
	public void addAithousa(Aithousa aithousa) {
	        if (aithousa != null && this.aithousa.contains(aithousa)==false) {
	        	boolean check = false;
	        	for (Epopteia teia : aithousa.getEpopteies()) {
	        		check=interval(teia);
	        		if(check == true) break;	
	        	}
	        	if(check==false) {
	        	aithousa.friendEpopteia().add(this);
	            this.aithousa.add(aithousa);
	        	}
	        }
	}
	
	//vgale aithousa (elegxos) MONO an yparxei idi
	
	public void removeAithousa(Aithousa aithousa) {
	        if (aithousa != null) {
	            aithousa.friendEpopteia().remove(this);
	            this.aithousa.remove(aithousa);
	        }
	 }
 
	Set<Aithousa> friendAithousa() {
	    return aithousa;
	}
	

	//prosthese epopti stin epopteia
	//elexe omws mipws eisai full? (elegxos1)
	//elexe an yparxei idi o epoptis? (elegxos2)
	public void addEpopti(Epoptis epopti) {
        if (epopti != null && epopti.getEpopteies().size()<epopti.getCategory().getMaxEpopteies()) {
	        	boolean check = false;
	        	for(SimpleCalendar md : epopti.getMiDiathesimotita()) {
	        		if(md.getYear()==getStarts().getYear() 
	        		   && md.getMonth()==getStarts().getMonth()
	        		   && md.getDayOfMonth()==getStarts().getDayOfMonth()) {
	        			check = true;
	        			break;
	        		}		
	        	}
	        	for (Epopteia teia : epopti.getEpopteies()) {
	        		check=interval(teia);
	        		if(check == true) break;	
	        	}
	        	if(check==false) {
	        	epopti.friendEpopteia().add(this);
	            this.epoptis.add(epopti);
	        	}
        }
	}
	
	//kane remove epopti apo epopteia
	//elexe omws mipws den yparxei kaneis? (elegxos1)
	//mipws den yphrxe pote o sugkekrimenos epoptis? (elegxos2)
	public void removeEpopti(Epoptis epopti) {
	        if (epopti != null) {
	        	epopti.friendEpopteia().remove(this);
	            this.epoptis.remove(epopti);
	        }
	 }
 
	Set<Epoptis> friendEpoptis() {
	    return epoptis;
	}
	

	public Set<Epoptis> getEpoptes() {
	        return new HashSet<Epoptis>(epoptis);
	}
	


	@Override
	public String toString() {
		return "Epopteia [id=" + id + ", starts=" + starts + ", ends=" + ends + "]";
	}

	public void setProgram(Program programe) {
		// TODO Auto-generated method stub

		if (this.program != null) {
            this.program.friendEpopteies().remove(this);
        }
        
        this.program = programe;
		
        if (this.program != null) {
            this.program.friendEpopteies().add(this);
        }
	}
	

	public boolean interval (Epopteia epopteia) {
		if((getStarts().compareTo(epopteia.getStarts())>=0&&getStarts().compareTo(epopteia.getEnds())<0) 
				||(epopteia.getStarts().compareTo(getStarts())>=0&&epopteia.getStarts().compareTo(getEnds())<0)  
				||(getStarts().compareTo(epopteia.getStarts())>=0&&getEnds().compareTo(epopteia.getEnds())<=0)
				||(epopteia.getStarts().compareTo(getStarts())>=0&&epopteia.getEnds().compareTo(getEnds())<=0))
			return true;
		return false;
	}

}