package mx.edu.uacm.concurrencia;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadConfig {

	@Bean
	public TaskExecutor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		
		executor.setCorePoolSize(4);
		executor.setMaxPoolSize(4);
		
		executor.setThreadNamePrefix("Tarea_ejecucion_thread");
		
		return executor;
	}
}
