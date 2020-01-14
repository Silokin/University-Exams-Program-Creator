package gr.aueb.mscis.sample.service;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import gr.aueb.mscis.sample.contacts.EmailAddress;
import gr.aueb.mscis.sample.contacts.EmailMessage;
import gr.aueb.mscis.sample.exceptions.EpoptisException;
import gr.aueb.mscis.sample.model.Epoptis;
import gr.aueb.mscis.sample.model.Program;
import gr.aueb.mscis.sample.persistence.JPAUtil;


public class NotificationService {

	 private EmailProvider provider;
	    
	    /**
	     * Θέτει τον πάροχο του ηλεκτρονικού ταχυδρομείου.
	     * @param provider Ο πάροχος ηλεκτρονικού ταχυδρομείου.
	     */
	    public void setProvider(EmailProvider provider) {
	        this.provider = provider;
	    }

	    /**
	     * Ενημερώνει όσους δανειζομένους.
	     * έχουν καθυστερήσει της επιστροφή.
	     * κάποιου αντιτύπου.
	     */
	    @SuppressWarnings("unchecked")
		public void notifyEpoptes(Program p, int day_today, int month_today, int year_today) {
	        if (provider == null) {
	            throw new EpoptisException();
	        }

	        EntityManager em  = JPAUtil.createEntityManager();
	        EntityTransaction tx = em.getTransaction();
	        tx.begin();
	        
	        List<Epoptis> allEpoptes = em.createQuery("select e from Epoptis e")
	        	.getResultList();
	        
	        
	        
	        Calendar date = Calendar.getInstance(); 
//	        int day_today = date.get(Calendar.DAY_OF_MONTH);
//	        int month_today = date.get(Calendar.MONTH);
//	        int year_today = date.get(Calendar.YEAR);
	        
	        System.out.println(day_today);
	        
	        for (Epoptis epoptis : allEpoptes) {
	            if (epoptis.getEmail() != null && epoptis.getEmail().isValid() && day_today < p.getStarts().getDayOfMonth() && month_today < p.getStarts().getMonth()) {
	                String message = "Παρακαλώ δηλώστε ημέρες μη διαθεσιμότητας";
	                sendEmail(epoptis,"Δήλωση μη διαθεσιμότητας", message);
	            }
	        }
	        
	        tx.commit();
	        em.close();
	    }


	    private void sendEmail(Epoptis epoptis,
	            String subject, String message) {
	        EmailAddress eMail  = epoptis.getEmail();
	        if (eMail == null || !eMail.isValid()) {
	            return;
	        }
	        
	        EmailMessage emailMessage = new EmailMessage();
	        emailMessage.setTo(eMail);
	        emailMessage.setSubject(subject);
	        emailMessage.setBody(message);
	        provider.sendEmail(emailMessage);
	    }

//	    private String composeMessage() {
//	        return message;
//	    }
	    
}
