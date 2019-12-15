package gr.aueb.mscis.sample.model;

import java.security.Timestamp;
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
	
	@org.hibernate.annotations.Type(
	            type="gr.aueb.mscis.sample.persistence.SimpleCalendarCustomType")
	@Column(name="starts")
    private SimpleCalendar starts;
    
	@org.hibernate.annotations.Type(
            type="gr.aueb.mscis.sample.persistence.SimpleCalendarCustomType")
    @Column(name="ends")
    private SimpleCalendar ends;
	
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
	
	
	//epopteies ws antigrafo tou set tis klasis tou programa extatastikis
	public Set<Epopteia> getEpopteies() {
        return new HashSet<Epopteia>(epopteies);
    }
	
	//lambanw tis epopteies pou yparxoun sto ptogramma exetastikis
	public Set<Epopteia> friendEpopteies()
	{
		return epopteies;
	}
	
	//mia epopteia theloume na prostethei sto programma eksetatistikis
	public void addEpopteia(Epopteia epopteia) {
        if (epopteia != null)
        	this.epopteies.add(epopteia);
            epopteia.setProgram(this);
        
    }
	
	//mia epopteia theloume na afairethei apo to programma exetastikis
	public void removeEpopteia(Epopteia epopteia)
	{
		if (epopteia != null)
			epopteia.setProgram(null);
	}
	
	public Integer getId()
	{
		return id;
	}
	
	public SimpleCalendar getStart() {
		return this.starts;
	}
	
	public void setStart(SimpleCalendar s) {
		this.starts = s;
	}
	
	public SimpleCalendar getEnd() {
		return this.ends;
	}
	
	public void setEnd(SimpleCalendar s) {
		this.ends = s;
	}
}


