package nilu.sag.Image_Generation_Vision.service;


import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImageOptionsBuilder;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.model.Media;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import nilu.sag.Image_Generation_Vision.model.Question;

@Service
public class OpenAIServiceImpl  implements OpenAIService {
	@Autowired
	private ImageModel imageModel;
	@Autowired
	private ChatModel chatModel;

	@Override
	public byte[] getImage(Question queston) {
		// TODO Auto-generated method stub
		//if we want image qualitiy then we use OpenAiImageOptions instead of ImageOptionsBuldeer
		var options = OpenAiImageOptions.builder()
				.withHeight(1024).withWidth(1024)
				.withResponseFormat("b64_json")
				//use DallE3 realstic image
				.withModel("dall-e-3")
				.withQuality("hd")
				.withStyle("natural")//Default Vision
				.build();
		ImagePrompt imagePrompt = new ImagePrompt(queston.question(), options);

        var imageResponse = imageModel.call(imagePrompt);
       return Base64.getDecoder().decode(imageResponse.getResult().getOutput().getB64Json());
	}

	@Override
	public String getDescription(MultipartFile file) throws IOException {
		// TODO Auto-generated method stub
		OpenAiChatOptions chatOption= OpenAiChatOptions.builder()
				.withModel(OpenAiApi.ChatModel.GPT_4_VISION_PREVIEW.getValue()).build();
		var userMessage = new UserMessage(
				 "Explain what do you see in this picture?",
				 List.of(new Media(MimeTypeUtils.IMAGE_JPEG, file.getBytes())));
		return chatModel.call(new Prompt(List.of(userMessage), chatOption)).getResult().getOutput().toString();
	}
	

}
