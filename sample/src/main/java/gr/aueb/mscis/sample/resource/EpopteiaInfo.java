package gr.aueb.mscis.sample.resource;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.xml.bind.annotation.XmlRootElement;

import gr.aueb.mscis.sample.model.Epopteia;
import gr.aueb.mscis.sample.service.MathimaService;
import gr.aueb.mscis.sample.service.ProgramService;
import gr.aueb.mscis.sample.util.SimpleCalendar;

@XmlRootElement
public class EpopteiaInfo {
	
	private int id = 0;
	
	private int mathimaId;
	
	private int programId;
	
	private int startD;
	
	private int startM;
	
	private int startY;
	
	private int startH;
	
	private int startMin;
	
	private int endD;
	
	private int endM;
	
	private int endY;
	
	private int endH;
	
	private int endMin;
	
	public EpopteiaInfo() {
	}
	
	public EpopteiaInfo(int id, int mathimaId, int programId, int startD,int startM,int startY,int startH,int startMin,int endD,int endM,int endY,int endH,int endMin) {
		
		this(mathimaId,programId,startD,startM,startY,startH,startMin,endD,endM,endY,endH,endMin);
		this.id = id;
	}
	
	public EpopteiaInfo(int mathimaId,int programId,int startD,int startM,int startY,int startH,int startMin,int endD,int endM,int endY,int endH,int endMin) {
		super();
		this.mathimaId=mathimaId;
		this.programId=programId;
		this.startD=startD;
		this.startM = startM;
		this.startY=startY;
		this.startH=startH;
		this.startMin=startMin;
		this.endD=endD;
		this.endM = endM;
		this.endY=endY;
		this.endH=endH;
		this.endMin=endMin;
	}
	
	public EpopteiaInfo(Epopteia epopteia) {
		this.id = epopteia.getId();
		this.mathimaId = epopteia.getMathima().getId();
		this.programId = epopteia.getProgram().getId();
		this.startD = epopteia.getStarts().getDayOfMonth();
		this.startM = epopteia.getStarts().getMonth();
		this.startY = epopteia.getStarts().getYear();
		this.startH = epopteia.getStarts().getHour();
		this.startMin = epopteia.getStarts().getMinute();
		this.endD = epopteia.getEnds().getDayOfMonth();
		this.endM = epopteia.getEnds().getMonth();
		this.endY = epopteia.getEnds().getYear();
		this.endH = epopteia.getEnds().getHour();
		this.endMin = epopteia.getEnds().getMinute();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMathimaId() {
		return mathimaId;
	}

	public void setMathimaId(int mathimaId) {
		this.mathimaId = mathimaId;
	}

	public int getProgramId() {
		return programId;
	}

	public void setProgramId(int programId) {
		this.programId = programId;
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

	public int getStartH() {
		return startH;
	}

	public void setStartH(int startH) {
		this.startH = startH;
	}

	public int getStartMin() {
		return startMin;
	}

	public void setStartMin(int startMin) {
		this.startMin = startMin;
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

	public int getEndH() {
		return endH;
	}

	public void setEndH(int endH) {
		this.endH = endH;
	}

	public int getEndMin() {
		return endMin;
	}

	public void setEndMin(int endMin) {
		this.endMin = endMin;
	}
	
	public static EpopteiaInfo wrap(Epopteia epopteia) {
		return new EpopteiaInfo(epopteia);
	}
	
	public static List<EpopteiaInfo> wrap(List<Epopteia> epopteies) {

		List<EpopteiaInfo> epopteiaInfoList = new ArrayList<>();

		for (Epopteia epopteia : epopteies) {
			epopteiaInfoList.add(new EpopteiaInfo(epopteia));
		}

		return epopteiaInfoList; 

	}

	public Epopteia getEpopteia(EntityManager em) {

		Epopteia epopteia = null;

		if (id != 0) {
			epopteia = em.find(Epopteia.class, id);
		} else {
			epopteia = new Epopteia();
		}

		epopteia.setStarts(new SimpleCalendar(startY,startM,startD,startH,startMin));
		epopteia.setEnds(new SimpleCalendar(endY,endM,endD,endH,endMin));
		
		ProgramService ps = new ProgramService(em);
		epopteia.setProgram(ps.findProgramById(programId));
		
		MathimaService ms = new MathimaService(em);
		epopteia.setMathima(ms.findMathimaById(mathimaId));

		return epopteia;
	}
}
