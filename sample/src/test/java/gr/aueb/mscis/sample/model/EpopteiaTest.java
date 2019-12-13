package gr.aueb.mscis.sample.model;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import gr.aueb.mscis.sample.model.Aithousa;
import gr.aueb.mscis.sample.model.Epopteia;
import gr.aueb.mscis.sample.model.Epoptis;
import gr.aueb.mscis.sample.model.EpoptisCategory;
import gr.aueb.mscis.sample.util.SimpleCalendar;

public class EpopteiaTest {
	
	@Test
	public void testAithousa() {
		//tsekarw add aithousa keno set
		Aithousa aithousa = new Aithousa ("gor","lami",200,2,"spike");
		Epopteia epopteia = new Epopteia (new SimpleCalendar(2020,1,12,18,0),new SimpleCalendar(2020,1,12,20,0));
		epopteia.addAithousa(aithousa);
		Assert.assertTrue(epopteia.getAithouses().contains(aithousa));
		Assert.assertTrue(aithousa.getEpopteies().contains(epopteia));
		
		//tsekarw add aithousa oxi keno set kai xwris kenes epopteies
		Aithousa aithousa1 = new Aithousa ("gor1","lami1",200,2,"spike1");
		Epopteia epopteia1 = new Epopteia (new SimpleCalendar(2020,1,12,11,0),new SimpleCalendar(2020,1,12,15,0));
		aithousa1.addEpopteia(epopteia1);
		epopteia.addAithousa(aithousa1);
		Assert.assertTrue(epopteia.getAithouses().contains(aithousa1));
		Assert.assertEquals(epopteia.getAithouses().size(),2);
		
		//add same item
		epopteia.addAithousa(aithousa);
		Assert.assertNotEquals(epopteia.getAithouses().size(),3);
		
		//tsekarw oti dn vazei ai8ousa me antikrouomenh epopteia
		Epopteia epopteia2 = new Epopteia (new SimpleCalendar(2020,1,12,19,0),new SimpleCalendar(2020,1,12,21,0));
		Aithousa aithousa2 = new Aithousa ("gor2","lami2",200,2,"spike2");
		aithousa2.addEpopteia(epopteia2);
		epopteia.addAithousa(aithousa2);
		Assert.assertEquals(epopteia.getAithouses().size(),2);	
	}
	
	@Test
	public void testEpoptis() {
		//testaroume an bhke o epoptis sthn epopteia kai to antistrofo
		Epopteia epopteia = new Epopteia();
		Epoptis epoptis = new Epoptis();
		EpoptisCategory ec = new EpoptisCategory("shalke",5);
		epoptis.setCategory(ec);
		epopteia.addEpopti(epoptis);
		Assert.assertTrue(epopteia.getEpoptes().contains(epoptis));
		Assert.assertTrue(epoptis.getEpopteies().contains(epopteia));
		//testaroume an 3eperasoume tous dia8esimous epoptes mia epopteias ti ginetai 
		
	}

}
