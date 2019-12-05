package gr.aueb.mscis.sample.persistence;

import java.security.Timestamp;
import java.util.Calendar;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import gr.aueb.mscis.sample.contacts.EmailAddress;
import gr.aueb.mscis.sample.model.Aithousa;
import gr.aueb.mscis.sample.model.Epoptis;
import gr.aueb.mscis.sample.model.EpoptisCategory;
import gr.aueb.mscis.sample.model.Mathima;
import gr.aueb.mscis.sample.model.Movie;
import gr.aueb.mscis.sample.model.ProgramExe;
import gr.aueb.mscis.sample.util.SimpleCalendar;
import gr.aueb.mscis.sample.util.SystemDate;


public class Initializer  {


    /**
     * Remove all data from database.
     * The functionality must be executed within the bounds of a transaction
     */
    public void  eraseData() {
        EntityManager em = JPAUtil.getCurrentEntityManager();
       
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query query = null;

        query = em.createNativeQuery("delete from aithousa");
        query.executeUpdate();
        query = em.createNativeQuery("delete from epopteia");
        query.executeUpdate();
        query = em.createNativeQuery("delete from epoptis");
        query.executeUpdate();
        query = em.createNativeQuery("delete from mathima");
        query.executeUpdate();
        query = em.createNativeQuery("delete from program");
        query.executeUpdate();
        query = em.createNativeQuery("delete from epoptiscategories");
        query.executeUpdate();
        query = em.createNativeQuery("delete from epopteia_aithousa");
        query.executeUpdate();
        query = em.createNativeQuery("delete from epopteia_epoptis");
        query.executeUpdate();
        
        tx.commit();
        em.close();
    }
    

    public void prepareData() {

        eraseData();                      

        Aithousa pleiades = new Aithousa("Πλειάδες", "1", 80, 2, "Ευελπίδων 1");
        Aithousa miltiades = new Aithousa("Μιλτιάδης", "1", 120, 4, "Κεντρικό");
        Aithousa kwstadopoulos = new Aithousa("Κωνσταντόπουλος", "2", 60, 1, "Τροίας 2");
        
        Mathima cscience = new Mathima("Επιστήμη των Υπολογιστών",1,"Πατσάκης");
        Mathima statistics = new Mathima("Στατιστική",3,"Τασούλας");
        Mathima crypto = new Mathima("Κρυπτογραφία",5,"Μαριάς");
        Mathima biology = new Mathima("Βιολογία",2,"Πικράκης");
        
        ProgramExe exe19_20 = new ProgramExe(new SimpleCalendar(2020,1,20,8,15),new SimpleCalendar(2020,2,20,20,15));
        
        EpoptisCategory proswpiko = new EpoptisCategory("Προσωπικό Τμήματος",5);
        EpoptisCategory ypopsifios_didaktor = new EpoptisCategory("Υποψήφιος Διδάκτωρ",3);
        
        Epoptis alex =  new Epoptis(1, "Alex", "Ath", new EmailAddress("testAlex@aueb.gr"),null,"1234");
        alex.setCategory(ypopsifios_didaktor);
        
        Epoptis giannis =  new Epoptis(2, "Giannis", "Nik", new EmailAddress("testGiannis@aueb.gr"),null,"1234");
        giannis.setCategory(proswpiko);
        
        Epoptis tasos =  new Epoptis(3, "Tasos", "Kouk", new EmailAddress("testTasos@aueb.gr"),null,"1234");
        tasos.setCategory(proswpiko);
        
        EntityManager em = JPAUtil.getCurrentEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        tx.begin();
        //edw arxizoun oi transactions
        
        
        em.persist(pleiades);
        em.persist(miltiades);
        em.persist(kwstadopoulos);
        em.persist(cscience);
        em.persist(statistics);
        em.persist(crypto);
        em.persist(biology);
        em.persist(exe19_20);
        em.persist(proswpiko);
        em.persist(ypopsifios_didaktor);
        em.persist(alex);
        em.persist(giannis);
        em.persist(tasos);
        
        //leipei na valoume orismenes meres ANOIXTES??
        //gia epopteia
        
        //edw apothikeuontai
        tx.commit();
        em.close();
    
    }
}
