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

	//tin katastasi tou epopti
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
    
    //i katigoria epopti
    @ManyToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.LAZY )
    @JoinColumn(name="categoryid")
    private EpoptisCategory category;
    
   //oi meres mi diathesimotitas
   @OneToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE},
		   mappedBy="epoptis", fetch=FetchType.LAZY)
   private Set<MiDiathesimotita> midiathesimotita = new HashSet<MiDiathesimotita>();
    
   //oi epopteies tou
    @ManyToMany(mappedBy="epoptis",fetch=FetchType.LAZY, 
            cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Set<Epopteia> epopteia = new HashSet<Epopteia>();
    
    
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
     * Επιστρέφει τον αριθμό τηλεφώνου του επόπτη.
     * @return Ο αριθμός τηλεφώνου
     */
    public TelephoneNumber getTelephone() {
        return telephone;
    }

    /**
     * Θέτει το e-mail του επόπτη.
     * @param eMail Το e-mail του επόπτη
     */
    public void setEmail(EmailAddress email) {
        this.email = email;
    }

    /**
     * Επιστρέφει το e-mail του επόπτη.
     * @return Το e-mail του επόπτη
     */
    public EmailAddress getEmail() {
        return email;
    }
    
    public String getPassword()
	{
		return password;
	}
	public void setPassword(String pass)
	{
		this.password = pass;
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
	public Set<MiDiathesimotita> getMiDiathesimotita() {
        return new HashSet<MiDiathesimotita>(midiathesimotita); 
        //ayto einai antigrafo tis arxikis listas
    }
    /**
     * Απομακρύνει μια ημερομηνία από τη συλλογή των μη διαθεσιμοτήτων του επόπτη.    
     * @param date Η ημερομηνία
     */
    public void removeMiDiathesimotita(MiDiathesimotita date) {
        if (date != null)
        {
        	date.setEpoptis(null);
            this.midiathesimotita.remove(date);
        }
    }
    
    /**
     * Προσθέτει μια ημερομηνία στη συλλογή των μη διαθεσιμοτήτων του επόπτη.    
     * @param date Η ημερομηνία
     */
	public void addMiDiathesimotita(MiDiathesimotita date) {
        if (date != null)
        {
        	date.setEpoptis(this);
        	this.midiathesimotita.add(date); 
        }
        //sti mi diathesimotita autou tou epopti vale auti tin imerominia
    }
	
	
	public Set<Epopteia> getEpopteies() {
	        return new HashSet<Epopteia>(epopteia);
	}
	
	public void addEpopteia(Epopteia epopteia) {
	        if (epopteia != null) {
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
	     * Επιστρέφει τον αριθμό των εποπτειών του επόπτη.
	     * @return Ο αριθμός των εποπτειών του επόπτη
	     */
	    private int countPendingEpopteies() {
	    	//poses einai oi epopteies
	        return epopteia.size();
	    }
	    
	    /**
	     * Επιστρέφει {@code true} αν δεν εχει ξεπεράσει ο επόπτης το μέγιστο πλήθος εποπτειών 
	     * με βάση τη κατηγορία του
	     * @return {@code true} αν μπορεί να εποπτεύσει
	     */
	    public boolean canEpopteusei() { 
	        int pendingEpopteies;

	        if (getCategory() == null)
	            return false;
	        pendingEpopteies = countPendingEpopteies();
	        return getCategory().canEpopteuseiBasedOnMaxEpopteies(pendingEpopteies);
	    }
	   
	    /**
	     * Επιστρέφει {@code true} αν η ημερομηνία εποπτείας δεν αντιστοιχεί με
	     * ημέρα που έχει δηλώσει μη διαθεσιμότητα
	     * @return {@code true} αν μπορεί να εποπτεύσει
	     */
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
        	//kai check oti den sympeftei mekapoia alli epopteia
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
	    public void setState(EpoptisState state)
	    {
	    	this.state = state;
	    }
	    
	    /**
	     * Αλλάζει την κατάσταση του επόπτη σε διαθέσιμο ({@code AVAILABLE}).
	     */
	    public void available() {
	        if (getState().equals(EpoptisState.UNAVAILABLE)) {
	            throw new EpoptisException();
	        }

	        setState(EpoptisState.AVAILABLE); 
	    }
	    
	    /**
	     * Αλλάζει την κατάσταση του επόπτη σε μη διαθέσιμο ({@code UNAVAILABLE}).
	     */
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
