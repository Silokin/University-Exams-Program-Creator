package gr.aueb.mscis.sample.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

import gr.aueb.mscis.sample.contacts.EmailAddress;
import gr.aueb.mscis.sample.model.Epoptis;

public class EpoptisService {
	
	private EntityManager em;

	public EpoptisService(EntityManager em) {
		this.em = em;
	}
	
	//create h update
	public void saveEpoptis(Epoptis epoptis) {

		EntityTransaction tx = em.getTransaction();
		tx.begin();
		if (epoptis.getId() != null) {
			em.merge(epoptis);
		} else {
			em.persist(epoptis);
		}
		tx.commit();
	}
	
	//delete
	public boolean deleteEpoptis(Epoptis epoptis) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		if (epoptis != null) {
			em.remove(epoptis);
			return true;
		}
		tx.commit();
		return false;

	}
	
	//find
	public Epoptis findEpoptisById(int id) {

		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Epoptis epoptis = null;
		try {
			epoptis = em.find(Epoptis.class, id);
			tx.commit();
		} catch (NoResultException ex) {
			tx.rollback();
		}
		return epoptis;
	}
	
	@SuppressWarnings("unchecked")
	public List<Epoptis> findEpoptisByMail(EmailAddress mail) {

		List<Epoptis> results = null;
		results = em
				.createQuery(
						"select email from Epoptis e where e.email = :mail ")
				.setParameter("mail", mail).getResultList();

		return results;
	}
}
