package com.shelest.booster;

import com.shelest.booster.services.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootMySqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMySqlApplication.class, args);
	}

//	@Bean
//	public DeveloperService getDeveloperService() {
//		return new DeveloperServiceImpl();
//	}

//	@Bean
//	public TaskService getTaskService() {
//		return new TaskServiceImpl();
//	}
//
//	@Bean
//	public ManagementService getManagementService() {
//		return new ManagementServiceImpl();
//	}
//
//	@Bean
//	public ProjectService getProjectService() {
//		return new ProjectServiceImpl();
//	}

}
