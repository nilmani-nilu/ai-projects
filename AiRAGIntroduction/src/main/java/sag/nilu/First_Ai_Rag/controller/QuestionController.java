package sag.nilu.First_Ai_Rag.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import sag.nilu.First_Ai_Rag.model.Answer;
import sag.nilu.First_Ai_Rag.model.Question;
import sag.nilu.First_Ai_Rag.service.MovieAIServiceImpl;

@RestController
public class QuestionController {

	private final MovieAIServiceImpl movieSerImpl;

	public QuestionController(MovieAIServiceImpl movieSerImpl) {
		this.movieSerImpl = movieSerImpl;
	}

	@PostMapping("/ask")
	public Answer askQuestion(@RequestBody Question question) {
		return movieSerImpl.getAnswer(question);
	}
	
	@PostMapping("/askMetaData")
	public Answer askMetaDataQuestion(@RequestBody Question question) {
		return movieSerImpl.getAnswer(question);
	}

}
