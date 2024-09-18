package nilu.sag.text_to_audio.service;

import nilu.sag.text_to_audio.model.Question;

public interface OpenAIService {
	byte[] getSpeech(Question question);

}
