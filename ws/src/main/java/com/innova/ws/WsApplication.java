package com.innova.ws;

import com.innova.ws.user.User;
import com.innova.ws.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class WsApplication {

	public static void main(String[] args) {
		SpringApplication.run(WsApplication.class, args);
	}

	@Bean
	@Profile("prod")
	CommandLineRunner createInitialAdmin(UserService userService) {
		return(args) -> {
			try {
				userService.getByUsername("admin");
			} catch (Exception e) {
				User user = new User();
				user.setUsername("admin");
				user.setFullName("ADMIN");
				user.setPassword("P4ssword");
				userService.save(user, "admin");
			}
		};
	}

	@Bean
	@Profile("dev")
	CommandLineRunner createInitialUsers(UserService userService) {
		return(args) -> {
			try {
				userService.getByUsername("user1");
			} catch (Exception e) {
				for(int i=1; i<=25; i++) {
					User user = new User();
					user.setUsername("user" + i);
					user.setFullName("name" + i);
					user.setPassword("P4ssword");
					userService.save(user, "user");
				}
			}
		};
	}
}
