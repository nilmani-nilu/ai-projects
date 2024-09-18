package sag.nilu.First_Ai_Rag.service;

import sag.nilu.First_Ai_Rag.model.Answer;
import sag.nilu.First_Ai_Rag.model.Question;

public interface ClimateAIService {
	Answer getAnswer(Question question);

}
