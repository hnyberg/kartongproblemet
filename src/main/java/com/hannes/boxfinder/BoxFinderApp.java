package com.hannes.boxfinder;

import com.hannes.boxfinder.service.BoxFinder;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@AllArgsConstructor
@SpringBootApplication
public class BoxFinderApp implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BoxFinderApp.class, args);
	}

	@Override
	public void run(String... args) {
		String answer = BoxFinder.findBox(args);
		System.out.println(answer);
	}
}
