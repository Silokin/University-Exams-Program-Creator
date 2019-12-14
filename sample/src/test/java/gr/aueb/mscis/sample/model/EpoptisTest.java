package gr.aueb.mscis.sample.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gr.aueb.mscis.sample.contacts.EmailAddress;
import gr.aueb.mscis.sample.contacts.TelephoneNumber;
import gr.aueb.mscis.sample.model.EpoptisCategory;
import gr.aueb.mscis.sample.model.*;
import org.junit.Before;
import org.junit.Test;

public class EpoptisTest {

	Epoptis epoptis;
	Epopteia epopteia;
	EmailAddress emailAddress;
	TelephoneNumber telephoneNumber;
	EpoptisCategory epoptiscategory;
	
	
	@Before
    public void setUp() {
        epoptis = new Epoptis();
        epopteia = new Epopteia();
        EmailAddress email = new EmailAddress("vas@gmail.com");
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
    public void testToString()
    {
      	
        String expected = "Mathima [id=0, name=null, surname=null, email=null, password=null, telephone=null]";
        assertFalse(epoptis.toString().equals(expected));
    }
	
	
}
