package sag.nilu.Rag_Ai_expert.service;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import sag.nilu.Rag_Ai_expert.model.Answer;
import sag.nilu.Rag_Ai_expert.model.Question;

@Service
public class OpenServiceImpl implements OpenAiService {
	
	private final ChatModel chatModel;
	private  final VectorStore vectorStore;
	
	public OpenServiceImpl(ChatModel chatModel, VectorStore vectorStore) {
		this.chatModel = chatModel;
		this.vectorStore = vectorStore;
	}
	
	@Value("classpath:/templates/rag-promptTemplate.st")
	private Resource ragResource;
	@Value("classpath:/templates/system-message.st")
	private Resource ragMessageResou;



	@Override
	public Answer getAnswer(Question question) {
		// TODO Auto-generated method stub
		PromptTemplate prmpTemp = new PromptTemplate(ragResource);
		Message systemMessage = prmpTemp.createMessage();
		List<Document> documents = vectorStore.similaritySearch(SearchRequest.query(question.question()).withTopK(5));
		List<String>contentList =documents.stream().map(Document::getContent).toList();
		PromptTemplate templatePromp = new PromptTemplate(ragMessageResou);
		Message userMessage = templatePromp.createMessage(Map.of("input", question.question(), "documents",
                String.join("\n", contentList)));
		ChatResponse response = chatModel.call(new Prompt(List.of(systemMessage,userMessage)));
		
		return new Answer(response.getResult().getOutput().getContent());
	}

}
