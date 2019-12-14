package gr.aueb.mscis.sample.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

import com.mgiandia.library.domain.Borrower;

import gr.aueb.mscis.sample.exceptions.EpoptisException;
import gr.aueb.mscis.sample.model.Epopteia;
import gr.aueb.mscis.sample.model.Epoptis;
import gr.aueb.mscis.sample.model.Mathima;
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
		
		
		//lista me to synolo
		@SuppressWarnings("unchecked")
		public List<Epopteia> findAllEpopteiesForACertainProgramExe(Integer programid)
		{
			List<Epopteia> results = null;
			results = em.createQuery("select e from Epopteia e where e.exetastikiid = :id")
							.setParameter("id",programid).getResultList();
			
			return results;
			
			
		}
		
		@SuppressWarnings("unchecked")
		public Map<Epoptis,Epopteia> findEpopteiesEpoptwn(Integer programid)
		{
		@SuppressWarnings("rawtypes")
		List results = null;//emfanise gia kathe epopti oles tis epopteies
		results = em.createQuery("select e, ep from Epopteia e, Epoptis ep where e.exetastikiid = :id")
				.setParameter("id",programid).getResultList();
		
		return (Map<Epoptis, Epopteia>) results;
		
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

		
	
	public String anathesiEpopteias(Integer epoptisid, Integer epopteia_id) {
		if (epoptis == null) {
			throw new EpoptisException();
		}
		if (!epoptis.canEpopteusei()) {
			return null;
		}

		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		Epoptis epoptis = em.find(Epoptis.class,epoptisid);
		Epopteia epopteia = em.find(Epopteia.class, epopteia_id);
		epopteia = epopteia.ekxwrhshEpopteias(epoptis);
		
		em.merge(epopteia);
		tx.commit();
		if (epopteia != null)
			return "Enarxi:" + epopteia.getStarts().toString() +", Lixi: " +  epopteia.getEnds().toString();
		else
			return null;

	}
	
	public Boolean findEpoptis(Integer epoptis_id) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			epoptis = em.find(Epoptis.class, epoptis_id);
			tx.commit();
		} catch (NoResultException ex) {
			epoptis = null;
			tx.rollback();
		}

		return epoptis != null;
	}
	/**
	 * throws EpoptisException with the error message
	 * @param epoptisid
	 * @param epopteiaid
	 * @return
	 */
	public void dwseEpopteia_Check(Integer epopteia_id, Integer epoptis_id) {

		boolean epoptisFound = findEpoptis(epoptis_id);

		if (!epoptisFound) {
			throw new EpoptisException("Borrower with id " + epoptis_id + "  does not exist.");
		}
		
		if (anathesiEpopteias(epoptis_id,epopteia_id) == null){
			throw new EpoptisException("Epoptis with id " + epoptis_id + " den mporei na epopteusei tin sugkekrimeni epopteia.");
		}
	}
	
	
	
}
