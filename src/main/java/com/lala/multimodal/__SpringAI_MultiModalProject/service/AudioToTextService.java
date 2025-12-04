package com.lala.multimodal.__SpringAI_MultiModalProject.service;

import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AudioToTextService {

    private final OpenAiAudioTranscriptionModel audioTranscriptionModel;

    public AudioToTextService(OpenAiAudioTranscriptionModel audioTranscriptionModel) {
        this.audioTranscriptionModel = audioTranscriptionModel;
    }


    public String transcribeAudio(MultipartFile audioFile) {

        Resource audioResource = audioFile.getResource();
        OpenAiAudioTranscriptionOptions options = OpenAiAudioTranscriptionOptions.builder()
                .model("whisper-1")
                .responseFormat(OpenAiAudioApi.TranscriptResponseFormat.TEXT)
                .temperature(0.0f)
                .build();

        //AudioTranscriptionPrompt prompt = new AudioTranscriptionPrompt(audioResource, options);
        return  audioTranscriptionModel.transcribe(audioResource, options);
    }
}
