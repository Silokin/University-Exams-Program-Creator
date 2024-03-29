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
import javax.persistence.ManyToMany;
import javax.persistence.Table;



/**
 * Οι αίθουσες μιας σχολής
 * @author MscIS-AlexGianTas
 */
@Entity
@Table(name = "aithousa")
public class Aithousa {

	@Id //primary key
    @Column(name="id") //onoma column
    @GeneratedValue(strategy = GenerationType.AUTO) //generate automatically
    private Integer id;
	
	@Column(name="name", length=50, nullable = false)
	private String name;
	
	@Column(name="orofos", length=50, nullable = false)
	private String orofos;
	
	//posi einai i xwritikotita tis aithousas
	@Column(name="noThesewn", nullable = false)
	private Integer noThesewn;
	
	//posoi epoptes xreiazontai
	@Column(name="noEpoptes", nullable = false)
	private Integer noEpoptes;
	
	@Column(name="ktirio", nullable = false)
	private String ktirio;
	
	
	//poses epopteies yparxoun
	@ManyToMany(mappedBy="aithousa",fetch=FetchType.LAZY, 
	           cascade = { CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REMOVE })
	private Set<Epopteia> epopteia = new HashSet<Epopteia>();	 
	
	/**
     * Προκαθορισμένος κατασκευαστής.
     */
	public Aithousa() {}
	
	/**
     * Βοηθητικός κατασκευαστής.
     * @param name 
     * @param orofos 
     * @param noThesewn Πόσα άτομα χωράει
     * @param noEpoptes Πόσους επόπτες χρειάζεται
     * @param ktirio Σε ποιο κτήριο γίνεται
     */
	public Aithousa(String name, String orofos, Integer noThesewn, Integer noEpoptes, String ktirio)
	{
		this.name = name;
		this.orofos = orofos;
		this.noThesewn = noThesewn;
		this.noEpoptes = noEpoptes;
		this.ktirio = ktirio;
	}
	
	public Integer getId()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getOrofos()
	{
		return orofos;
	}
	
	public void setOrofos(String orofos)
	{
		this.orofos = orofos;
	}
	
	
	public Integer getNoThesewn()
	{
		return noThesewn;
	}
	
	public void setNoThesewn(Integer noThesewn)
	{
		this.noThesewn = noThesewn;
	}
	
	
	public Integer getNoEpoptes()
	{
		return noEpoptes;
	}
	
	public void setNoEpoptes(Integer noEpoptes)
	{
		this.noEpoptes = noEpoptes;
	}
	
	
	public String getKtirio()
	{
		return ktirio;
	}
	
	public void setKtirio(String ktirio)
	{
		this.ktirio = ktirio;
	}

	/**
     * Επιστρέφει τις εποπτείες μιας αίθουσας
     * Η συλλογή των εποπτειών είναι αντίγραφο. Για την προσθήκη κάποιας εποπτείας
     * στη συλλογή χρησιμοποιείστε τη μέθοδο {@link Aithousa#addEpopteia(Epopteia)}
     * και για την  απομάκρυνση μιας εποπτείας τη
     * μέθοδο {@link Aithousa#removeEpopteia(Epopteia)}.
     * @return Αντίγραφο της συλλογής των εποπτειών της αίθουσας
     */
	public Set<Epopteia> getEpopteies() {
	        return new HashSet<Epopteia>(epopteia);
	}


	public void addEpopteia(Epopteia epopteia) {
        if (epopteia != null && this.epopteia.contains(epopteia)==false && canAddEpopteia(epopteia)) {
            epopteia.friendAithousa().add(this);
            this.epopteia.add(epopteia);
        }
}
	public boolean canAddEpopteia(Epopteia epopteia) {
    	boolean check = true;
    	for (Epopteia teia : getEpopteies()) {
    		check=teia.interval(epopteia);
    		if(check == false) break;	
    	}
    	return check;
	}
	public void removeEpopteia(Epopteia epopteia) {
	        if (epopteia != null) {
	            epopteia.friendAithousa().remove(this);
	            this.epopteia.remove(epopteia);
	        }
	 }
	 
	 public Set<Epopteia> friendEpopteia()
	 {
		 return epopteia;
	 }

	 @Override
		public String toString() {
			return "Aithousa [id=" + id + ", name=" + name + ", orofos=" + orofos + ", noThesewn=" + noThesewn + ", noEpoptes =" 
					+noEpoptes + ", ktirio="+ ktirio +"]";
		}
}