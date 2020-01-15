package gr.aueb.mscis.sample.persistence;


import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import gr.aueb.mscis.sample.contacts.EmailAddress;
import gr.aueb.mscis.sample.contacts.TelephoneNumber; 
import gr.aueb.mscis.sample.model.Aithousa;
import gr.aueb.mscis.sample.model.Epopteia;
import gr.aueb.mscis.sample.model.Epoptis;
import gr.aueb.mscis.sample.model.EpoptisCategory;
import gr.aueb.mscis.sample.model.Mathima;
import gr.aueb.mscis.sample.model.MiDiathesimotita;
import gr.aueb.mscis.sample.model.Program;
import gr.aueb.mscis.sample.util.SimpleCalendar;


public class Initializer  {


    /**
     * Διαγραφή όλων των δεδομένων της βάσης.
     * Η λειτουργικότητα θα φανεί από την αρχικοποίηση δεδομένων που δίνουμε εμείς
     */
    public void  eraseData() {
        EntityManager em = JPAUtil.getCurrentEntityManager();
       
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query query = null;


        query = em.createNativeQuery("delete from midiathesimotita");
        query.executeUpdate();
        query = em.createNativeQuery("delete from epopteia_epoptis");
        query.executeUpdate();
        query = em.createNativeQuery("delete from epopteia_aithousa");
        query.executeUpdate();
        query = em.createNativeQuery("delete from aithousa");
        query.executeUpdate();
        query = em.createNativeQuery("delete from epoptis");
        query.executeUpdate();
        query = em.createNativeQuery("delete from epopteia");
        query.executeUpdate();
        query = em.createNativeQuery("delete from mathima");
        query.executeUpdate();
        query = em.createNativeQuery("delete from program");
        query.executeUpdate();
        query = em.createNativeQuery("delete from epoptiscategory");
        query.executeUpdate();
//        query = em.createNativeQuery("delete from epoptes_dates");
//        query.executeUpdate();
        
        tx.commit();
        em.close();
    }
    
    /**
    * Αρχικοποίηση των δεδομένων της βάσης
    *
    */
    public void prepareData() {

        eraseData();                      

        Program exe19_20 = new Program(new SimpleCalendar(2020,1,20,0,0),new SimpleCalendar(2020,2,21,0,0));
        
        Aithousa pleiades = new Aithousa("Πλειάδες", "1", 80, 2, "Ευελπίδων 1");
        Aithousa miltiades = new Aithousa("Μιλτιάδης", "1", 120, 4, "Κεντρικό");
        Aithousa kwstadopoulos = new Aithousa("Κωνσταντόπουλος", "2", 60, 1, "Τροίας 2");
        
        Mathima cscience = new Mathima("Επιστήμη των Υπολογιστών",1,"Πατσάκης");
        Mathima statistics = new Mathima("Στατιστική",3,"Τασούλας");
        Mathima crypto = new Mathima("Κρυπτογραφία",5,"Μαριάς");
        Mathima biology = new Mathima("Βιολογία",2,"Πικράκης");
        
        MiDiathesimotita mi_diath1 = new MiDiathesimotita(new SimpleCalendar(2020,1,25,0,0));
        
        EpoptisCategory proswpiko = new EpoptisCategory("Προσωπικό Τμήματος",5);
        EpoptisCategory ypopsifios_didaktor = new EpoptisCategory("Υποψήφιος Διδάκτωρ",3);
        
        Epoptis alex =  new Epoptis("Alex", "Ath", new EmailAddress("testAlex@aueb.gr"),new TelephoneNumber("6984795050"),"1234");
        alex.setCategory(ypopsifios_didaktor);
        alex.addMiDiathesimotita(mi_diath1);
        
        Epoptis giannis =  new Epoptis("Giannis", "Nik", new EmailAddress("testGiannis@aueb.gr"),new TelephoneNumber("6984796060"),"1234");
        giannis.setCategory(proswpiko);
        
        Epoptis tasos =  new Epoptis("Tasos", "Kouk", new EmailAddress("testTasos@aueb.gr"),new TelephoneNumber("6984794040"),"1234");
        tasos.setCategory(proswpiko);
        
        Epopteia epopteia1 = new Epopteia(new SimpleCalendar(2020,1,21,13,15),new SimpleCalendar(2020,1,21,15,15));
        epopteia1.setProgram(exe19_20);
        epopteia1.setMathima(biology);
        
        Epopteia epopteia2 = new Epopteia(new SimpleCalendar(2020,1,22,13,15),new SimpleCalendar(2020,1,22,15,15));
        epopteia2.setProgram(exe19_20);
        epopteia2.setMathima(crypto);
        epopteia2.addAithousa(pleiades);
        
        Epopteia epopteia3 = new Epopteia(new SimpleCalendar(2020,2,22,13,15),new SimpleCalendar(2020,2,22,15,15));
        epopteia3.setProgram(exe19_20);
        epopteia3.setMathima(statistics);
        

        epopteia1.ekxwrhshEpopteias(alex);
        
        EntityManager em = JPAUtil.getCurrentEntityManager();
        EntityTransaction tx = em.getTransaction();
   
        
        tx.begin();
        //edw arxizoun oi transactions
        
        //em.persist(mi);

        em.persist(exe19_20);
        em.persist(pleiades);
        em.persist(miltiades);
        em.persist(kwstadopoulos);
        em.persist(cscience);
        em.persist(statistics);
        em.persist(crypto);
        em.persist(biology);
        em.persist(proswpiko);
        em.persist(ypopsifios_didaktor);
        em.persist(mi_diath1);
        em.persist(alex);
        em.persist(giannis);
        em.persist(tasos);
        em.persist(epopteia1);
        em.persist(epopteia2);
        em.persist(epopteia3);
        
        

        //edw apothikeuontai
        tx.commit();
        em.close();
    
    }
}
