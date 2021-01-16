package ru.ssau.fiit.tetris;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class Audio implements Serializable {
    private String audioId;

    private String name;
    private String uriSerializable;

    public Audio() { }

    public Audio(String name) {
        this.name = name;
    }

    public Audio(String name, String uriSerializable) {
        this.name = name;
        this.uriSerializable = uriSerializable;
    }

    public Audio(String audioId, String name, String uriSerializable) {
        this.audioId = audioId;
        this.name = name;
        this.uriSerializable = uriSerializable;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getUriSerializable() { return uriSerializable; }
    public String getAudioId() { return audioId; }
    public void setAudioId(String audioId) { this.audioId = audioId; }
    public void setUriSerializable(String uriSerializable) { this.uriSerializable = uriSerializable; }

    @Override
    public boolean equals(@Nullable Object obj) {
        Audio audio = (Audio) obj;
        return this.getName().equals(audio.getName());
    }
}
