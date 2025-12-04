package com.lala.multimodal.__SpringAI_MultiModalProject.controller;

import com.lala.multimodal.__SpringAI_MultiModalProject.service.AudioToTextService;
import com.lala.multimodal.__SpringAI_MultiModalProject.service.ImageService;
import com.lala.multimodal.__SpringAI_MultiModalProject.service.TextToAudioService;
import com.lala.multimodal.__SpringAI_MultiModalProject.service.TextToImageService;
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class ImageController {

    private final ImageService service;
    private final TextToImageService textToImageService;
    private final TextToAudioService textToAudioService;
    private final AudioToTextService audioToTextService;
    public ImageController(ImageService service, TextToImageService textToImageService, TextToAudioService textToAudioService,AudioToTextService audioToTextService){
        this.service = service;
        this.textToImageService = textToImageService;
        this.textToAudioService=textToAudioService;
        this.audioToTextService= audioToTextService;
    }
    @GetMapping("/ask")
    public ResponseEntity<String> ask(@RequestParam("imageName")String imageName,@RequestParam("question")String question){

         try{
             String response =  service.getAnswer(imageName,question);
             return ResponseEntity.ok(response);
         } catch (Exception e) {
             e.printStackTrace();
             return ResponseEntity.status(500).body("Error while generating answers :"+e.getMessage());
         }

    }

    @GetMapping("/ask-image")
    public ResponseEntity<?> askImage(@RequestParam("message")String message){
        try{
            byte[] response =  textToImageService.getImage(message);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(response);
                    } catch (Exception e) {
            return ResponseEntity.status(500).body("Error while generating answers :"+e.getMessage());
        }

    }


    @GetMapping("/ask-audio")
    public ResponseEntity<?> askAudio(@RequestParam("message")String message,
                                      @RequestParam(required = false, defaultValue="alloy") String voice,
                                      @RequestParam(required = false, defaultValue = "1.0") Double speed,
                                      @RequestParam(required = false, defaultValue = "mp3") String format){
        try{
            byte[] response =  textToAudioService.getAudio(message,voice,speed,format);
            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf("audio/mpeg"))
                    .body(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error while generating answers :"+e.getMessage());
        }

    }

    @GetMapping("/transcribe-audio-to-text")
    public ResponseEntity<String> askAudioToText(@RequestParam("file")MultipartFile audioFile){
        if(audioFile.isEmpty()){
            return ResponseEntity.badRequest().body("File is empty");
        }
        //supported format: mp3,mp4,mpeg,mpga,m4a,wav,webm
        String contentType = audioFile.getContentType();
        if(contentType==null || !(contentType.equals("audio/mpeg") || contentType.equals("audio/wav") || contentType.equals("audio/mp4") || contentType.equals("audio/webm"))){
            return ResponseEntity.badRequest().body("Unsupported file format: "+contentType);
        }

        try{
            String response =  audioToTextService.transcribeAudio(audioFile);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error while transcribing audio :"+e.getMessage());
        }



    }

}
