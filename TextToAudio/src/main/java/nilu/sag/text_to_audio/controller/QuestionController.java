package nilu.sag.text_to_audio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import nilu.sag.text_to_audio.model.Question;
import nilu.sag.text_to_audio.service.OpenAIService;

@RestController
public class QuestionController {
	@Autowired
	private OpenAIService openAiService;
	
	@PostMapping(value ="/talk", produces = "audio/mpeg")
    public byte[] talkTalk(@RequestBody Question question) {
        return openAiService.getSpeech(question);
    }

}
