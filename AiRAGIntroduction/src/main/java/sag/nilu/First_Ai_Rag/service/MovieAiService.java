package sag.nilu.First_Ai_Rag.service;

import sag.nilu.First_Ai_Rag.model.Answer;
import sag.nilu.First_Ai_Rag.model.GetTitleRequest;
import sag.nilu.First_Ai_Rag.model.Question;

public interface MovieAiService {
	//Answer getTitleWithInfo(GetTitleRequest getTitleRequest);
	 Answer getAnswer(Question question);
	 Answer getMetaAnswer(Question question);

}
