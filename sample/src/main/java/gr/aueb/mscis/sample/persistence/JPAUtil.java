package gr.aueb.mscis.sample.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

    private static EntityManagerFactory emf;
    private static final ThreadLocal<EntityManager> currentEntityManager = new ThreadLocal<EntityManager>();

    
    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("sample");
        }
        return emf;
    }
    
    
    public static EntityManager getCurrentEntityManager() {      
        EntityManager em = currentEntityManager.get();         
        if (em  == null || !em.isOpen()) {
            em = getEntityManagerFactory().createEntityManager();
            currentEntityManager.set(em);
        }
        return em;
    }
    
    public static EntityManager createEntityManager() {
    	
    	return getEntityManagerFactory().createEntityManager();
    }

   
}
