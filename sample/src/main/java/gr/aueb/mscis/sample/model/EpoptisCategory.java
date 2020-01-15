package gr.aueb.mscis.sample.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
* Η κατηγορία επόπτη.
* @authorMscIS-AlexGianTas
*
*/
@Entity
@Table(name="epoptiscategory")
public class EpoptisCategory {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id; 
    
    @Column(name="description", length = 50, nullable = false)
    private String description;
    
    //poses epopteies mporei na kanei me basi tin epopteia 
    @Column(name="epopteies")
    private Integer maxEpopteies;
    
    /**
     * Ο προκαθορισμένος κατασκευαστής.
     */
    public EpoptisCategory() { }

    /**
     * Βοηθητικός κατασκευαστής.
     * Aρχικοποιεί τα βασικά στοιχεία της κατηγορίας επόπτη.
     * @param description Περιγραφή κατηγορίας
     * @param maxEpopteies Μέγιστος αριθμός εποπτειών
     */
    public EpoptisCategory(String description, int maxEpopteies) {

    	this.description = description;
        this.maxEpopteies = maxEpopteies;
    }


    public Integer getId() {
        return id;
    }
    
    
    /**
     * Θέτει την περιγραφή της κατηγορίας επόπτη.
     * @param description Η περιγραφή της κατηγορίας επόπτη.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Επιστρέφει την περιγραφή της κατηγορίας επόπτη.
     * @return Η κατηγορία επόπτη.
     */
    public String getDescription() {
        return description;
    }


    /**
     * Επιστρέφει τον μέγιστο αριθμό εποπτειών ανάλογα με ητν κατηγορία επόπτη
     * @return Ο μέγιστος αριθμός εποπτειών
     */
    public int getMaxEpopteies() {
        return maxEpopteies;
    }
    
    public void setMaxEpopteies(Integer maxEpopteies)
    {
    	this.maxEpopteies = maxEpopteies;
    }

    /**
     * Επιστρέφει {@code true} εάν ο επόπτης
     * της συγκεκριμένης κατηγορίας επόπτη
     * Μπορεί να εποπτεύσει.
     * @param pendingEpopteies Ο αριθμός των εποπτειών που διαθέτει
     * @return {@code true} εάν μπορεί να εποπτεύσει.
     */
    public boolean canEpopteuseiBasedOnMaxEpopteies(int pendingEpopteies) {
        return maxEpopteies > pendingEpopteies;
    }
    
}