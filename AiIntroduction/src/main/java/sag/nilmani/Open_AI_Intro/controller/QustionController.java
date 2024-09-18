package sag.nilmani.Open_AI_Intro.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import sag.nilmani.Open_AI_Intro.model.Answer;
import sag.nilmani.Open_AI_Intro.model.GetCapitalRequest;
import sag.nilmani.Open_AI_Intro.model.GetCapitalResponse;
import sag.nilmani.Open_AI_Intro.model.GetCityRequest;
import sag.nilmani.Open_AI_Intro.model.GetCityResponse;
import sag.nilmani.Open_AI_Intro.model.Question;
import sag.nilmani.Open_AI_Intro.service.OpenAIService;

@RestController
public class QustionController {
	private final OpenAIService openAIService;

    public QustionController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping("/ask")
    public Answer askQuestion( @RequestBody Question question) {
    	if (question == null || question.question() == null) {
            throw new IllegalArgumentException("Received null question");
        }
        return openAIService.getAnswer(question);
    }
    
    @PostMapping("/capitalWithInfo")
    public Answer getCapitalWithInfo(@RequestBody GetCapitalRequest getCapitalRequest) {
        return this.openAIService.getCapitalWithInfo(getCapitalRequest);
    }
    
    @PostMapping("/capital")
    public Answer getCapital(@RequestBody GetCapitalRequest getCapitalRequest) {
        return this.openAIService.getCapital(getCapitalRequest);
    }
    @PostMapping("/rajdhani")
    public GetCapitalResponse getRajdhani(@RequestBody GetCapitalRequest getCapitalRequest) {
        return this.openAIService.getRajdhani(getCapitalRequest);
    }
    
    @PostMapping("/saharWithInfo")
    public Answer getSaharWithInfo(@RequestBody GetCityRequest getCityRequest) {
        return this.openAIService.getSahar(getCityRequest);
    }
    @PostMapping("/cityWithInfo")
    public GetCityResponse getCityWithInfo(@RequestBody GetCityRequest getCityRequest) {
        return this.openAIService.getCity(getCityRequest);
    }

}
