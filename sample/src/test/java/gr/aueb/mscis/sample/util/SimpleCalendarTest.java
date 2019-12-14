package gr.aueb.mscis.sample.util;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

import gr.aueb.mscis.sample.util.BasicEqualTester;
import gr.aueb.mscis.sample.util.SimpleCalendar;

public class SimpleCalendarTest {

	

	    @Test
	    public void creation() {
	        SimpleCalendar march_1_2007 = new SimpleCalendar(2007, 3 , 1 , 3 , 4);
	        assert1stMarch2007(march_1_2007);
	    }    
	    
	    
	    @Test
	    public void creationFormCalendar() {
	        Calendar date = Calendar.getInstance();
	        date.set(2007,Calendar.MARCH,1);
	        SimpleCalendar march_1_2007 = new SimpleCalendar(date);
	        assert1stMarch2007(march_1_2007);
	    }
	    
	    @Test
	    public void preserveDateInvirant() {
	        SimpleCalendar date = new SimpleCalendar(2007, 2, 29 , 3 , 4);
	        assert1stMarch2007(date);
	    }
	    
	     
	    @Test
	    public void addDays() {
	        SimpleCalendar date = new SimpleCalendar(2007, 2, 28 , 3 , 4);
	        SimpleCalendar march_1_2007 = date.addDays(1);
	        assert1stMarch2007(march_1_2007);
	        Assert.assertFalse(date.equals(march_1_2007));
	    }
	    
	    
	    
	    @Test
	    public void beforeAndAfter() {
	        SimpleCalendar date = new SimpleCalendar(2007, 3, 1 , 3 , 4);
	    
	        Assert.assertTrue(date.before(new SimpleCalendar(2007, 3, 2 , 3 , 4)));
	        Assert.assertTrue(date.compareTo(new SimpleCalendar(2007, 3, 2 , 3 , 4)) < 0);        
	        
	        Assert.assertTrue(date.after(new SimpleCalendar(2007, 2, 28 , 3 , 4)));
	        Assert.assertTrue(date.compareTo(new SimpleCalendar(2007, 2, 28 , 3 , 4)) > 0);
	        
	        Assert.assertFalse(date.after(new SimpleCalendar(2007, 3, 1 , 3 , 4)));                       
	        Assert.assertFalse(date.before(new SimpleCalendar(2007, 3, 1 , 3 , 4)));
	        Assert.assertEquals(0, date.compareTo(new SimpleCalendar(2007, 3, 1 , 3 , 4)));        
	    }
	    
	    
	    @Test
	    public void getJavaCalendar() {
	        SimpleCalendar date = new SimpleCalendar(2007, 3, 1 , 3 , 4);
	        Calendar javaDate = date.getJavaCalendar();
	        
	        Assert.assertEquals(2007, javaDate.get(Calendar.YEAR));
	        Assert.assertEquals(Calendar.MARCH, javaDate.get(Calendar.MONTH));
	        Assert.assertEquals(1, javaDate.get(Calendar.DAY_OF_MONTH));
	    }
	    

//	    @Test
//	    public void duration() {
//	        SimpleCalendar february_28_2007 = new SimpleCalendar(2007, 2, 28 , 3 , 4);
//	        SimpleCalendar march_1_2007 = new SimpleCalendar(2007, 3, 1 , 3 , 4);
//	        
//	        Assert.assertEquals(0, february_28_2007.durationInDays(new SimpleCalendar(2007, 2, 28 , 3 , 4)));
//	        Assert.assertEquals(1, february_28_2007.durationInDays(march_1_2007));
//	        Assert.assertEquals(-1, march_1_2007.durationInDays(february_28_2007));        
//	    }
	    
	    
	    @Test
	    public void equalsAndHashCode() {
	        BasicEqualTester<SimpleCalendar> equalsTester = new BasicEqualTester<SimpleCalendar> ();
	        equalsTester.setObjectUnderTest(new SimpleCalendar(2007, 3, 1 , 3 , 4));
	        equalsTester.otherObjectIsOfDifferentType(new Object());
	        equalsTester.otherObjectIsNull();
	        equalsTester.objectsHaveDifferentState(new SimpleCalendar(2007, 3, 2 , 3 , 4));
	        equalsTester.sameReferences(equalsTester.getObjectUnderTest());
	        equalsTester.bothObjectsHaveSameState(new SimpleCalendar(2007, 3, 1 , 3 , 4));
	    }
	    
	    
	    
	    
	    private void assert1stMarch2007(SimpleCalendar date) {
	        Assert.assertEquals(2007, date.getYear() );
	        Assert.assertEquals(3,date.getMonth());
	        Assert.assertEquals(1, date.getDayOfMonth());
	    }	
	
	
	
}
