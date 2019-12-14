package gr.aueb.mscis.sample.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

import gr.aueb.mscis.sample.model.Epopteia;
import gr.aueb.mscis.sample.model.EpoptisCategory;

public class EpoptisCategoryService {

	private EntityManager em;

	public EpoptisCategoryService(EntityManager em) {
		this.em = em;
	}
	
	//create h update
	public void saveEpoptisCategory(EpoptisCategory category) {

		EntityTransaction tx = em.getTransaction();
		tx.begin();
		if (category.getId() != null) {
			em.merge(category);
		} else {
			em.persist(category);
		}
		tx.commit();
	}
	
	//delete
	public boolean deleteEpopteia(EpoptisCategory category) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		if (category != null) {
			em.remove(category);
			return true;
		}
		tx.commit();
		return false;

	}
	
	//find
	public EpoptisCategory findEpoptisCategoryById(int id) {

		EntityTransaction tx = em.getTransaction();
		tx.begin();
		EpoptisCategory category = null;
		try {
			category = em.find(EpoptisCategory.class, id);
			tx.commit();
		} catch (NoResultException ex) {
			tx.rollback();
		}
		return category;
	}
}
