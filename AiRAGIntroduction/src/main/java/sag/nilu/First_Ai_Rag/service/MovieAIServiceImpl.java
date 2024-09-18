package sag.nilu.First_Ai_Rag.service;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import sag.nilu.First_Ai_Rag.model.Answer;
import sag.nilu.First_Ai_Rag.model.GetTitleRequest;
import sag.nilu.First_Ai_Rag.model.Question;
@Service
public class MovieAIServiceImpl implements MovieAiService{
	
	private final ChatModel chatClient;
	private final SimpleVectorStore vectorStore;
	
	
	
	public MovieAIServiceImpl(ChatModel chatClient, SimpleVectorStore vectorStore) {
		this.chatClient = chatClient;
		this.vectorStore = vectorStore;
	}

	@Value("classpath:/templates/rag-promptTemplate.st")
    private Resource ragResource;
	
	@Value("classpath:/templates/rag-promptTemplate.st")
    private Resource ragmetaResource;
	
	@Override
	public Answer getAnswer(Question question) {
		// TODO Auto-generated method stub
		List<Document> documents = vectorStore.similaritySearch(SearchRequest.query(question.question()).withTopK(5));
		List<String> contentList = documents.stream().map(Document::getContent).toList();
		PromptTemplate prompTemplate = new PromptTemplate(ragResource);
		Prompt prompt = prompTemplate.create(Map.of("input",question.question(),"documents",String.join("\n", contentList)));
		contentList.forEach(System.out::println);
		ChatResponse response = chatClient.call(prompt);
		return new Answer(response.getResult().getOutput().getContent());
	}

	@Override
	public Answer getMetaAnswer(Question question) {
		// TODO Auto-generated method stub
		List<Document> documents = vectorStore.similaritySearch(SearchRequest.query(question.question()).withTopK(5));
		List<String> contentList = documents.stream().map(Document::getContent).toList();
		PromptTemplate prompTemplate = new PromptTemplate(ragmetaResource);
		Prompt prompt = prompTemplate.create(Map.of("input",question.question(),"documents",String.join("\n", contentList)));
		contentList.forEach(System.out::println);
		ChatResponse response = chatClient.call(prompt);
		return new Answer(response.getResult().getOutput().getContent());
	}


	

}
