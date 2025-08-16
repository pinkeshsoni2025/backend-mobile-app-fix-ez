package com.coders.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;





@SpringBootApplication
public class JavaMailSenderApplication {
	
	private static Logger LOG = LoggerFactory
            .getLogger(JavaMailSenderApplication.class);

	public static void main(String[] args) {
		LOG.info("\n 1. STARTING : Spring boot application starting");
		SpringApplication.run(JavaMailSenderApplication.class, args);
		LOG.info("\n 3. STOPPED  : Spring boot application stopped");
	}
	
    /*@Bean
    CommandLineRunner startup() {
		return args -> {
			Product p1 = new Product(1,"Laptop", "Laptop","silver",true);
			Product p2 = new Product(2,"iPad", "iPad","silver",true);
			
			productsRepository.saveAll(List.of(p1,p2));
			
		};
    }*/

}
