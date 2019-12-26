package gr.aueb.mscis.sample.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

import gr.aueb.mscis.sample.model.Mathima;


public class MathimaService {

		private EntityManager em;

		public MathimaService(EntityManager em) {
			this.em = em;
		}
		//create h update
		public Mathima saveMathima(Mathima mathima) {

			EntityTransaction tx = em.getTransaction();
			tx.begin();
			if (mathima.getId() != null) {
				em.merge(mathima);
			} else {
				em.persist(mathima);
			}
				tx.commit();
				return mathima;
		}
				
		//delete
		public boolean deleteMathima(Mathima mathima) {
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			if (mathima != null) {
				em.remove(mathima);
				return true;
			}
			tx.commit();
			return false;

			}
				
		//find
		public Mathima findMathimaById(int id) {

			EntityTransaction tx = em.getTransaction(); 
			tx.begin();
			Mathima mathima = null;
			try { 
				mathima = em.find(Mathima.class, id);
				tx.commit();
			} catch (NoResultException ex) {
				tx.rollback();
			}
			return mathima;
		}
				
		@SuppressWarnings("unchecked")
		public List<Mathima> findMathimaBySemester(int semester) {

			List<Mathima> results = null;
			results = em
					.createQuery("select m from Mathima m where m.semester = :semester ")
							.setParameter("semester", semester).getResultList();

					return results;
		}
				
		@SuppressWarnings("unchecked")
		public List<Mathima> findMathimaByTitle(String title) {

			List<Mathima> results = null;
			results = em.createQuery("select m from Mathima m where m.title = :title ")
							.setParameter("title", title).getResultList();

			return results;
		}
			

		public List<Mathima> findAllMathimata() {
			List<Mathima> results = null;

			results = em.createQuery("select m from Mathima m", Mathima.class)
							.getResultList();

			return results;
		}
		
	}


