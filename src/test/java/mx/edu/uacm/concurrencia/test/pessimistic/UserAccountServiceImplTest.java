package mx.edu.uacm.concurrencia.test.pessimistic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.task.TaskExecutor;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mx.edu.uacm.concurrencia.HibernatJpaControlConcurrenciaApplication;
import mx.edu.uacm.concurrencia.domain.pessimistic.UserAccount;
import mx.edu.uacm.concurrencia.service.AnotherUserAccountService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=HibernatJpaControlConcurrenciaApplication.class)
public class UserAccountServiceImplTest {

	private static final Logger log = LogManager.getLogger(UserAccountServiceImplTest.class);
	
	@Autowired
	private AnotherUserAccountService anotherUserAccountService;
	
	@Autowired
	private TaskExecutor taskExecutor;
	
	@Test
	public void testGuardarUserAccountThreads() {
		log.debug("Entrando a Test testGuardarUserAccountThreads");
		
		UserAccount user = new UserAccount();
		
		user.setId(1);
		user.setBalance(600);
		user.setName("Jenny");
		
		anotherUserAccountService.insertUserAccount(user);
	
		//Primer hilo
		taskExecutor.execute(cambioDeSaldoHilo(700, 1));
		//Segundo hilo
		taskExecutor.execute(cambioDeSaldoHilo(800, 1));
	}
	
	public Runnable cambioDeSaldoHilo(final double nuevoBalance, final long sleepTime) {
		//Clase interna anonima
		return new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				log.debug("Dentro de run()");
				
				UserAccount userAccount = anotherUserAccountService.buscarPorIdUserAccount(UserAccount.class, 1);
				
				userAccount.setBalance(nuevoBalance);
				
				anotherUserAccountService.insertUserAccount(userAccount);
				
				
				
			}
		});
	}
}

