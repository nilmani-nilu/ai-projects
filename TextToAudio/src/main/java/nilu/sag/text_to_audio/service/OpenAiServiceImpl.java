package nilu.sag.text_to_audio.service;

import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.audio.speech.SpeechPrompt;
import org.springframework.ai.openai.audio.speech.SpeechResponse;
import org.springframework.stereotype.Service;

import nilu.sag.text_to_audio.model.Question;

@Service
public class OpenAiServiceImpl implements OpenAIService{
	
	private final OpenAiAudioSpeechModel audioSpeechClient;

	public OpenAiServiceImpl(OpenAiAudioSpeechModel audioSpeechClient) {
		this.audioSpeechClient = audioSpeechClient;
	}

	@Override
	public byte[] getSpeech(Question question) {
		// TODO Auto-generated method stub
		OpenAiAudioSpeechOptions speechOptions = OpenAiAudioSpeechOptions.builder()
                .withVoice(OpenAiAudioApi.SpeechRequest.Voice.ALLOY)
                .withSpeed(1.0f)
                .withResponseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3)
                .withModel(OpenAiAudioApi.TtsModel.TTS_1.value)//this line you can use for any language voice
                .build();

        SpeechPrompt speechPrompt = new SpeechPrompt(question.question(),
                speechOptions);

        SpeechResponse response = audioSpeechClient.call(speechPrompt);

        return response.getResult().getOutput();
	}
	

}
