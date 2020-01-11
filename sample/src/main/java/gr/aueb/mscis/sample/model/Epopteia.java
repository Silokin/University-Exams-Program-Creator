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
            joinColumns = {@JoinColumn(name="epopteia")},
            inverseJoinColumns = {@JoinColumn(name="epoptis")})
    private Set<Epoptis> epoptis = new HashSet<Epoptis>();
	
	
	//leei ti mathima ehei pou epopteyei I KATHE EPOPTEIA POU EPEKSERGAZOMASTE
	@OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name="mathimaid")
    private Mathima mathima;


	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="program")
	private Program program;
	
	public Epopteia()
	{}
	
	public Epopteia(SimpleCalendar starts, SimpleCalendar ends)
	{
		this.starts = starts;
		this.ends = ends;
	
	}
	
	public Program getProgram() {
		return program;
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
	
	public Mathima getMathima()
	{
		return mathima;
	}
//	public void removeMathima(Mathima mathima)
//	{
//		if (mathima!=null)
//			this.mathima = null;
//	}
//	public void removeMathimaDirect()
//	{
//		if (this.mathima!=null)
//			this.mathima.removeEpopteiaIndirect();
//			this.mathima = null;
//			
//	}
//	
//	public void removeMathimaIndirect()
//	{
//		if (this.mathima!=null)
//			this.mathima = null;
//			
//	}
	
	public Set<Aithousa> getAithouses() {
	        return new HashSet<Aithousa>(aithousa);
	}
	
	//vale aithouses gia multiple different epopteiess
	//dld gia epopteia 20/2/20 vale 2-3 diaforetikes aithouses
	//prosoxi na ginete add aithousa (elegxos) mono AN DEN YPARXEI idi
	
	
//	//elegxei pws i atihousa einai diathesimi
//	public void addAithousa(Aithousa aithousa) {
//			if (aithousa != null) {
//	        	boolean check = false;
//	        	for (Epopteia teia : aithousa.getEpopteies()) {
//	        		check=interval(teia);
//	        		if(check == true) break;	
//	        	}
//	        	if(check==false) {
//	        		aithousa.friendEpopteia().add(this);
//	        		this.aithousa.add(aithousa);
//	        	}
//			}
//	        
//	}


	//elegxei pws i atihousa einai diathesimi
	public void addAithousa(Aithousa aithousa) {
			if (aithousa != null && aithousa.canAddEpopteia(this)) {
	        		aithousa.friendEpopteia().add(this);
	        		this.aithousa.add(aithousa);
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
		//an o epoptis yparxei kai mporei na epopteusei (2nd check for canEpopteusei)
		if (epopti != null) {// && epopti.canEpopteusei() && epopti.canAddEpopteia(this)) {
	        	epopti.friendEpopteia().add(this);
	            this.epoptis.add(epopti);
        }
	}
	
	public boolean doNotExceedMaxNumOfEpoptesInAithousa()
	{
		int max =0;
		for (Aithousa aithousa: getAithouses())
			{
			//max epoptes based on aithousa
				max=aithousa.getNoEpoptes();
				//epoptes gia ti sugkkerimeni epopteia
				//an to sunolo twn epoptwn gia auti tin epopteia
				//sugkekrimenes wres
				//einai to max gia kapoia aithousa
				if (getEpoptis().size() != max)
					return true;
			}
		return false;
	}
	
	
	 //elegxei gia to an ginetai na ekxwrithei i epopteia stin arxi tis methodou
    public Epopteia ekxwrhshEpopteias(Epoptis epoptis) 
    {
    	//edwses epopti?
    	if (epoptis == null)
    		return null;
    	//ehie diathesimes epopteies wste na tou dwsoume auti?
    	if (!epoptis.canEpopteusei())
    		return null;
    	//einai diathesimos?
    	if (epoptis.getState() == EpoptisState.UNAVAILABLE)
    		return null;
    	
    	
    	if (!epoptis.canAddEpopteia(this))
    		return null;
    	
    	
			epoptis.addEpopteia(this);
			addEpopti(epoptis);
			
    	//an meta apo auti tin ekxwrhsh epopteias,
		//to synolo epopteiwn tou epoptis einai iso me to MAX, 
		///tote set him/her as UNAVAILABLE
    	if (!epoptis.canEpopteusei())
    		epoptis.setState(EpoptisState.UNAVAILABLE);
    	
    	//updated epopteia me epopti!
    	return this;
    	
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
	

	public Set<Epoptis> getEpoptis() {
	        return new HashSet<Epoptis>(epoptis);
	}
	


	@Override
	public String toString() {
		return "Epopteia [id=" + id + ", starts=" + starts + ", ends=" + ends + "]";
	}


	
	public boolean interval (Epopteia epopteia) {
		
		//an i epopteia pou pas na valeis den einai idia me tin wra pou arxizei mia alli
		//13:30 - 15:30
//		if (!getStarts().equals(epopteia.getStarts()))// && !getEnds().equals(epopteia.getEnds()))// != epopteia.getStarts())
//			return true;

		
		//paw na sou valw 12:30 - 15:30 get
		//kai eheis 13:30 - 15:30 epopteia.get
		//ara den mporeis
		
		//2nd
		//paw na sou vale 14:30-15:30 get
		//kai eheis 13:30 - 15:30 epopteia.get
//		//ara den mporeis
//		if (getStarts().compareTo(epopteia.getStarts()) < 0)
//			return false;
//		if (getStarts().compareTo(epopteia.getStarts()) > 0)
//			return false;
		//an i epopteia pou pas na valeis den einai sto endiameso mias allis epopteias
		
		//an i epopteia pou pas na valeis den teleiwnei enw eheis kapoia alli epopteia
		
		
		
		if((getStarts().compareTo(epopteia.getStarts()) >=0 && getStarts().compareTo(epopteia.getEnds()) < 0) 
		||(epopteia.getStarts().compareTo(getStarts()) >=0 && epopteia.getStarts().compareTo(getEnds()) < 0)  
		||(getStarts().compareTo(epopteia.getStarts()) >=0 && getEnds().compareTo(epopteia.getEnds()) <= 0)
		||(epopteia.getStarts().compareTo(getStarts()) >=0 && epopteia.getEnds().compareTo(getEnds()) <= 0))
			return false;
		return true;
	}

}