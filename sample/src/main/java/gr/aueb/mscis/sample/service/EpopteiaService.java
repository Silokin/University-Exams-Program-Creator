package gr.aueb.mscis.sample.service; 

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

import gr.aueb.mscis.sample.exceptions.EpoptisException;
import gr.aueb.mscis.sample.model.Epopteia;
import gr.aueb.mscis.sample.model.Epoptis;
import gr.aueb.mscis.sample.util.SimpleCalendar;


public class EpopteiaService {

	private Epoptis epoptis;
	private EntityManager em;

	public EpopteiaService(EntityManager em) {
		this.em = em;
	}
	
	//create h update
	public void saveEpopteia(Epopteia epopteia) {

		EntityTransaction tx = em.getTransaction();
		tx.begin();
		if (epopteia.getId() != null) {
			em.merge(epopteia);
		} else {
			em.persist(epopteia);
		}
		tx.commit();
	}
	
	//delete
	public boolean deleteEpopteia(Epopteia epopteia) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		if (epopteia != null) {
			em.remove(epopteia);
			return true;
		}
		tx.commit();
		return false;

	}
	
	//find
	public Epopteia findEpopteia(int id) {

		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Epopteia epopteia = null;
		try {
			epopteia = em.find(Epopteia.class, id);
			tx.commit();
		} catch (NoResultException ex) {
			tx.rollback();
		}
		return epopteia;
	}


}
