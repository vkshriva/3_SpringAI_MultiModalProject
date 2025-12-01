package com.lala.multimodal.__SpringAI_MultiModalProject.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

@Service
public class ImageService {
    private final ChatClient chatClient;
    public ImageService( ChatClient.Builder builder){
        this.chatClient = builder.build();
    }
    public String getAnswer(String imageName, String questions){
        ClassPathResource resource = new ClassPathResource("images/"+imageName+".jpg");
        return  chatClient
                .prompt()
                .user(userDetails -> userDetails.text(questions)
                        .media(MimeTypeUtils.IMAGE_JPEG,resource)
                ).call()
                .content();

    }
}
