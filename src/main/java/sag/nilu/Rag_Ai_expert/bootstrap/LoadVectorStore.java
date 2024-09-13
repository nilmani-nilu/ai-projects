package sag.nilu.Rag_Ai_expert.bootstrap;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import sag.nilu.Rag_Ai_expert.config.VectorStoreProperties;

@Slf4j
@Component
public class LoadVectorStore implements CommandLineRunner {
	private static final Logger log = LoggerFactory.getLogger(LoadVectorStore.class);
	
	private final VectorStore vectorStore;
	private final VectorStoreProperties vectorStoreProperties;
	

	@Autowired
	public LoadVectorStore(VectorStore vectorStore, VectorStoreProperties vectorStoreProperties) {
		this.vectorStore = vectorStore;
		this.vectorStoreProperties = vectorStoreProperties;
	}


	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		if (vectorStore.similaritySearch("Sportsman").isEmpty()){
            log.debug("Loading documents into vector store");

            vectorStoreProperties.getDocumentsToLoad().forEach(document -> {
                System.out.println("Loading document: " + document.getFilename());

                TikaDocumentReader documentReader = new TikaDocumentReader(document);
                List<Document> documents = documentReader.get();

                TextSplitter textSplitter = new TokenTextSplitter();

                List<Document> splitDocuments = textSplitter.apply(documents);

                vectorStore.add(splitDocuments);
            });
        }

        log.debug("Vector store loaded");
		
	}


}
