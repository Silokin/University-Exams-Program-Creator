package gr.aueb.mscis.sample.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "mathima")
public class Mathima {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "title", length = 512, nullable = false)
	private String title;

	@Column(name = "semester", nullable = false)
	private int semester;

	@Column(name = "teacher", length = 100, nullable = false)
	private String teacher;
	
	@OneToOne
	private Epopteia epopteia;
	
	public Mathima() {
	}

	public Mathima(String title, int semester, String teacher) {
		super();
		this.title = title;
		this.semester = semester;
		this.teacher = teacher;
	}

	
	public int getId() {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result+ ((teacher == null) ? 0 : teacher.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + semester;
		return result;
	}

	//
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		Mathima other = (Mathima) obj;
		
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
		if (semester != other.semester)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Mathima [id=" + id + ", title=" + title + ", semester=" + semester + ", teacher=" + teacher + ", epopteia =" 
				+epopteia + "]";
	}

	
	
}
