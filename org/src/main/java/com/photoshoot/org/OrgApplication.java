package com.photoshoot.org;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class OrgApplication {

	public static void main(String[] args) {

		SpringApplication.run(OrgApplication.class, args);
	}


	
	@Bean
	public WebMvcConfigurer configure() {
	    return new WebMvcConfigurer() {
	        @Override
	        public void addCorsMappings(CorsRegistry reg) {
	            reg.addMapping("/**")
	                .allowedOrigins(
	                    "http://localhost:3000", // Local development
	                    "http://3.111.81.148", // AWS instance by IP
	                    "http://ec2-3-111-81-148.ap-south-1.compute.amazonaws.com", // AWS instance by domain
	                    "https://yourdomain.com" // If you've set up a custom domain
	                )
	                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
	                .allowedHeaders("*")
	                .allowCredentials(true); // Enable this if you're passing cookies or authorization headers
	        }
	    };
	}

}
