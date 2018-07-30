package mx.edu.uacm.concurrencia.test.optimistic.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.task.TaskExecutor;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import mx.edu.uacm.concurrencia.HibernatJpaControlConcurrenciaApplication;
import mx.edu.uacm.concurrencia.domain.optimistic.AnotherUserAccount;
import mx.edu.uacm.concurrencia.service.AnotherUserAccountService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=HibernatJpaControlConcurrenciaApplication.class)
public class AnotherUserAccountServiceImpTest {

	private static final Logger log = LogManager.getLogger(AnotherUserAccountServiceImpTest.class);
	
	@Autowired
	private AnotherUserAccountService anotherUserAccountService;
	
	@Autowired
	private TaskExecutor taskExecutor;
	
	@Test
	public void testGuardarAnotherUserAccountThreads() {
		log.debug("Entrando a Test testGuardarAnotherUserAccountThreads");
		
		AnotherUserAccount anotherUserAccount = new AnotherUserAccount();
		anotherUserAccount.setId(1);	
		anotherUserAccount.setBalance(600);		
		anotherUserAccount.setName("Ernesto");
		
		//persist
		anotherUserAccountService.insertAnotherUserAccount(anotherUserAccount);
		
		//Ejecucion hilo
		taskExecutor.execute(cambioDeSaldoHilo(700, 1));
		//Ejecucion hilo
		taskExecutor.execute(cambioDeSaldoHilo(800, 1));
	
		
	}

	public Runnable cambioDeSaldoHilo(final double nuevoBalance, final long sleepTime) {
		//Clase interna anonima
		return new Thread(new Runnable() {
			
			@Override
			public void run() {
				log.debug("Entrando a run()");
				//Select, obtener la entidad manejada por el contexto de persistencia
				AnotherUserAccount userAccount = anotherUserAccountService.buscarPorId(AnotherUserAccount.class, 1);
			
				userAccount.setBalance(nuevoBalance);
				anotherUserAccountService.insertAnotherUserAccount(userAccount);
				
				try {
					Thread.sleep(sleepTime);
				}catch (InterruptedException e) {
					e.printStackTrace();
				}
				log.debug(userAccount);
			}
		});
	}
}
