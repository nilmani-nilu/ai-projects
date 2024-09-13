package sag.nilu.Rag_Ai_expert.service;

import sag.nilu.Rag_Ai_expert.model.Answer;
import sag.nilu.Rag_Ai_expert.model.Question;

public interface OpenAiService {
	Answer getAnswer(Question question);

}
