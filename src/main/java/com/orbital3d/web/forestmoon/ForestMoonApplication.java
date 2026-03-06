package com.orbital3d.web.forestmoon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.orbital3d.web")
public class ForestMoonApplication {
	public static void main(String[] args) {
		SpringApplication.run(ForestMoonApplication.class, args);
	}
}
