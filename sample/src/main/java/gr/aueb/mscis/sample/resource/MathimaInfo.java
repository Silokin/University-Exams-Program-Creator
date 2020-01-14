package gr.aueb.mscis.sample.resource;

import java.util.ArrayList;

import java.util.List;


import gr.aueb.mscis.sample.model.Mathima;
import javax.persistence.EntityManager;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class MathimaInfo {

	private Integer id;
	
	private String title;
	
	private int semester;
	
	private String teacher;
	
	
	public MathimaInfo() {
	}
	
	public MathimaInfo(Integer id, String title, int semester, String teacher) {
		this(title, semester, teacher);
		this.id = id;
 
	} 
	
	public MathimaInfo(String title, int semester, String teacher) {
		super();
		this.title = title;
		this.semester = semester;
		this.teacher = teacher;
	}
	
	public MathimaInfo(Mathima mathima) {
		id = mathima.getId();
		title = mathima.getTitle();
		semester = mathima.getSemester();
		teacher = mathima.getTeacher();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public static MathimaInfo wrap(Mathima m) {
		return new MathimaInfo(m);
	}

	public static List<MathimaInfo> wrap(List<Mathima> mathimata) {

		List<MathimaInfo> mathimaInfoList = new ArrayList<>();

		for (Mathima m : mathimata) {
			mathimaInfoList.add(new MathimaInfo(m));
		} 

		return mathimaInfoList;

	}
	
	public Mathima getMathima(EntityManager em) {

		Mathima mathima = null;

		if (id != null) {
			mathima = em.find(Mathima.class, id);
		} else {
			mathima = new Mathima();
		}
		mathima.setTitle(title); 
		mathima.setSemester(semester);
		mathima.setTeacher(teacher);
		
		return mathima;
	}
	
}
