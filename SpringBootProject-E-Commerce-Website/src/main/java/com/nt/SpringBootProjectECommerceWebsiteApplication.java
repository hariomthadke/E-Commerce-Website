package com.nt;

import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.nt.service.EmailScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class SpringBootProjectECommerceWebsiteApplication {

	
	@Bean(name = "taskExecutor")
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(50);
        executor.setMaxPoolSize(50);
        executor.setQueueCapacity(200);
        executor.setThreadNamePrefix("AsyncThread-");
        executor.initialize();
        System.out.println("Active threads: " + executor.getActiveCount()); // This line helps for debugging.
        return executor;
    }
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootProjectECommerceWebsiteApplication.class, args);
		
	}

}
