package ru.ssau.fiit.tetris;

public interface IActivityRequestHandler {
    public void onGameClosed(int score, long time);
    public void onGamePaused();
}
