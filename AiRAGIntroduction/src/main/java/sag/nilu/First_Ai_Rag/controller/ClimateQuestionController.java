package sag.nilu.First_Ai_Rag.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import sag.nilu.First_Ai_Rag.model.Answer;
import sag.nilu.First_Ai_Rag.model.Question;
import sag.nilu.First_Ai_Rag.service.ClimateServiceImpl;

@RestController
public class ClimateQuestionController {
	
	private final ClimateServiceImpl cliService;

	public ClimateQuestionController(ClimateServiceImpl cliService) {
		this.cliService = cliService;
	}
	
	@PostMapping("/pucho")
	public Answer askQuestion(@RequestBody Question question) {
		return cliService.getAnswer(question);
	}
	
	

}
