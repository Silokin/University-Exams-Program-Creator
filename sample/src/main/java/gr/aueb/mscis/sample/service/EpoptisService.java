package gr.aueb.mscis.sample.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import gr.aueb.mscis.sample.contacts.EmailAddress;
import gr.aueb.mscis.sample.model.Epopteia;
import gr.aueb.mscis.sample.model.Epoptis;
import gr.aueb.mscis.sample.model.MiDiathesimotita;

public class EpoptisService {
	
	private EntityManager em;
	MiDiathesimotita midiathesimotita;

	public EpoptisService(EntityManager em) {
		this.em = em;
	}
	
	//create h update
	public Epoptis saveEpoptis(Epoptis epoptis) {

		EntityTransaction tx = em.getTransaction();
		tx.begin();
		if (epoptis.getId() != null) {
			em.merge(epoptis); 
		} else {
			em.persist(epoptis);
		}
		tx.commit();
		return epoptis;
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
		if(!epoptis.isEmpty())
			return epoptis.get(0);
		return null;
	}
	
	
	public MiDiathesimotita saveMiDiathesimotita(MiDiathesimotita midiathesimotita)
	{
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		if (midiathesimotita != null) {
			if (midiathesimotita.getId() != null) {
				em.merge(midiathesimotita);
			} else {
				em.persist(midiathesimotita);
			}
			tx.commit();
			return midiathesimotita;
		}
		else 
			return null;
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
		public List<MiDiathesimotita> findMiDiathesimotitaOfEpoptis(Epoptis epoptis) {

			List<MiDiathesimotita> results = null;
			results = em.createQuery("select m from MiDiathesimotita m where m.epoptis = :epoptis")
					.setParameter("epoptis",epoptis).getResultList();
			
			return results;
		}
		
		@SuppressWarnings("unchecked")
		public List<Epopteia> findEpopteiesEpopti(Epoptis epoptis)
		{
			List<Epopteia> results = null;
			results = em.createNativeQuery("select epopteia from epopteia_epoptis where epoptis = :epoptis")
					.setParameter("epoptis",epoptis).getResultList();
			
			return results;
		}
		
		public List<Epoptis> findAllEpoptes() {
			// TODO Auto-generated method stub
			List<Epoptis> results = null;

			results = em.createQuery("select e from Epoptis e", Epoptis.class)
					.getResultList();

			return results;
		}
		
		//giannis version
		public boolean logIn(String usr,String pass) {
			if(usr.equals("admin")) {
				if(pass.equals("qwerty")) {
					return true;
				}
			}else {
				Epoptis epoptis = findEpoptisByMail(new EmailAddress(usr));
				if (epoptis != null)
				{
					if (epoptis.getId()!=null) {
						if(epoptis.getPassword().equals(pass))
							return true;
					}
				}
			}
			return false;	
			}
		
		//alex version with test
			public boolean login(Epoptis epoptis, EmailAddress email, String password)
		{
			if (email.isValid())
			{
				if (epoptis.getEmail().equals(email) && epoptis.getPassword().equals(password))
						return true;
			}		
			return false;
			
		}
		
//		@SuppressWarnings("unchecked")
//		public EpoptisState findState(Epoptis epoptis)
//		{
//			List<EpoptisState> results = null;
//			results = em.createQuery("select e from Epoptis e where e.epoptisstate = :epoptis")
//					.setParameter("epoptis",epoptis.getState()).getResultList();
//			
//			return results.get(0);
//		}
//		
//		@SuppressWarnings("unchecked")
//		public EpoptisCategory findCategoryOfEpoptis(Integer id)
//		{
//			List<EpoptisCategory> results = null;
//			results = em.createQuery("select categoryid from Epoptis e where e.id = :epoptis")
//					.setParameter("epoptis",id).getResultList();
//			
//			return results.get(0);
//		}
}
