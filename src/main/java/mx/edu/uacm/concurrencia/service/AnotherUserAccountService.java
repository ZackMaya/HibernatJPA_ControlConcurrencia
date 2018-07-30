package mx.edu.uacm.concurrencia.service;

import mx.edu.uacm.concurrencia.domain.optimistic.AnotherUserAccount;
import mx.edu.uacm.concurrencia.domain.pessimistic.UserAccount;

public interface AnotherUserAccountService {

	void insertAnotherUserAccount(AnotherUserAccount anotherUser);
	
	AnotherUserAccount buscarPorId(Class<AnotherUserAccount> clase, Object o);
	
	void insertUserAccount(UserAccount userAccount);
	
	UserAccount buscarPorIdUserAccount(Class<UserAccount> clase, long o);
	
}
