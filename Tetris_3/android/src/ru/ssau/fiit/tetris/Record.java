package ru.ssau.fiit.tetris;

public class Record {
    private String points;
    private String time;

    public Record(String points, String time) {
        this.points = points;
        this.time = time;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
