package sag.nilmani.Open_AI_Intro.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OpenAIServiceImpTest {
	@Autowired
	OpenAIServiceImp openAIservie;
	
	@Test
	void getAnswer() {
		String answer = openAIservie.getAnswer("Tell me about sanskrit chants");
		System.out.println("Got the answer");
		System.out.println(answer);
	}

}
