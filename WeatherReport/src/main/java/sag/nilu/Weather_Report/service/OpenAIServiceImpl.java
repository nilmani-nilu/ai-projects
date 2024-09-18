package sag.nilu.Weather_Report.service;

import java.util.List;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.model.ModelOptionsUtils;
import org.springframework.ai.model.function.FunctionCallbackWrapper;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import sag.nilu.Weather_Report.funct.WeatherServiceFunction;
import sag.nilu.Weather_Report.model.Answer;
import sag.nilu.Weather_Report.model.Question;
import sag.nilu.Weather_Report.model.WeatherResponse;

@Service
public class OpenAIServiceImpl implements OpenAIService {
	
	@Value("${sfg.aiapp.apiNinjasKey}")
    private String apiNinjasKey;
	
	private final OpenAiChatModel openAiChatModel;
	
	//String apiNinjasKey,this.apiNinjasKey = apiNinjasKey;

	public OpenAIServiceImpl(OpenAiChatModel openAiChatModel) {
		
		this.openAiChatModel = openAiChatModel;
	}



	@Override
	public Answer getAnswer(Question question) {
		var promptOptions = OpenAiChatOptions.builder()
                .withFunctionCallbacks(List.of(FunctionCallbackWrapper.builder(new WeatherServiceFunction(apiNinjasKey))
                        .withName("CurrentWeather")
                                .withDescription("Get the current weather for a location")
                                .withResponseConverter((response) -> {
                                    String schema = ModelOptionsUtils.getJsonSchema(WeatherResponse.class, false);
                                    String json = ModelOptionsUtils.toJsonString(response);
                                    return schema + "\n" + json;
                                })
                      .build()))
                .build();

        Message userMessage = new PromptTemplate(question.question()).createMessage();
        Message systemMessage = new SystemPromptTemplate("You are a weather service. You receive weather information from a service which gives you the information based on the metrics system." +
                " When answering the weather in an imperial system country, you should convert the temperature to Fahrenheit and the wind speed to miles per hour. ").createMessage();

        var response = openAiChatModel.call(new Prompt(List.of(userMessage), promptOptions));

        return new Answer(response.getResult().getOutput().getContent());
	}

}
