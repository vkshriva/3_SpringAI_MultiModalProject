package com.lala.multimodal.__SpringAI_MultiModalProject.controller;

import com.lala.multimodal.__SpringAI_MultiModalProject.service.ImageService;
import com.lala.multimodal.__SpringAI_MultiModalProject.service.TextToImageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ImageController {

    private final ImageService service;
    private final TextToImageService textToImageService;
    public ImageController(ImageService service, TextToImageService textToImageService){
        this.service = service;
        this.textToImageService = textToImageService;
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

}
