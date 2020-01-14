package gr.aueb.mscis.sample.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

import gr.aueb.mscis.sample.model.Aithousa;
import gr.aueb.mscis.sample.model.Epopteia;
import gr.aueb.mscis.sample.model.Mathima;
import gr.aueb.mscis.sample.model.Program;
import gr.aueb.mscis.sample.util.SimpleCalendar;

public class ProgramService {
	 
	private EntityManager em;

	public ProgramService(EntityManager em) {
		this.em = em;
	}
	
	//create h update
	public Program saveProgram(Program program) { 

		EntityTransaction tx = em.getTransaction();
		tx.begin();
		if (program.getId() != null) {
			em.merge(program);
		} else {
			em.persist(program);
		}
		tx.commit();
		return program;
	}
	
	//delete
	public boolean deleteProgram(Program program) {
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		if (program != null) {
			em.remove(program);
			return true;
		}
		tx.commit();
		return false;

	}
	
	//find
	public Program findProgramById(int id) {

		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Program program = null;
		try {
			program = em.find(Program.class, id);
			tx.commit();
		} catch (NoResultException ex) {
			tx.rollback();
		}
		return program;
	}

	@SuppressWarnings("unchecked")
	public List<Epopteia> getListOfEpopteiesForAnExetastiki(Program p)
	{
		List<Epopteia> results = null;
		
		results =  em.createQuery("select e from Epopteia e where e.program = :program")
				.setParameter("program", p).getResultList();
		
		return results;
	}
	
//	@SuppressWarnings({ "unchecked", "null" })
//	public List<Epoptis> getListOfEpoptesInAnExetastiki(Program p)
//	{
//		List<Epopteia> results = null;
//		
//		results =  em.createQuery("select e from Epopteia e where e.program= :program")
//				.setParameter("program", p).getResultList();
//		
//		List<Epoptis> results2 = null;
////		if (results != null)
////		{
////			for (Epopteia e: results)
////		{
////			for (Epoptis ep: e.getEpoptes())
////				{
////				if (ep != null)
////					results2.add(ep);
////				}
////		}
////		
////		return results2;
////		}
////		else
//			return results;
//	}
//	
	public List<Program> findAllPrograms() {
		List<Program> results = null;

		results = em.createQuery("select p from Program p", Program.class)
				.getResultList();

		return results;
	}

	@SuppressWarnings("unchecked")
	public List<Program> findProgramByStartDate(SimpleCalendar starts)
	{
		List<Program> results = null;	
		results =  em.createQuery("select p from Program p where p.starts = :starts")
				.setParameter("starts", starts).getResultList();
		return results;
	}
	
	public Program katartisiProgrammatos(Mathima mathima, Epopteia epopteia, Aithousa aithousa, Program p)
	{
		epopteia.setMathima(mathima);
		epopteia.addAithousa(aithousa);
		epopteia.setProgram(p);
		
//		EpopteiaService ep = new EpopteiaService(em);
//		ep.saveEpopteia(epopteia);

		p.addEpopteia(epopteia);
		saveProgram(p);
		
		return p;
		
	}
}
