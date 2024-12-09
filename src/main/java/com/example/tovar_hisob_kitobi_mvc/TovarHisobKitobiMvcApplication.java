package com.example.tovar_hisob_kitobi_mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

@EnableJdbcHttpSession
@EnableJpaAuditing
@SpringBootApplication
public class TovarHisobKitobiMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(TovarHisobKitobiMvcApplication.class, args);
	}

}


