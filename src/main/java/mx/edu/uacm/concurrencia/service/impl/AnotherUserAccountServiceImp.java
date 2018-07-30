package mx.edu.uacm.concurrencia.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import mx.edu.uacm.concurrencia.domain.optimistic.AnotherUserAccount;
import mx.edu.uacm.concurrencia.domain.pessimistic.UserAccount;
import mx.edu.uacm.concurrencia.service.AnotherUserAccountService;

@Service
@Transactional
public class AnotherUserAccountServiceImp implements AnotherUserAccountService {

	private static final Logger log = LogManager.getLogger(AnotherUserAccountServiceImp.class);
	
	@PersistenceContext(type=PersistenceContextType.EXTENDED)
	private EntityManager em;
	
	public void insertAnotherUserAccount(AnotherUserAccount anotherUser) {
		log.debug("Entrando al metodo insertAnotherUserAccount");
		
		em.persist(anotherUser);				
	}

	
	public AnotherUserAccount buscarPorId(Class<AnotherUserAccount> clase, Object o) {
		log.debug("Entrando al metodo buscarPorId");	
		
		return em.find(clase, Long.parseLong(o.toString()));
	}


	@Override
	public void insertUserAccount(UserAccount userAccount) {
		
		log.debug("Entrando al metodo insertUserAccount");
		
		em.persist(userAccount);
	}


	@Override
	public UserAccount buscarPorIdUserAccount(Class<UserAccount> clase, long o) {
		log.debug("Entrando al metodo buscarPorIdUserAccount");	
		
		//return em.find(clase, o);
		
		//return em.find(clase, o, LockModeType.READ);
		return em.find(clase, 0, LockModeType.PESSIMISTIC_READ);
	}

}
