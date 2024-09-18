package sag.nilu.First_Ai_Rag.service;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import sag.nilu.First_Ai_Rag.model.Answer;
import sag.nilu.First_Ai_Rag.model.Question;

@Service
public class ClimateServiceImpl implements ClimateAIService{
	
	private final ChatModel chatModel;
	private final SimpleVectorStore vectorStore;
	
	

	public ClimateServiceImpl(ChatModel chatModel, SimpleVectorStore vectorStore) {
		this.chatModel = chatModel;
		this.vectorStore = vectorStore;
	}
	@Value("classpath:/templates/climate-ragTemplatePrompt.st")
	private Resource cliReresource;


	@Override
	public Answer getAnswer(Question question) {
		// TODO Auto-generated method stub
		List<Document> document = vectorStore.similaritySearch(SearchRequest.query(question.question()).withTopK(10));
		List<String> contentList = document.stream().map(Document::getContent).toList();
		PromptTemplate prompTemplate = new PromptTemplate(cliReresource);
		Prompt prompt = prompTemplate.create(Map.of("input",question.question(),"document"
				,String.join("\n", contentList)));
		ChatResponse chResponse = chatModel.call(prompt);
		
		return new Answer(chResponse.getResult().getOutput().getContent());
	}

}

