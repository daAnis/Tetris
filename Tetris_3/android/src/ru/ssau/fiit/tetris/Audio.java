package ru.ssau.fiit.tetris;

import android.net.Uri;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class Audio implements Serializable {
    private String name;
    private String uriSerializable;
    private transient Uri uri;

    public Audio() { }

    public Audio(String name) {
        this.name = name;
    }

    public Audio(String name, Uri uri) {
        this.name = name;
        this.uri = uri;
        uriSerializable = uri.toString();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Uri getUri() { return uri; }
    public void setUri(Uri uri) { this.uri = uri; }
    public String getUriSerializable() { return uriSerializable; }

    @Override
    public boolean equals(@Nullable Object obj) {
        Audio audio = (Audio) obj;
        return this.getName().equals(audio.getName());
    }
}
