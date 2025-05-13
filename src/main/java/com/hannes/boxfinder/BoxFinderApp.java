package com.hannes.boxfinder;

import com.hannes.boxfinder.service.ArticleNotFoundException;
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
		String answer = "";
		try {
			answer = BoxFinder.findBox(args);
		} catch (ArticleNotFoundException anfe) {
			System.out.println(anfe.getMessage());
		}
		System.out.println(answer);
	}
}
