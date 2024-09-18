package nilu.sag.Image_Generation_Vision.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import nilu.sag.Image_Generation_Vision.model.Question;
import nilu.sag.Image_Generation_Vision.service.OpenAIService;

@RestController
public class ImageQuestionControlelr {

	private  final OpenAIService openAiService;
	
	
	
	public ImageQuestionControlelr(OpenAIService openAiService) {
		this.openAiService = openAiService;
	}



	@PostMapping(value = "/image", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage(@RequestBody Question question) {
        return openAiService.getImage(question);
    }
	
	@PostMapping(value = "/vision", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> upload(
            @Validated @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name
    ) throws IOException {

        return ResponseEntity.ok(openAiService.getDescription(file));
    }

}
