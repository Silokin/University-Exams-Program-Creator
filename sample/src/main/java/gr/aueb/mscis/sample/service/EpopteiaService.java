package gr.aueb.mscis.sample.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import gr.aueb.mscis.sample.exceptions.EpoptisException;
import gr.aueb.mscis.sample.model.Epoptis;
import gr.aueb.mscis.sample.util.SimpleCalendar;


public class EpopteiaService {

	private Epoptis epoptis;
	private EntityManager em;

	public EpopteiaService(EntityManager em) {
		this.em = em;
	}
	
	public SimpleCalendar ekxwrhshEpopteias(Epoptis epoptis) {
		if (epoptis == null) {
			throw new EpoptisException();
		}
		if (!epoptis.canEpopteusei()) {
			return null;
		}

		EntityTransaction tx = em.getTransaction();
		tx.begin();
//		
//		//find sugkekrimeni epopteia???
//		//ekxwrhshEpopteias se epopti
//		Item item = em.find(Item.class, itemNo);
//		Loan loan = item.borrow(borrower);
//
//		em.persist(loan);
//		tx.commit();
//		return loan.getDue();
		return null;

	}
}
