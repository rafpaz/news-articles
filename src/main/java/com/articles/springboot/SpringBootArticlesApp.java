package com.articles.springboot;

import com.articles.springboot.configuration.JpaConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(JpaConfiguration.class)
@SpringBootApplication(scanBasePackages = {"com.articles.springboot"})
public class SpringBootArticlesApp {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootArticlesApp.class, args);
	}
}