package com.lala.multimodal.__SpringAI_MultiModalProject.service;

import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Base64;

@Service
public class TextToImageService {
    private final ImageModel imageModel;
    public TextToImageService(ImageModel imageModel){
        this.imageModel =imageModel;
    }

    public byte[] getImage(@RequestParam("message")String message){
        ImagePrompt imagePrompt = new ImagePrompt(message,
                OpenAiImageOptions.builder()
                        .responseFormat("b64_json")  //by default it is URL
                        .build());

        ImageResponse response = imageModel.call(imagePrompt);
        String b64 =  response.getResult().getOutput().getB64Json();
        return Base64.getDecoder().decode(b64);
    }
}
