package com.campusconnect.CampusConnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;


@SpringBootApplication(scanBasePackages = {"com.campusconnect.CampusConnect"})
@EnableMongoAuditing
public class CampusConnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CampusConnectApplication.class, args);
	}


}
