package ru.liga.intership.badcode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.liga.intership.badcode.service.PersonService;

@SpringBootApplication
public class BadcodeApplication {
	static Logger logger = LoggerFactory.getLogger(BadcodeApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BadcodeApplication.class, args);
		PersonService personService = new PersonService();
		logger.info("----- Get Adult Male Average BMI -----");
		personService.getAdultMaleUsersAverageBMI();
	}
}
