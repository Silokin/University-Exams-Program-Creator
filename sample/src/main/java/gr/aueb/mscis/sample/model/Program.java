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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import gr.aueb.mscis.sample.util.SimpleCalendar;


/**
* Το πρόγραμμα εξεταστικής
* @authorMscIS-AlexGianTas
*
*/
@Entity
@Table(name="program")
public class Program {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO) //generate automatically
    private Integer id;
	
	
	//pote ksekina i eksetastiki
	@org.hibernate.annotations.Type(
	            type="gr.aueb.mscis.sample.persistence.SimpleCalendarCustomType")
	@Column(name="starts")
    private SimpleCalendar starts;
    
	//pote teleiwnei i eksetastiki
	@org.hibernate.annotations.Type(
            type="gr.aueb.mscis.sample.persistence.SimpleCalendarCustomType")
    @Column(name="ends")
    private SimpleCalendar ends;
	
	//poses epopteies ehei i exetastiki
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE}, 
            mappedBy="program", fetch=FetchType.LAZY)
	private Set<Epopteia> epopteies = new HashSet<Epopteia>();
	
	public Program() 
	{}
	
	
	public Program(SimpleCalendar starts, SimpleCalendar ends)
	{
		this.starts = starts;
		this.ends = ends;
	}
	
	public Integer getId()
	{
		return id;
	}
	
	public SimpleCalendar getStarts()
	{
		return starts;
	}
	
	public void setStarts(SimpleCalendar s) {
		this.starts = s;
	}
	
	public SimpleCalendar getEnds() {
		return this.ends;
	}
	
	public void setEnds(SimpleCalendar s) {
		this.ends = s;
	}
	
	//epopteies ws antigrafo tou set tis klasis tou programa extatastikis
	public Set<Epopteia> getEpopteies() {
        return new HashSet<Epopteia>(epopteies);
    }
	
	//lambanw tis epopteies pou yparxoun sto programma exetastikis
	public Set<Epopteia> friendEpopteies()
	{
		return epopteies;
	}
	
	//mia epopteia theloume na prostethei sto programma eksetatistikis
	public void addEpopteia(Epopteia epopteia) {
	     if (epopteia != null) {
	    	this.epopteies.add(epopteia);
	     	epopteia.setProgram(this);
	     }
	}
		
//		//mia epopteia theloume na afairethei apo to programma exetastikis
//		public void removeEpopteia(Epopteia epopteia)
//		{
//			if (epopteia != null)
//				epopteia.setProgram(null);
//		}
//		
//	//mia epopteia theloume na prostethei sto programma eksetatistikis
//	public void addEpopteia(Epopteia epopteia) {
//        if (epopteia != null)
//            epopteia.setProgram(this);
//        
//    }
	
	//mia epopteia theloume na afairethei apo to programma exetastikis
	public void removeEpopteia(Epopteia epopteia)
	{
		if (epopteia != null)
		{
			this.epopteies.remove(epopteia);
			epopteia.setProgram(null);
		}
	}
	
	
}


