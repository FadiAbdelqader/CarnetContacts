package com.example.projetFsr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.example.projetFsr"})
public class ProjetFsrApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetFsrApplication.class, args);
	}

}
