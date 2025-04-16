package com.example.electroplan_backend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@OpenAPIDefinition(
//		info = @Info(
//				title = "Online queries",
//				contact = @Contact(
//						name = "Daria Yakovenko",
//						email = "email@gmail.com"
//				),
//				description = "MyOpen",
//				version = "2.2.0"
//		),
//		servers = {
//				@Server(url = "http://localhost:8080", description = "main server"),
//		}
//)
@SpringBootApplication
public class ElectroplanBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(ElectroplanBackendApplication.class, args);
	}

}
