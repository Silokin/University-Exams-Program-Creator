package gr.aueb.mscis.sample.model;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

import gr.aueb.mscis.sample.contacts.EmailAddress;
import gr.aueb.mscis.sample.contacts.TelephoneNumber;
import gr.aueb.mscis.sample.model.Epopteia;
import gr.aueb.mscis.sample.util.SimpleCalendar;
import gr.aueb.mscis.sample.model.MiDiathesimotita;


public class MiDiathesimotitaTest {
	
	MiDiathesimotita miDiathesimotita;
	Epopteia epopteia;
	SimpleCalendar simpleCalendar;
	Epoptis epoptis;
	
	@Before
    public void setUp() {
		EmailAddress email = new EmailAddress("vas@gmail.com");
	    TelephoneNumber telephone = new TelephoneNumber("6944587456");
	 	Epoptis epoptis = new Epoptis("mal","val",email,telephone,"123");
        miDiathesimotita = new MiDiathesimotita(new SimpleCalendar(2020,1,12,11,2)); 
	}
	
	


	@Test
    public void testMiDiathesimotita() {
		EmailAddress email = new EmailAddress("vas@gmail.com");
	    TelephoneNumber telephone = new TelephoneNumber("6944587456");
	 	Epoptis epoptis = new Epoptis("mal","val",email,telephone,"123");
        epoptis.getMiDiathesimotita();
        MiDiathesimotita expected = new MiDiathesimotita(new SimpleCalendar(2020,1,12,11,0));
        assertFalse(epoptis.getMiDiathesimotita().contains(expected));

    }
 
	@Test 
    public void testEpoptis() {
		EmailAddress email = new EmailAddress("vas@gmail.com");
	    TelephoneNumber telephone = new TelephoneNumber("6944587456");
	 	Epoptis epoptis = new Epoptis("mal","val",email,telephone,"123");
		 miDiathesimotita.setEpoptis(epoptis);
	     assertNotNull(miDiathesimotita.getEpoptis());

    }
 
//	@Test
//    public void testMiDiathesimotita2() {
//		
//		SimpleCalendar simpleCalendar = new SimpleCalendar(2020,1,12,19,0);
//        miDiathesimotita.setDate(simpleCalendar);
//        miDiathesimotita.
//        epopteia.setProgram(programe);
//
//        assertNotNull(epopteia.getEpoptes());
//		
//    }
 
	@Test
    public void testMiDiathesimotita3() {
		
		SimpleCalendar simpleCalendar = new SimpleCalendar(2020,1,12,19,0);
        miDiathesimotita.setDate(simpleCalendar);
        
        assertNotNull(miDiathesimotita.getDate());
		
    }
}