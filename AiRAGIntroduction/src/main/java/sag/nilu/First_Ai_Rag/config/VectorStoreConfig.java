package sag.nilu.First_Ai_Rag.config;

import java.io.File;
import java.util.List;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;



@Slf4j
@Configuration
public class VectorStoreConfig {
	private static final Logger log = LoggerFactory.getLogger(VectorStoreConfig.class);
	
	@Bean
	SimpleVectorStore simpleVectorStore(EmbeddingModel embeddingClient, VectorStoreProperties vectorStoreProperties) {
		var store = new SimpleVectorStore(embeddingClient);
		File vectorStoreFile = new File(vectorStoreProperties.getVectorStorePath());
		//check the vector stor path exist or not
		if (vectorStoreFile.exists()) {
			//load store file
			store.load(vectorStoreFile);
		}else {
			log.debug("Loading documents into vector store");
			vectorStoreProperties.getDocumentsToLoad().forEach(document -> {
                log.debug("Loading document: " + document.getFilename());
                TikaDocumentReader documentReader = new TikaDocumentReader(document);
                List<Document> docs = documentReader.get();
                TextSplitter textSplitter = new TokenTextSplitter();
                List<Document> splitDocs = textSplitter.apply(docs);
                store.add(splitDocs);
			 });

            store.save(vectorStoreFile);
		}
		return store;
	}

}
