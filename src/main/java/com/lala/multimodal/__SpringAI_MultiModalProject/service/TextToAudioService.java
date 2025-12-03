package com.lala.multimodal.__SpringAI_MultiModalProject.service;

import org.springframework.ai.audio.tts.TextToSpeechPrompt;
import org.springframework.ai.audio.tts.TextToSpeechResponse;
import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class TextToAudioService {
    private final OpenAiAudioSpeechModel speechModel;

    public TextToAudioService(OpenAiAudioSpeechModel speechModel){
        this.speechModel = speechModel;
    }

    public byte[] getAudio(@RequestParam("message") String message){
        OpenAiAudioSpeechOptions speechOptions =
                OpenAiAudioSpeechOptions
                        .builder()
                        .model("tts-1")
                        .voice(OpenAiAudioApi.SpeechRequest.Voice.ALLOY)
                        .responseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3)
                        .speed(1.0)
                        .build();

        TextToSpeechPrompt speechPrompt = new TextToSpeechPrompt(message,speechOptions);
        TextToSpeechResponse response = speechModel.call(speechPrompt);
        return  response.getResult().getOutput();
    }
}
