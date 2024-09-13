package sag.nilu.Rag_Ai_expert.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import sag.nilu.Rag_Ai_expert.model.Answer;
import sag.nilu.Rag_Ai_expert.model.Question;
import sag.nilu.Rag_Ai_expert.service.OpenServiceImpl;



@RestController
public class QuestionCotnroller {
	
	@Autowired
	private OpenServiceImpl openService;
	
	@PostMapping("/askExpert")
    public Answer askQuestion(@RequestBody Question question) {
        return openService.getAnswer(question);
    }

}
