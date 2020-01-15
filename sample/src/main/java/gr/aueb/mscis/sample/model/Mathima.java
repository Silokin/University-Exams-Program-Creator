package gr.aueb.mscis.sample.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
* Τα μαθήματα εξεταστικής
* @author MscIS-AlexGianTas
*
*/
@Entity
@Table(name = "mathima")
public class Mathima {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "title", length = 512, nullable = false)
	private String title;

	@Column(name = "semester", nullable = false)
	private int semester;

	@Column(name = "teacher", length = 100, nullable = false)
	private String teacher;
	
	@OneToOne(mappedBy="mathima")
	private Epopteia epopteia;
	
	public Mathima() {
	}

	public Mathima(String title, int semester, String teacher) {
		super();
		this.title = title;
		this.semester = semester;
		this.teacher = teacher;
	}

	
	public Integer getId() {
		return id;
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
		this.teacher = teacher ;
	}
	
//	public void removeEpopteiaDirect() {
//		if (this.epopteia!= null) {
//			this.epopteia.removeMathimaIndirect();
//			this.epopteia = null;
//		}
//	}
//	
//	public void removeEpopteiaIndirect() {
//		if (this.epopteia!= null) {
//			this.epopteia = null;
//		}
//	}
	
	public void setEpopteia(Epopteia epopteia)
	{
		this.epopteia = epopteia;
	}
	
	public Epopteia getEpopteia()
	{
		return epopteia;
	}

//	//mono se mia ek twn duo me many to ... sxesi
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((epopteia == null) ? 0 : epopteia.hashCode());
//		result = prime * result + id;
//		result = prime * result + semester;
//		result = prime * result + ((teacher == null) ? 0 : teacher.hashCode());
//		result = prime * result + ((title == null) ? 0 : title.hashCode());
//		return result;
//	}

	//se oles tis klaseis
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mathima other = (Mathima) obj;
		if (epopteia == null) {
			if (other.epopteia != null)
				return false;
		} else if (!epopteia.equals(other.epopteia))
			return false;
		if (id != other.id)
			return false;
		if (semester != other.semester)
			return false;
		if (teacher == null) {
			if (other.teacher != null)
				return false;
		} else if (!teacher.equals(other.teacher))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Mathima [id=" + id + ", title=" + title + ", semester=" + semester + ", teacher=" + teacher + ", epopteia =" 
				+epopteia + "]";
	}

	
	
}
