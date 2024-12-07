package com.campusconnect.CampusConnect;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CampusConnectApplicationTests {

	@Test
	@PostConstruct
	void contextLoads() {
		System.out.println("Server started ......");
	}

	@Test
	@PreDestroy
	void contextDestroy(){
		System.out.println("Server stopped ......");
	}

}
