package nilu.sag.Image_Generation_Vision.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import nilu.sag.Image_Generation_Vision.model.Question;

public interface OpenAIService {
	byte [] getImage(Question queston);
	
	String  getDescription(MultipartFile file)  throws IOException;

}
