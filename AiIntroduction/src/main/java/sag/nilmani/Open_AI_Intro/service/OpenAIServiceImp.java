package sag.nilmani.Open_AI_Intro.service;
import java.util.Map;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import sag.nilmani.Open_AI_Intro.model.Answer;
import sag.nilmani.Open_AI_Intro.model.GetCapitalRequest;
import sag.nilmani.Open_AI_Intro.model.GetCapitalResponse;
import sag.nilmani.Open_AI_Intro.model.GetCityRequest;
import sag.nilmani.Open_AI_Intro.model.GetCityResponse;
import sag.nilmani.Open_AI_Intro.model.Question;

@Service
public class OpenAIServiceImp implements OpenAIService {

	    private final ChatClient chatClient;

		public OpenAIServiceImp(ChatClient chatClient) {
			this.chatClient = chatClient;
		}
		
		@Value("classpath:templates/get-capital-prompt.st")
	    private Resource getCapitalPrompt;
		
		@Value("classpath:templates/get-captial-withInfo.st")
		private Resource getCapitalPromptInfo;
		
		@Value("classpath:templates/get-city-info.st")
		private Resource getCityInfo;
		
		@Autowired
		ObjectMapper objectMapper;

		@Override
		public String getAnswer(String question) {
			// TODO Auto-generated method stub
	        PromptTemplate promptTemplate = new PromptTemplate(question);
	        Prompt prompt = promptTemplate.create();
	        ChatResponse response = chatClient.call(prompt);

	        return response.getResult().getOutput().getContent();
		}

		@Override
		public Answer getAnswer(Question question) {
			// TODO Auto-generated method stub
			PromptTemplate promptTemplate = new PromptTemplate(question.question());
	        Prompt prompt = promptTemplate.create();
	        ChatResponse response = chatClient.call(prompt);

	        return new Answer(response.getResult().getOutput().getContent());
		}

		@Override
		public Answer getCapital(GetCapitalRequest getCapitalRequest) {
			// TODO Auto-generated method stub
			PromptTemplate promptTemplate = new PromptTemplate(getCapitalPrompt);
	        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry()));
	        ChatResponse response = chatClient.call(prompt);

	        return new Answer(response.getResult().getOutput().getContent());
		}

		@Override
		public Answer getCapitalWithInfo(GetCapitalRequest getCapitalRequest) {
			// TODO Auto-generated method stub
			if (getCapitalRequest == null || getCapitalRequest.stateOrCountry() == null) {
		        throw new IllegalArgumentException("The request or stateOrCountry cannot be null");
		    }

		    // Ensure the resource file is available
		    if (getCapitalPromptInfo == null || !getCapitalPromptInfo.exists()) {
		        throw new IllegalStateException("Prompt template file not found or not accessible");
		    }

		    PromptTemplate prompTemplate = new PromptTemplate(getCapitalPromptInfo);
		    Prompt prompt = prompTemplate.create(Map.of("stateOrCountry", getCapitalRequest.stateOrCountry()));
		    ChatResponse response = chatClient.call(prompt);
		    return new Answer(response.getResult().getOutput().getContent());
		}

		@Override
		public GetCapitalResponse getRajdhani(GetCapitalRequest getCapitalrequest) {
			// TODO Auto-generated method stub
			BeanOutputParser<GetCapitalResponse> parser = new BeanOutputParser<GetCapitalResponse>(GetCapitalResponse.class);
			String format = parser.getFormat();
			PromptTemplate pmtTemplate = new PromptTemplate(getCapitalPrompt);
			Prompt prompt = pmtTemplate.create(Map.of("stateOrCountry",getCapitalrequest.stateOrCountry(),"format",format));
			ChatResponse response = chatClient.call(prompt);
			return parser.parse(response.getResult().getOutput().getContent());
		}

		@Override
		public Answer getSahar(GetCityRequest getCityRequest) {
			// TODO Auto-generated method stub
			PromptTemplate proTemplate = new PromptTemplate(getCityInfo);
			Prompt prmt = proTemplate.create(Map.of("cityOrState",getCityRequest.cityOrState()));
			ChatResponse response =  chatClient.call(prmt);
			System.out.println(response.getResult().getOutput().getContent());
			String responseString;
			try {
				JsonNode jsonNode = objectMapper.readTree(response.getResult().getOutput().getContent());
				responseString = jsonNode.get("answer").asText();
			} catch (JsonProcessingException e) {
				// TODO: handle exception
				throw new RuntimeException(e);
			}
			
			return new Answer(responseString);
		}

		@Override
		public GetCityResponse getCity(GetCityRequest geCityrequest) {
			// TODO Auto-generated method stub
			BeanOutputParser<GetCityResponse> parser = new BeanOutputParser<GetCityResponse>(GetCityResponse.class);
			String format = parser.getFormat();
			PromptTemplate prmpTemplate = new PromptTemplate(getCityInfo);
			Prompt prompt = prmpTemplate.create(Map.of("cityOrState",geCityrequest.cityOrState(),"format",format));
			ChatResponse response = chatClient.call(prompt);
			return parser.parse(response.getResult().getOutput().getContent()) ;
		}

	    
}
