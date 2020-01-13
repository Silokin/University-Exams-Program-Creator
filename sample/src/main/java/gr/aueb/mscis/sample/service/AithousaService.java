package gr.aueb.mscis.sample.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import gr.aueb.mscis.sample.model.Aithousa;
import gr.aueb.mscis.sample.model.Mathima;

public class AithousaService {
	private EntityManager em;

	public AithousaService(EntityManager em) {
		this.em = em;
	}
	
	//create h update aithousa
	public Aithousa saveAithousa(Aithousa aithousa) {

		EntityTransaction tx = em.getTransaction();
		tx.begin();
		if (aithousa.getId() != null) {
			em.merge(aithousa);
		} else {
			em.persist(aithousa);
		}
		tx.commit(); 
		
		return aithousa;
	}
	
	//delete
	public boolean deleteAithousa(Aithousa aithousa) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		if (aithousa != null) {
			em.remove(aithousa);
			tx.commit();
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
	
	//euresi olwn twn aithouswn
	public List<Aithousa> findAllAithouses()
	{
		List<Aithousa> results = null;

		results = em.createQuery("select a from Aithousa a", Aithousa.class)
						.getResultList();

		return results;
	}
	
	//eueresi twn aithouswn me vasi ton orofo
	@SuppressWarnings("unchecked")
	public List<Aithousa> findAithousaByOrofos(String orofos)
	{
		List<Aithousa> results = null;
		results = em.createQuery("select a from Aithousa a where a.orofos = :orofos")
				.setParameter("orofos", orofos).getResultList();
				

		return results;
	}
	
	//eueresi twn aithouswn me vasi to onoma
	@SuppressWarnings("unchecked")
	public List<Aithousa> findAithousaByName(String name)
	{
		List<Aithousa> results = null;
		results = em.createQuery("select a from Aithousa a where a.name = :name")
				.setParameter("name", name).getResultList();
				

		return results;
	}
	
	//eueresi twn aithouswn me vasi to ktirio
	@SuppressWarnings("unchecked")
	public List<Aithousa> findAithousaByKtirio(String ktirio)
	{
		List<Aithousa> results = null;
		results = em.createQuery("select a from Aithousa a where a.ktirio = :ktirio")
				.setParameter("ktirio", ktirio).getResultList();
				

		return results;
	}
	
	
	
}