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
		public void saveMathima(Mathima mathima) {

			EntityTransaction tx = em.getTransaction();
			tx.begin();
			if (mathima.getId() != null) {
				em.merge(mathima);
			} else {
				em.persist(mathima);
			}
			tx.commit();
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
					.createQuery(
							"select title from Mathima m where m.semester = :semester ")
					.setParameter("semester", semester).getResultList();

			return results;
		}
		
		@SuppressWarnings("unchecked")
		public List<Mathima> findMathimaByTitle(String title) {

			List<Mathima> results = null;
			results = em
					.createQuery(
							"select title from Mathima m where m.title = :title ")
					.setParameter("title", title).getResultList();

			return results;
		}
	/*
		public Borrower createBorrower(Map<String, String> data) {
			Borrower b = new Borrower();

			try {
				b.setBorrowerNo(Integer.valueOf(data
						.get(BorrowerInfo.BORROWERNO_KEY)));
				b.setFirstName(data.get(BorrowerInfo.FIRSTNAME_KEY));
				b.setLastName(data.get(BorrowerInfo.LASTNAME_KEY));
				b.setEmail(data.get(BorrowerInfo.EMAIL_KEY));
				b.setTelephone(data.get(BorrowerInfo.TELEPHONE_KEY));

				return b;
			} catch (Exception e) {
				return null;
			}

		}
	*/
//		public Mathima findMathimaById(int id) {
//			return em.find(Mathima.class, id);
//		}
//
//		public boolean saveOrUpdateMathima(Mathima m) {
//
//			if (m != null) {
//				em.merge(m);
//				return true;
//			}
//
//			return false;
//		}
//
//		/**
//		 * Creates a new borrower instance in the database
//		 * @param b
//		 * @return
//		 */
//		public boolean createMathima(Mathima m) {
//
//			if (m != null) {
//				em.persist(m);
//				return true;
//			}
//
//			return false;
//		}
//		
//		public boolean deleteMathima(Mathima m) {
//
//			if (m != null) {
//				em.remove(m);
//				return true;
//			}
//
//			return false;
//		}

		public List<Mathima> findAllMathimata() {
			List<Mathima> results = null;

			results = em.createQuery("select m from Mathima m", Mathima.class)
					.getResultList();

			return results;
		}
	}


