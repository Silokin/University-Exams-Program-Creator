package gr.aueb.mscis.sample.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import gr.aueb.mscis.sample.model.Epoptis;
import gr.aueb.mscis.sample.model.Mathima;
import gr.aueb.mscis.sample.persistence.Initializer;
import gr.aueb.mscis.sample.persistence.JPAUtil;


public class JPAQueriesTest {

	private Initializer dataHelper;

    @Before
    public void setUpJpa() {
        dataHelper = new Initializer();
        dataHelper.prepareData();
    }
    
    //4 mathima yparxoun mono
    @SuppressWarnings("unchecked")
    @Test
    public void simpleQuery() {
        int EXPECTED_ΜΑΤΗΙΜΑ_NUMBER = 4;
        EntityManager em = JPAUtil.getCurrentEntityManager();
        Query query = em.createQuery("select mathima from Mathima mathima");
        List<Mathima> results = query.getResultList();      
        Assert.assertEquals(EXPECTED_ΜΑΤΗΙΜΑ_NUMBER, results.size());
        
    }
    
    //ena mathima pou legete ΣΤΑΤΙΣΤΙΚΗ
    @SuppressWarnings("unchecked")
    @Test
    public void restrictionQuery() {
        int EXPECTED_NUMBER_STARTING_WITH_S = 1;
        EntityManager em = JPAUtil.getCurrentEntityManager();
        Query query = em.createQuery("select mathima from Mathima mathima where title like :titleCrit");
        query.setParameter("titleCrit", "Σ%");
        List<Mathima> results = query.getResultList();  
        Assert.assertEquals(EXPECTED_NUMBER_STARTING_WITH_S,results.size());
        
    }
    
    //alex is ypopsifios didaktwr
    @SuppressWarnings("unchecked")
    @Test
    public void implicitJoin() {
        int EXPECTED_YPOPSIFIOS_DIDAKTOR = 1;
        EntityManager em = JPAUtil.getCurrentEntityManager();
        Query query = em.createQuery("select e from Epoptis e where e.category.description = 'Υποψήφιος Διδάκτωρ'");        
        List<Epoptis> results = query.getResultList();  
        Assert.assertEquals(EXPECTED_YPOPSIFIOS_DIDAKTOR,results.size());
    }
    
    //tasos has epopteia
    @Test
    public void innerJoin() {
        int EXPECTED_ITEM_NUMBER = 1;
        EntityManager em = JPAUtil.getCurrentEntityManager();
        Query query = em.createQuery("select e from Epoptis e join e.epopteia ep");        
        Assert.assertEquals(EXPECTED_ITEM_NUMBER,query.getResultList().size());
    }
    
    
    //perimenw 1 epopti giati ehw ekxwrhsie mono 1 epopteia genikotera
    @Test
    public void queryFromJoinTable() {
        int EXPECTED_ITEM_NUMBER = 1;
        EntityManager em = JPAUtil.getCurrentEntityManager();
        Query query = em.createNativeQuery("select epoptis from epopteia_epoptis");        
        Assert.assertEquals(EXPECTED_ITEM_NUMBER,query.getResultList().size());
    }
    
    
    //yparxoun 3 epoptes..
    @SuppressWarnings("unchecked")
    @Test
    public void outerJoin() {
        int EXPECTED_ITEM_NUMBER = 3;
        EntityManager em = JPAUtil.getCurrentEntityManager();
        Query query = em.createQuery("select e from Epoptis e left join e.epopteia ep");        
        @SuppressWarnings("unused")
        List<Epoptis> results = query.getResultList();  
        Assert.assertEquals(EXPECTED_ITEM_NUMBER,query.getResultList().size());
    }
    
    
    
}
