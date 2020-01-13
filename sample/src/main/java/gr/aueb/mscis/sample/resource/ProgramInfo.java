package gr.aueb.mscis.sample.resource;

import gr.aueb.mscis.sample.model.Aithousa;
import gr.aueb.mscis.sample.model.Program;
import gr.aueb.mscis.sample.util.SimpleCalendar;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class ProgramInfo {
	
	private Integer id;
	
	private SimpleCalendar starts;
	
	private SimpleCalendar ends;
	

	public ProgramInfo() {
	}
	
	
	public ProgramInfo(Integer id , SimpleCalendar starts, SimpleCalendar ends) {
		this(starts, ends);
		this.id = id;

	}
	
	public ProgramInfo(SimpleCalendar starts, SimpleCalendar ends)
	{
		super();
		this.starts = starts;
		this.ends = ends;
		
	} 



	public ProgramInfo(Program program) {
		id = program.getId(); 
		starts = program.getStarts();
		ends = program.getEnds();
	}




	public Integer getId() {
		return id;
	}




	public void setId(Integer id) {
		this.id = id;
	}




	public SimpleCalendar getStarts() {
		return starts;
	}




	public void setStarts(SimpleCalendar starts) {
		this.starts = starts;
	}




	public SimpleCalendar getEnds() {
		return ends;
	}




	public void setEnds(SimpleCalendar ends) {
		this.ends = ends;
	}
	
	public static ProgramInfo wrap(Program program) {
		return new ProgramInfo(program);
	}
	
	public static List<ProgramInfo> wrap(List<Program> Programs) {

		List<ProgramInfo> programInfoList = new ArrayList<>();

		for (Program program : Programs) {
			programInfoList.add(new ProgramInfo(program));
		}

		return programInfoList;
 
	}   
	
	public Program getProgram(EntityManager em) {

		Program program = null;

		if (id != null) {
			program = em.find(Program.class, id);
		} else {
			program = new Program();
		}
		program.setStarts(starts);
		program.setEnds(ends);

		return program;
	}
	
	
}
