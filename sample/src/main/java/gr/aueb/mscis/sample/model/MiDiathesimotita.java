package gr.aueb.mscis.sample.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import gr.aueb.mscis.sample.util.SimpleCalendar;

@Entity
@Table(name = "midiathesimotita")
public class MiDiathesimotita {

	@Id
	@Column(name="id")
	private Integer id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="epoptisid")
	private Epoptis epoptis; 
	
	@org.hibernate.annotations.Type(
            type="gr.aueb.mscis.sample.persistence.SimpleCalendarCustomType")
	@Column(name="starts")
	private SimpleCalendar date;

	public MiDiathesimotita()
	{}
	
	public MiDiathesimotita(SimpleCalendar date)
	{
		this.date = date;
	}
	
	public Integer getId()
	{
		return id;
	}
	
	public Epoptis getEpoptis() {
		return epoptis;
	}

	public void setEpoptis(Epoptis epoptis) {
		this.epoptis = epoptis;
	}
	
	public SimpleCalendar getDate() {
		return date;
	}

	public void setDate(SimpleCalendar date) {
		this.date = date;
	}
	

}


