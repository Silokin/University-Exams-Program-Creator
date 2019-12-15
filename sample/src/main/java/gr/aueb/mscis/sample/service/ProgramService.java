package gr.aueb.mscis.sample.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

import gr.aueb.mscis.sample.model.Epoptis;
import gr.aueb.mscis.sample.model.Program;

public class ProgramService {
	
	private EntityManager em;

	public ProgramService(EntityManager em) {
		this.em = em;
	}
	
	//create h update
	public void saveProgram(Program program) {

		EntityTransaction tx = em.getTransaction();
		tx.begin();
		if (program.getId() != null) {
			em.merge(program);
		} else {
			em.persist(program);
		}
		tx.commit();
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
	
	public List<Program> findAllPrograms() {
		List<Program> results = null;

		results = em.createQuery("select p from Program p", Program.class)
				.getResultList();

		return results;
	}


}
