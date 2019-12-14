package gr.aueb.mscis.sample.model;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


import org.junit.Before;
import org.junit.Test;


import gr.aueb.mscis.sample.model.Epopteia;

import gr.aueb.mscis.sample.model.Aithousa;

public class EpoptisCategoryTest {

	Aithousa aithousa;
	Epopteia epopteia;
	EpoptisCategory epoptisCategory;
	
	
	@Before
    public void setUp() {
        epoptisCategory = new EpoptisCategory("kat",3);  
	}
	
	@Test
    public void testDescription() {

		epoptisCategory.setDescription("kat");
        assertTrue(epoptisCategory.getDescription().equals("kat"));

    }
	
	@Test
    public void testMaxEpopteies() {


		epoptisCategory.setMaxEpopteies(3);
        assertEquals(epoptisCategory.getMaxEpopteies(),3);

    }
 
}


