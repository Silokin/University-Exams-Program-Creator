package gr.aueb.mscis.sample.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import gr.aueb.mscis.sample.contacts.EmailAddress;
import gr.aueb.mscis.sample.contacts.TelephoneNumber;
import gr.aueb.mscis.sample.model.*;
import gr.aueb.mscis.sample.util.SimpleCalendar;

import org.junit.Before;
import org.junit.Test;
public class EpoptisTest {

	Epoptis epoptis;
	Epopteia epopteia;
	EmailAddress emailAddress;
	TelephoneNumber telephoneNumber;
	EpoptisCategory epoptiscategory;
	MiDiathesimotita miDiathesimotita;
	
	@Before
    public void setUp() {
        epoptis = new Epoptis();
        epopteia = new Epopteia();
        
	}
	
	@Test
    public void testName() {

        epoptis.setName("vas");
        assertTrue(epoptis.getName().equals("vas")); 

    }
	 
	@Test
    public void testSurName() {

        epoptis.setSurName("par");
        assertTrue(epoptis.getSurName().equals("par")); 

    }
	
	@Test
    public void testEmail() {
		EmailAddress email = new EmailAddress("vas@gmail.com");
        epoptis.setEmail(email);
        assertTrue(epoptis.getEmail().equals(email)); 
 
    }
	 
	@Test
    public void testTelephoneNumber() {
		TelephoneNumber telephone = new TelephoneNumber("6944587456");
        epoptis.setTelephone(telephone);
        assertTrue(epoptis.getTelephone().equals(telephone)); 

    }
	
	@Test
    public void testEpoptisCategory() {
		EpoptisCategory category = new EpoptisCategory("did", 5);
        epoptis.setCategory(category);
        assertTrue(epoptis.getCategory().equals(category)); 

    } 
	@Test
    public void testToString() {
      	
        String expected = "Mathima [id=0, name=null, surname=null, email=null, password=null, telephone=null]";
        assertFalse(epoptis.toString().equals(expected));
    }
	
	@Test
	public void testAddEpoptia() {
		epoptis.addEpopteia(epopteia);
		epopteia.addEpopti(epoptis);
		assertTrue(epopteia.getEpoptis().contains(epoptis));
		
	}
	 
	@Test
	public void testRemoveEpoptia() {
		epoptis.removeEpopteia(epopteia);
		epopteia.removeEpopti(epoptis);
		assertFalse(epopteia.getEpoptis().contains(null));
		
	}
	 
	@Test
    public void testRemoveMiDiathesimotita() {
		EmailAddress email = new EmailAddress("vas@gmail.com");
	    TelephoneNumber telephone = new TelephoneNumber("6944587456");
	 	Epoptis epoptis = new Epoptis("mal","val",email,telephone,"123");
		miDiathesimotita = new MiDiathesimotita(new SimpleCalendar(2020,1,12,11,2)); 
        epoptis.removeMiDiathesimotita(miDiathesimotita);
        assertNotNull(epoptis.getMiDiathesimotita());

    } 
	@Test
    public void testAddMiDiathesimotita() {
		EmailAddress email = new EmailAddress("vas@gmail.com");
	    TelephoneNumber telephone = new TelephoneNumber("6944587456");
	 	Epoptis epoptis = new Epoptis("mal","val",email,telephone,"123");
		miDiathesimotita = new MiDiathesimotita(new SimpleCalendar(2020,1,12,11,2)); 
        epoptis.addMiDiathesimotita(miDiathesimotita);
        assertNotNull(epoptis.getMiDiathesimotita());

    } 
	
	@Test
    public void testcanAddEpopteia() {
		EmailAddress email = new EmailAddress("vas@gmail.com");
	    TelephoneNumber telephone = new TelephoneNumber("6944587456");
	 	Epoptis epoptis = new Epoptis("mal","val",email,telephone,"123");
	 	
        Epopteia epopteia = new Epopteia (new SimpleCalendar(2020,1,12,11,0),new SimpleCalendar(2020,1,12,15,0));
	 	epoptis.canAddEpopteia(epopteia);
        
        assertNotNull(epoptis.canAddEpopteia(epopteia));

    } 
	
	@Test
    public void testUnAvailable() {
		EmailAddress email = new EmailAddress("vas@gmail.com");
	    TelephoneNumber telephone = new TelephoneNumber("6944587456");
	 	Epoptis epoptis = new Epoptis("mal","val",email,telephone,"123");
	 	epoptis.setState(EpoptisState.UNAVAILABLE);
	 	epoptis.unavailable();
        assertTrue(epoptis.getState().equals(EpoptisState.UNAVAILABLE));
    } 
	
	@Test
    public void testavailable2() {
		EmailAddress email = new EmailAddress("vas@gmail.com");
	    TelephoneNumber telephone = new TelephoneNumber("6944587456");
	 	Epoptis epoptis = new Epoptis("mal","val",email,telephone,"123");
	 	epoptis.setState(EpoptisState.AVAILABLE);
	 	epoptis.available();
        assertTrue(epoptis.getState().equals(EpoptisState.AVAILABLE));
    } 

	
}