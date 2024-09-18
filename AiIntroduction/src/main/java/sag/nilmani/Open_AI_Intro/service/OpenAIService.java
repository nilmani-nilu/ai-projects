package sag.nilmani.Open_AI_Intro.service;

import sag.nilmani.Open_AI_Intro.model.Answer;
import sag.nilmani.Open_AI_Intro.model.GetCapitalRequest;
import sag.nilmani.Open_AI_Intro.model.GetCapitalResponse;
import sag.nilmani.Open_AI_Intro.model.GetCityRequest;
import sag.nilmani.Open_AI_Intro.model.GetCityResponse;
import sag.nilmani.Open_AI_Intro.model.Question;

public interface OpenAIService {
	String getAnswer(String question);
	Answer getAnswer(Question question);
	Answer getCapital(GetCapitalRequest getCapitalRequest);
	Answer getCapitalWithInfo(GetCapitalRequest getCapitalRequest);
	GetCapitalResponse  getRajdhani(GetCapitalRequest getCapitalrequest);
	Answer getSahar(GetCityRequest getCityRequest);
	GetCityResponse  getCity(GetCityRequest geCityrequest);

}
