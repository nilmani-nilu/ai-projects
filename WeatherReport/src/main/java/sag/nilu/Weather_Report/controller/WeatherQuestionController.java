package sag.nilu.Weather_Report.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import sag.nilu.Weather_Report.model.Answer;
import sag.nilu.Weather_Report.model.Question;
import sag.nilu.Weather_Report.service.OpenAIService;
import sag.nilu.Weather_Report.service.OpenAIServiceImpl;

@Controller
public class WeatherQuestionController {
	
	private final OpenAIService openAiService;
	
	
	public WeatherQuestionController(OpenAIService openAiService) {
		this.openAiService = openAiService;
	}


	@PostMapping("/weather")
    public Answer askQuestion(@RequestBody Question question) {
        return openAiService.getAnswer(question);
    }
	

}
