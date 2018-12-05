package edu.cloudtech.FoodBolt;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import edu.cloudtech.FoodBolt.chatbot.FoodboltLambda;

@SpringBootApplication
public class FoodBoltApplication {
	
	 @PostConstruct
	  void started() {
	    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	  }

	public static void main(String[] args) {
		SpringApplication.run(FoodBoltApplication.class, args);
		new FoodboltLambda();
	}
}
