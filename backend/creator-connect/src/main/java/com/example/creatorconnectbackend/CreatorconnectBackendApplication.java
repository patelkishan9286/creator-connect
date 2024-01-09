package com.example.creatorconnectbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * CreatorconnectBackendApplication.java
 * 
 * Description:
 * This is the main class for the CreatorConnect backend application.
 * It is bootstrapped using Spring Boot, providing configurations
 * and initialization procedures for the application.
 * 
 * Functions:
 * - main(String[] args): Serves as the entry point for the application. It initializes the embedded Tomcat server (by default)
 *   and Spring's ApplicationContext.
 */

@SpringBootApplication
public class CreatorconnectBackendApplication {

	public static void main(String[] args) {
	    // SpringApplication.run method starts the embedded Tomcat server (by default) and initializes Spring's ApplicationContext.
		SpringApplication.run(CreatorconnectBackendApplication.class, args);
	}

}
