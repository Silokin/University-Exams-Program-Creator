package gr.aueb.mscis.sample.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;


import gr.aueb.mscis.sample.exceptions.EpoptisException;
import gr.aueb.mscis.sample.model.Aithousa;
import gr.aueb.mscis.sample.model.Epopteia;
import gr.aueb.mscis.sample.model.Epoptis;
import gr.aueb.mscis.sample.model.Program;
import gr.aueb.mscis.sample.util.SimpleCalendar;


public class EpopteiaService {

	private Epoptis epoptis;
	private EntityManager em;

	public EpopteiaService(EntityManager em) {
		this.em = em;
	}
	
	//create h update
	public Epopteia saveEpopteia(Epopteia epopteia) { 

			EntityTransaction tx = em.getTransaction();
			tx.begin();
			if (epopteia.getId() != null) {
				epopteia = em.merge(epopteia);
			} else {
				em.persist(epopteia);
			}
			tx.commit();
			
			return epopteia;
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
		
		
		//lista me to synolo
		@SuppressWarnings("unchecked")
		public List<Epopteia> findAllEpopteiesForACertainProgramExe(Program program)
		{
			List<Epopteia> results = null;
			results = em.createQuery("select e from Epopteia e where e.program = :program")
							.setParameter("program",program).getResultList();
			
			return results;
			
			
		}
		
		//find
		public Epopteia findEpopteiaById(int id) {

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

		//find
		public List<Epopteia> findAllEpopteies() {

			List<Epopteia> results = null;

			results = em.createQuery("select e from Epopteia e", Epopteia.class)
					.getResultList();

			return results;
		}

		
		@SuppressWarnings("unchecked")
		public List<Epopteia> findEpopteiaByStartDate(SimpleCalendar date)
		{
			List<Epopteia> results = null;
			results =  em.createQuery("select e from Epopteia e where e.starts = :date")
					.setParameter("date",date).getResultList();
	
			
			if (!results.isEmpty())
				return results;
			else
				return null;
			
		}
		
	
	public Epopteia anathesiEpopteias(Epoptis epoptis0, Epopteia epopteia0) {
		if (epoptis0 == null) {
			throw new EpoptisException();
		}
		if (!epoptis0.canEpopteusei()) { 
			return null;
		}

		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		Epoptis epoptis = em.find(Epoptis.class,epoptis0.getId());
		Epopteia epopteia = em.find(Epopteia.class, epopteia0.getId());
		epopteia = epopteia.ekxwrhshEpopteias(epoptis);
		
		if (epopteia != null) {
			em.merge(epopteia);
			tx.commit();
			return epopteia;
			}
		else
			tx.commit();
			return null;

	}

	
//	/**
//	 * throws EpoptisException with the error message
//	 * @param epoptisid
//	 * @param epopteiaid
//	 * @return
//	 */
//	public void dwseEpopteia_Check(Epoptis epoptis0, Epopteia epopteia0) {
//
//		EpoptisService es = new EpoptisService(em);
//		Epoptis epoptis2 = null;
//		boolean epoptisFound = false;
//		//if (epoptis0 != null)
//			epoptis2 = es.findEpoptisById(epoptis0.getId()); 
//		
//		if (epoptis2 != null)
//			epoptisFound = true;
//
//		if (!epoptisFound) {
//			throw new EpoptisException("Epoptis with id " + epoptis.getId() + "  den yparxei.");
//		}
//		
//		if (anathesiEpopteias(epoptis0,epopteia0) == null){
//			throw new EpoptisException("Epoptis with id " + epoptis.getId() + " den mporei na epopteusei tin sugkekrimeni epopteia");
//		}
//	}
	
	@SuppressWarnings("unchecked")
	public List<Epoptis> findEpoptesOfEpopteia(Epopteia epopteia)
	{
		List<Epoptis> results = null;
		results = em.createNativeQuery("select epoptis from epopteia_epoptis where epopteia = :epopteia")
				.setParameter("epopteia",epopteia).getResultList();
		
		return results;
	}
	
	public void addAithousa(int idE,int idA) {
		
		Epopteia epopteia = findEpopteiaById(idE);
		
		AithousaService as = new AithousaService(em);
		Aithousa aithousa = as.findAithousaById(idA);
		
		epopteia.addAithousa(aithousa);
		epopteia = saveEpopteia(epopteia);	
	}
	
//	@Test
//	public List<MiDiathesimotita> findMiDiathesimotitaOfEpoptis()
//	{}
	
	
}
