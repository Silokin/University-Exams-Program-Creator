package gr.aueb.mscis.sample.model;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import gr.aueb.mscis.sample.util.SimpleCalendar;
import gr.aueb.mscis.sample.contacts.EmailAddress;
import gr.aueb.mscis.sample.contacts.TelephoneNumber;
import gr.aueb.mscis.sample.model.*;

public class EpopteiaTest {
	
	Epopteia epopteia;
	Aithousa aithousa;
	SimpleCalendar simpleCalendar;
	Mathima mathima;
	
	@Test
	public void testAithousa() { 
		//tsekarw add aithousa keno set
		Aithousa aithousa = new Aithousa ("gor","lami",200,2,"spike");
		Epopteia epopteia = new Epopteia (new SimpleCalendar(2020,1,12,18,0),new SimpleCalendar(2020,1,12,20,0));
		epopteia.addAithousa(aithousa);
		assertTrue(epopteia.getAithouses().contains(aithousa));
		assertTrue(aithousa.getEpopteies().contains(epopteia));
		
		//tsekarw add aithousa oxi keno set kai xwris kenes epopteies
		Aithousa aithousa1 = new Aithousa ("gor1","lami1",200,2,"spike1");
		Aithousa aithousa2 = new Aithousa ("gor2","lami1",200,2,"spike1");
		Epopteia epopteia1 = new Epopteia (new SimpleCalendar(2020,1,12,11,0),new SimpleCalendar(2020,1,12,15,0));
		Epopteia epopteia2 = new Epopteia (new SimpleCalendar(2020,1,12,11,0),new SimpleCalendar(2020,1,12,15,0));
		aithousa1.addEpopteia(epopteia1); 
		aithousa2.addEpopteia(epopteia2);
		epopteia.addAithousa(aithousa1);
		epopteia.addAithousa(aithousa2);
		
		assertNotNull(epopteia.getAithouses().contains(aithousa1));
		//Assert.assertEquals(epopteia.getAithouses().size(),2);
		  
		//add same item 
		epopteia.addAithousa(aithousa);

		Assert.assertNotEquals(4,epopteia.getAithouses().size());

		assertEquals(3,epopteia.getAithouses().size());
		
		//tsekarw oti dn vazei ai8ousa me antikrouomenh epopteia
		Epopteia epopteia3 = new Epopteia (new SimpleCalendar(2020,1,12,19,0),new SimpleCalendar(2020,1,12,21,0));
		Aithousa aithousa3 = new Aithousa ("gor2","lami2",200,2,"spike2");
		aithousa2.addEpopteia(epopteia3);
		epopteia.addAithousa(aithousa3);

		Assert.assertEquals(3,epopteia.getAithouses().size());	
		assertEquals(epopteia.getAithouses().size(),4);	
	}
	  
	@Test 
	public void testEpoptis() {
		//testaroume an bhke o epoptis sthn epopteia kai to antistrofo
		Epopteia epopteia = new Epopteia();
		Epoptis epoptis = new Epoptis();
		EpoptisCategory ec = new EpoptisCategory("shalke",5);
		epoptis.setCategory(ec);
		epopteia.addEpopti(epoptis);
		Assert.assertTrue(epopteia.getEpoptis().contains(epoptis));
		Assert.assertTrue(epoptis.getEpopteies().contains(epopteia));
		//testaroume an 3eperasoume tous dia8esimous epoptes mia epopteias ti ginetai 
		
	}
	  
//	@Test
//    public void testRemoveAithousa() {
//
//		Aithousa aithousa = new Aithousa();
//		epopteia.addAithousa(aithousa);
//		epopteia.removeAithousa(aithousa);
//		assertEquals(epopteia.getAithouses().size(),0);
//	}
	 @Test
	    public void testToString()
	    {
		    Epopteia epopteia = new Epopteia();	
	        String expected = "Epopteia [id=0 , starts=null , ends=null]";
	        assertFalse(epopteia.toString().equals(expected));
	    }   
	 
	 @Test
	    public void testMathima(){
		    Mathima mathima = new Mathima("cas",1,"fas");
		    Epopteia epopteia = new Epopteia (new SimpleCalendar(2020,1,12,11,0),new SimpleCalendar(2020,1,12,15,0));
		    epopteia.setMathima(mathima);	
	        assertNotNull(epopteia.getMathima());
	    }   
	 
