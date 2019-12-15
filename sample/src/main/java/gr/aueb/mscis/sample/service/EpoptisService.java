package gr.aueb.mscis.sample.service;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

import gr.aueb.mscis.sample.contacts.EmailAddress;
import gr.aueb.mscis.sample.model.Epopteia;
import gr.aueb.mscis.sample.model.Epoptis;
import gr.aueb.mscis.sample.model.EpoptisCategory;
import gr.aueb.mscis.sample.model.EpoptisState;
import gr.aueb.mscis.sample.model.MiDiathesimotita;

public class EpoptisService {
	
	private EntityManager em;
	MiDiathesimotita midiathesimotita;

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
	public Epoptis findEpoptisByMail(EmailAddress mail) {

		List<Epoptis> epoptis = null;
		epoptis = em
				.createQuery(
						"select e from Epoptis e where e.email = :mail ")
				.setParameter("mail", mail).getResultList();

		return epoptis.get(0);
	}
	
	public void saveMiDiathesimotita(MiDiathesimotita midiathesimotita)
	{
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		if (midiathesimotita.getId() != null) {
			em.merge(midiathesimotita);
		} else {
			em.persist(midiathesimotita);
		}
		tx.commit();
		//midiathesimotita.setEpoptis(epoptis.getId());
	}
	
	
	//delete
		public boolean deleteMiDiathesimotita(MiDiathesimotita midiathesimotita) {
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			if (midiathesimotita != null) {
				em.remove(midiathesimotita);
				return true;
			}
			tx.commit();
			return false;

		}
		
		//find
		@SuppressWarnings("unchecked")
		public List<MiDiathesimotita> findMiDiathesimotitaByEpoptisId(Integer epoptis_id) {

			List<MiDiathesimotita> results = null;
			results = em.createQuery("select m from MiDiathesimotita m where m.epoptisid = :id")
					.setParameter("id",epoptis_id).getResultList();
			
			return results;
		}
		
		@SuppressWarnings("unchecked")
		public List<Epopteia> findEpopteiesByEpoptisId(Epoptis epoptis)
		{
			List<Epopteia> results = null;
			results = em.createQuery("select e from Epopteia e where e.epoptis = :epoptis")
					.setParameter("epoptis",epoptis).getResultList();
			
			return results;
		}
		

		@SuppressWarnings("unchecked")
		public EpoptisState findState(Epoptis epoptis)
		{
			List<EpoptisState> results = null;
			results = em.createQuery("select e from Epoptis e where e.epoptisstate = :epoptis")
					.setParameter("epoptis",epoptis.getState()).getResultList();
			
			return results.get(0);
		}
		
		@SuppressWarnings("unchecked")
		public EpoptisCategory findCategoryOfEpoptis(Epoptis epoptis)
		{
			List<EpoptisCategory> results = null;
			results = em.createQuery("select categoryid from Epoptis e where e.id = :epoptis")
					.setParameter("epoptis",epoptis.getId()).getResultList();
			
			return results.get(0);
		}
}
