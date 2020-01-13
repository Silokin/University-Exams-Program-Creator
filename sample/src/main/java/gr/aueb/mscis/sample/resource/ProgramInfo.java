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
	
	private int startD;
	
	private int startM;
	
	private int startY;
	
	private int endD;
	
	private int endM;
	
	private int endY;
	

	public ProgramInfo() {
	}
	
	
	public ProgramInfo(Integer id ,  int startD,int startM,int startY,int endD,int endM,int endY) {
		this(startD,startM,startY,endD,endM,endY);
		this.id = id;

	}
	
	public ProgramInfo(int startD,int startM,int startY,int endD,int endM,int endY)
	{
		super();
		this.startD=startD;
		this.startM = startM;
		this.startY=startY;
		this.endD=endD;
		this.endM = endM;
		this.endY=endY;
		
	} 



	public ProgramInfo(Program program) {
		id = program.getId(); 
		this.startD= program.getStarts().getDayOfMonth();
		this.startM = program.getStarts().getMonth();
		this.startY= program.getStarts().getYear();
		this.endD= program.getEnds().getDayOfMonth();
		this.endM = program.getEnds().getMonth();
		this.endY= program.getEnds().getYear();
	}




	public Integer getId() {
		return id;
	}




	public void setId(Integer id) {
		this.id = id;
	}





	
	public int getStartD() {
		return startD;
	}


	public void setStartD(int startD) {
		this.startD = startD;
	}


	public int getStartM() {
		return startM;
	}


	public void setStartM(int startM) {
		this.startM = startM;
	}


	public int getStartY() {
		return startY;
	}


	public void setStartY(int startY) {
		this.startY = startY;
	}


	public int getEndD() {
		return endD;
	}


	public void setEndD(int endD) {
		this.endD = endD;
	}


	public int getEndM() {
		return endM;
	}


	public void setEndM(int endM) {
		this.endM = endM;
	}


	public int getEndY() {
		return endY;
	}


	public void setEndY(int endY) {
		this.endY = endY;
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
		program.setStarts(new SimpleCalendar(startD,startM,startY,0,0));
		program.setEnds(new SimpleCalendar(endD,endM,endY,0,0));

		return program;
	}
	
	
}
