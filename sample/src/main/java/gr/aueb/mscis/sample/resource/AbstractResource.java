package gr.aueb.mscis.sample.resource;

import javax.persistence.EntityManager;

import gr.aueb.mscis.sample.persistence.JPAUtil;

public class AbstractResource {

	protected EntityManager getEntityManager() {

		return JPAUtil.getCurrentEntityManager();

	}

}

	