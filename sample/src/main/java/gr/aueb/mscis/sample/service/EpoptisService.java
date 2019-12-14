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
		
		

}
