package com.internship.ticketing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TicketingApplication {

	public static void main(String[] args) {

		System.out.println("Server has started");
		SpringApplication.run(TicketingApplication.class, args);
	}

}