	 @Test
	 public void testsetProgram() {
        SimpleCalendar simpleCalendarstart = new SimpleCalendar(2020,1,12,19,0);
        SimpleCalendar simpleCalendarend = new SimpleCalendar(2020,1,12,19,1);
        Epopteia epopteia = new Epopteia (new SimpleCalendar(2020,1,12,11,0),new SimpleCalendar(2020,1,12,15,0));
		Program program = new Program(simpleCalendarstart,simpleCalendarend);
		epopteia.setProgram(program);
		assertNotNull(epopteia.getProgram());
	}

	 @Test
	    public void testRemoveAithousa(){
		    Aithousa aithousa = new Aithousa ("gor2","lami2",200,2,"spike2");
		    Epopteia epopteia = new Epopteia (new SimpleCalendar(2020,1,12,11,0),new SimpleCalendar(2020,1,12,15,0));
		    epopteia.removeAithousa(aithousa);	
	        assertEquals(epopteia.getAithouses().size(),0);
	    }  
	 
	 @Test
	    public void testRemoveEpopti(){
		    EmailAddress email = new EmailAddress("vas@gmail.com");
		    TelephoneNumber telephone = new TelephoneNumber("6944587456");
		 	Epoptis epoptis = new Epoptis("mal","val",email,telephone,"123");
		    Epopteia epopteia = new Epopteia (new SimpleCalendar(2020,1,12,11,0),new SimpleCalendar(2020,1,12,15,0));
		    epopteia.removeEpopti(epoptis);	
	        assertEquals(epopteia.getEpoptis().size(),0);
	    }
	 
	 @Test
	 public void testEkxwrhshEpopteias(){
		 	Epopteia epopteia = new Epopteia(new SimpleCalendar(2020,1,12,11,0),new SimpleCalendar(2020,1,12,15,0));	
		 	Epoptis epoptis = new Epoptis(); 
		    epopteia.ekxwrhshEpopteias(epoptis);
	        assertNull(epopteia.ekxwrhshEpopteias(epoptis));
	    }
	 
	 @Test
	 public void testEkxwrhshEpopteias2(){
		 	Epopteia epopteia = new Epopteia(new SimpleCalendar(2020,1,12,11,0),new SimpleCalendar(2020,1,12,15,0));	
		 	Epoptis epoptis = new Epoptis();
		 	epoptis.setState(EpoptisState.UNAVAILABLE);
	        assertNull(epopteia.ekxwrhshEpopteias(epoptis));
	    }
	 
	 
	 @Test
	 public void testEkxwrhshEpopteias3(){
		 	Epopteia epopteia = new Epopteia(new SimpleCalendar(2020,1,12,11,0),new SimpleCalendar(2020,1,12,15,0));	
		 	Aithousa aithousa = new Aithousa ("gor2","lami2",200,2,"spike2");
		 	epopteia.addAithousa(aithousa);
	        assertTrue(epopteia.doNotExceedMaxNumOfEpoptesInAithousa());
	    }
	 @Test
	 public void testEkxwrhshEpopteias4(){
		 	Epopteia epopteia = new Epopteia(new SimpleCalendar(2020,1,12,11,0),new SimpleCalendar(2020,1,12,15,0));	
	        assertFalse(epopteia.doNotExceedMaxNumOfEpoptesInAithousa());
	    }
	 
//	 @Test
//	 public void testEkxwrhshEpopteias5(){
//		 	Epopteia epopteia = new Epopteia(new SimpleCalendar(2020,1,12,11,0),new SimpleCalendar(2020,1,12,15,0));	
//		 	Aithousa aithousa = new Aithousa ("gor2","lami2",200,2,"spike2");
//		 	EmailAddress email = new EmailAddress("vas@gmail.com");
//		    TelephoneNumber telephone = new TelephoneNumber("6944587456");
//		 	Epoptis epoptis = new Epoptis("mal","val",email,telephone,"123");
//		 	epopteia.addAithousa(aithousa);
//		 	epopteia.addEpopti(epoptis);
//		 	epopteia.doNotExceedMaxNumOfEpoptesInAithousa();
//		 	epopteia.ekxwrhshEpopteias(epoptis);
//		 	assertNotNull(epopteia.ekxwrhshEpopteias(epoptis));
//	    } 
} 