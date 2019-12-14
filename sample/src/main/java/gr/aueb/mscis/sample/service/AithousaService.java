package gr.aueb.mscis.sample.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

import gr.aueb.mscis.sample.model.Aithousa;

public class AithousaService {
	private EntityManager em;

	public AithousaService(EntityManager em) {
		this.em = em;
	}
	
	//create h update
	public void saveAithousa(Aithousa aithousa) {

		EntityTransaction tx = em.getTransaction();
		tx.begin();
		if (aithousa.getId() != null) {
			em.merge(aithousa);
		} else {
			em.persist(aithousa);
		}
		tx.commit();
	}
	
	//delete
	public boolean deleteAithousa(Aithousa aithousa) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		if (aithousa != null) {
			em.remove(aithousa);
			return true;
		}
		tx.commit();
		return false;

	}
	
	//find
	public Aithousa findAithousaById(int id) {

		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Aithousa aithousa = null;
		try {
			aithousa = em.find(Aithousa.class, id);
			tx.commit();
		} catch (NoResultException ex) {
			tx.rollback();
		}
		return aithousa;
	}
}
