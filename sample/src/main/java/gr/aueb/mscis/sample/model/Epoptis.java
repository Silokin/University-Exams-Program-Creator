package gr.aueb.mscis.sample.model;

import gr.aueb.mscis.sample.contacts.*;
import gr.aueb.mscis.sample.exceptions.EpoptisException;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


//epishs prepei na vroume akri me tis meres mi diathesimotitas
//epishs prepei na ginetai ADD ena epoptis se epopteia
//MONO an den einai FULL, me vasi to category
//kai na mi sympeftei me tis meres mi diathesimotitas??


/**
 * Οι επόπτες που αναλαμβάνουν εποπτείες μαθημάτων
 * @author MscIS-AlexGianTas
 */
@Entity
@Table(name = "epoptis")
public class Epoptis {

	@Id
    @Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO) //generate automatically
    private Integer id;
	
	@Column(name="name", length=50, nullable = false)
	private String name;
	
	@Column(name="surname", length=50, nullable = false)
	private String surname;

	@Enumerated(EnumType.STRING)
    @Column(name="epoptisstate")
    private EpoptisState state = EpoptisState.AVAILABLE;
	
	@org.hibernate.annotations.Type(
            type="gr.aueb.mscis.sample.persistence.TelphoneNumberCustomType")
    @Column(name="telephone")
    private TelephoneNumber telephone;
    
    @org.hibernate.annotations.Type(
            type="gr.aueb.mscis.sample.persistence.EMailCustomType")
    @Column(name="email")
    private EmailAddress email;
    
    //prepei na einai perissotero apo 6 digits and not contains special symbols(optional)
    /*@org.hibernate.annotations.Type(
            type="com.mgiandia.library.persistence.PasswordCustomType")
    @Column(name="password")
    private Password password; */
    
    @Column(name="password", length=25, nullable=false)
    private String password;
    
    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY )
    @JoinColumn(name="categoryid")
    private EpoptisCategory category;
    
    
   @OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE},
		   mappedBy="epoptis", fetch=FetchType.LAZY)
   private Set<MiDiathesimotita> midiathesimotita = new HashSet<MiDiathesimotita>();
    
    //polloi epoptes exoun, pithanon, polles hmeres mh diathesimotitas 
    @ManyToMany(mappedBy="epoptis",fetch=FetchType.LAZY, 
            cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Set<Epopteia> epopteia = new HashSet<Epopteia>();
    
    /*@ManyToMany(mappedBy="epoptis",fetch=FetchType.LAZY, 
            cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Set<Epopteia> epopteies = new HashSet<Epopteia>();
    //μεσω της γραμματειας θα κανει set και θα τις προσθετει στου αντιστοιχου εποπτη το set εποπτειες*/
    
	public Epoptis() {
	}

	public Epoptis(String name, String surname, EmailAddress email, TelephoneNumber telephone, String password) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.telephone = telephone;
		this.password = password;
		
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
	
	public String getSurName()
	{
		return surname;
	}
	
	public void setSurName(String surname)
	{
		this.surname = surname;
	}
	
	public void setTelephone(TelephoneNumber telephone) {
        this.telephone = telephone;
    }

    /**
     * Επιστρέφει τον αριθμό τηλεφώνου του δανειζομένου.
     * @return Ο αριθμός τηλεφώνου
     */
    public TelephoneNumber getTelephone() {
        return telephone;
    }

    /**
     * Θέτει το e-mail του δανειζομένου.
     * @param eMail Το e-mail του δανειζομένου
     */
    public void setEmail(EmailAddress email) {
        this.email = email;
    }

    /**
     * Επιστρέφει το e-mail του δανειζομένου.
     * @return Το e-mail του δανειζομένου
     */
    public EmailAddress getEmail() {
        return email;
    }
    
    public void setCategory(EpoptisCategory category)
    {
    	this.category = category;
    }
    
    public EpoptisCategory getCategory()
    {
    	return category;
    }
    
	//epistrefei antigrafo kai oxi to original. auto symbainei dioti theloume na apokleisoume to gegonos
	//na ginoun allages stin lista me ti mi diathesimotita me kostos tin apwleia dedomenwn
	
	//auti ti lista tin ehei o GRAMMATEAS
	//gia na tin parei arkei na ?????????????
	//brei ton sugkekrimeno epopti pou thelei mesw tou antikeimenou kai ystera auti ti method
	public Set<MiDiathesimotita> getMiDiathesimotita() {
        return new HashSet<MiDiathesimotita>(midiathesimotita); //ayto einai antigrafo tis arxikis listas
    }
    /**
     * Απομακρύνει μια ημερομηνία από τη συλλογή των μη διαθεσιμοτήτων του επόπτη.    
     * @param date Η ημερομηνία
     */
    public void removeMiDiathesimotita(MiDiathesimotita date) {
        if (date != null)
            this.midiathesimotita.remove(date);
    }
    
    /**
     * Προσθέτει μια ημερομηνία στη συλλογή των μη διαθεσιμοτήτων του επόπτη.    
     * @param date Η ημερομηνία
     */
	public void addMiDiathesimotita(MiDiathesimotita date) {
        if (date != null)
        	this.midiathesimotita.add(date); //sti mi diathesimotita autou tou epopti vale auti tin imerominia
    }
	
	
	public Set<Epopteia> getEpopteies() {
	        return new HashSet<Epopteia>(epopteia);
	}
	
	public void addEpopteia(Epopteia epopteia) {
	        if (epopteia != null && canEpopteusei() && canAddEpopteia(epopteia)) {
	            epopteia.friendEpoptis().add(this);
	            this.epopteia.add(epopteia);
	        }
	}
	 
	public void removeEpopteia(Epopteia epopteia) {
	        if (epopteia != null) {
	            epopteia.friendEpoptis().remove(this);
	            this.epopteia.remove(epopteia);
	        }
	 }
	 
	 public Set<Epopteia> friendEpopteia()
	 {
		 return epopteia;
	 }
	 
	 /**
	     * Επιστέφει {@code true} αν ο δανειζόμενος μπορεί να δανειστεί κάποιο αντίτυπο.
	     * Το αν ο δανειζόμενος μπορεί να δανειστεί κάποιο
	     * αντίτυπο εξαρτάται από το μέγιστο αριθμό αντιτύπων
	     * που μπορεί να δανειστεί και από τον αριθμό των
	     * αντιτύπων που έχει δανειστεί και δεν έχει επιστρέψει
	     * Επιστρέφει {@code false} εάν η κατηγορία δανειζομένου
	     * είναι {@code null}
	     * @return {@code true} εάν ο δανειζόμενος μπορεί
	     * να δανειστεί κάποιο αντίτυπο.
	     */
	 
	 /**
	     * Επιστρέφει τον αριθμό των εκκρεμών αντιτύπων του δανειζομένου.
	     * Είναι ο αριθμός αντιτύπων που έχει δανειστεί ο δανειζόμενος 
	     * και δεν έχει επιστρέψει.
	     * @return Ο αριθμός των αντιτύπων που δεν έχουν επιστραφεί.
	     */
	    private int countPendingEpopteies() {
	    	//poses einai oi epopteies
	        return epopteia.size();
	    }
	    
	    public boolean canEpopteusei() {
	        int pendingEpopteies;

	        if (getCategory() == null)
	            return false;
	        pendingEpopteies = countPendingEpopteies();
	        return getCategory().canEpopteuseiBasedOnMaxEpopteies(pendingEpopteies);
	    }
	    
	    public boolean canAddEpopteia(Epopteia epopteia) {
        	boolean check = true;
        	//gia tis imeres pou den mporei na epopteusei
        	for(MiDiathesimotita md : getMiDiathesimotita()) {
        		//an isodynamoun me tin sugkekrimeni epopteia pou paei na tou ekxwrithei
        		//tote check = true
        		if(md.getDate().getYear()==epopteia.getStarts().getYear() 
        		   && md.getDate().getMonth()==epopteia.getStarts().getMonth()
        		   && md.getDate().getDayOfMonth()==epopteia.getStarts().getDayOfMonth()) {
        			check = false;
        			break;
        		}		
        	}
        	//pare tis epopteies tou sigkekrimenou epopti
        	//
        	for (Epopteia teia : getEpopteies()) {
        		check=epopteia.interval(teia);
        		if(check == false) break;	
        	}
        	return check;
	    }
	   
	    public EpoptisState getState()
	    {
	    	return state;
	    }
	    
	    //den mporei o opoiosdipote na allaksei tin katastasi tou epopti
	    protected void setState(EpoptisState state)
	    {
	    	this.state = state;
	    }
	    
	    /**
	     * Αλλάζει την κατάσταση του αντιτύπου σε διαθέσιμο ({@code AVAILABLE}).
	     */
	    public void available() {
	        if (getState().equals(EpoptisState.UNAVAILABLE)) {
	            throw new EpoptisException();
	        }

	        setState(EpoptisState.AVAILABLE);
	    }
	    
	    public void unavailable()
	    {
	    	if (getState().equals(EpoptisState.AVAILABLE))
	    		throw new EpoptisException();
	    	
	    	setState(EpoptisState.UNAVAILABLE);
	    }
	    
	@Override
	public String toString() {
		return "Epoptis [id=" + id + ", name=" + name + ", surname=" + surname + ", email=" + email + ", password=" 
	+ password + ", telephone=" + telephone + "]";
	}

	
	
}
