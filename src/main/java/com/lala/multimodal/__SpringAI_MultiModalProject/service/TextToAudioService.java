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

    public byte[] getAudio(String message,String voice, Double speed,String format){

        OpenAiAudioApi.SpeechRequest.Voice voiceEnum = parseVoice(voice);
        OpenAiAudioApi.SpeechRequest.AudioResponseFormat formatEnum = parseFormat(format);
        double speedVal = (speed != null && speed > 0) ? speed : 1.0;

        OpenAiAudioSpeechOptions speechOptions =
                OpenAiAudioSpeechOptions
                        .builder()
                        .model("tts-1")
                        .voice(voiceEnum)
                        .responseFormat(formatEnum)
                        .speed(speedVal)
                        .build();

        TextToSpeechPrompt speechPrompt = new TextToSpeechPrompt(message,speechOptions);
        TextToSpeechResponse response = speechModel.call(speechPrompt);
        return  response.getResult().getOutput();
    }

    private OpenAiAudioApi.SpeechRequest.Voice parseVoice(String voice) {
        if (voice == null) {
            return OpenAiAudioApi.SpeechRequest.Voice.ALLOY;
        }
        try {
            return OpenAiAudioApi.SpeechRequest.Voice.valueOf(voice.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            return OpenAiAudioApi.SpeechRequest.Voice.ALLOY;
        }
    }

    private OpenAiAudioApi.SpeechRequest.AudioResponseFormat parseFormat(String format) {
        if (format == null) {
            return OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3;
        }
        try {
            return OpenAiAudioApi.SpeechRequest.AudioResponseFormat.valueOf(format.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            return OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3;
        }
    }
}
