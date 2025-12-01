package com.lala.multimodal.__SpringAI_MultiModalProject.controller;

import com.lala.multimodal.__SpringAI_MultiModalProject.service.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ImageController {

    private final ImageService service;
    public ImageController(ImageService service){
        this.service = service;
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
}
