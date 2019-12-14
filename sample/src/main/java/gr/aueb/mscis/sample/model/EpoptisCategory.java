package gr.aueb.mscis.sample.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Columns;

import gr.aueb.mscis.sample.util.SimpleCalendar;

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
    private Integer id; 
    
    @Column(name="description", length = 50, nullable = false)
    private String description;
    
    @Column(name="epopteies")
    private int maxEpopteies;
    
    /**
     * Ο προκαθορισμένος κατασκευαστής.
     */
    public EpoptisCategory() { }

    /** 
     * Βοηθητικός κατασκευαστής.
     * Aρχικοποιεί τα βασικά στοιχεία της κατηγορίας δανειζομένου.
     * @param description Περιγραφή κατηγορίας
     * @param maxLendingDays Μέγιστος αριθμός ημερών δανεισμού
     * @param maxLendingItems Μέγιστος αριθμός αντιτύπων
     * προς (ταυτόχρονο) δανεισμό
     * @param dailyFine Ημερήσιο πρόστιμο καθυστέρησης
     */
    public EpoptisCategory(String description, int maxEpopteies) {

    	this.description = description; 
        this.maxEpopteies = maxEpopteies;
    }


    public Integer getId() {
        return id;
    }
    
    
    /**
     * Θέτει την περιγραφή της κατηγορίας δανειζομένου.
     * @param description Η περιγραφή της κατηγορίας δανειζομένου.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Επιστρέφει την περιγραφή της κατηγορίας δανειζομένου.
     * @return Η κατηγορία δανειζομένου.
     */
    public String getDescription() {
        return description;
    }


    /**
     * Επιστρέφει τον μέγιστο αριθμό ημερών δανεισμού
     * ενός αντιτύπου για την κατηγορία δανειζομένου.
     * @return Ο μέγιστος αριθμός ημερών δανεισμού.
     */
    public int getMaxEpopteies() {
        return maxEpopteies;
    }
    
    public void setMaxEpopteies(int maxEpopteies)
    {
    	this.maxEpopteies = maxEpopteies;
    }

    /**
     * Επιστρέφει {@code true} εάν ο δανειζόμενος
     * της συγκεκριμένης κατηγορίας δανειζομένου
     * Μπορεί να δανειστεί κάποιο αντίτυπο.
     * @param pendingItems Ο αριθμός των αντιτύπων
     * που έχουν δανειστεί και δεν έχουν επιστραφεί.
     * @return {@code true} εάν μπορεί να δανειστεί κάποιο αντίτυπο.
     */
    public boolean canEpopteuseiBasedOnMaxEpopteies(int pendingEpopteies) {
        return maxEpopteies > pendingEpopteies;
    }
    
//    //prwta
//    epoptis.available();
//    //kathe fora pou ginetai set mias epopteias elegxw
//    if (canEpopteusei && epoptis.getState() != EpoptisState.UNAVAILABLE)
//    	epopteia.add(epoptis)
//    else
//    	epoptis.unavailable()	
}
