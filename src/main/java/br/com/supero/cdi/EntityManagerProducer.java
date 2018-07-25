package br.com.supero.cdi;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.hibernate.FlushMode;
import org.hibernate.Session;

public class EntityManagerProducer {

	@PersistenceUnit
	private EntityManagerFactory emf;

	@SuppressWarnings("deprecation")
	@Produces
	@RequestScoped
	EntityManager createEntityManager() {
		EntityManager em = emf.createEntityManager();
		em.unwrap(Session.class).setFlushMode(FlushMode.MANUAL);
		return new TransactionalEntityManager(em);
	}

	void closeEntityManager(@Disposes EntityManager em) {
		if (em.isOpen()) {
			em.close();
		}
	}

}