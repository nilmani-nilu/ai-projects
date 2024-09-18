package sag.nilu.Weather_Report.service;

import sag.nilu.Weather_Report.model.Answer;
import sag.nilu.Weather_Report.model.Question;

public interface OpenAIService {
	Answer getAnswer(Question question);

}
