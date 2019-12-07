package sg.nus.iss.team8.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class Team8SmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(Team8SmsApplication.class, args);
	}

}
