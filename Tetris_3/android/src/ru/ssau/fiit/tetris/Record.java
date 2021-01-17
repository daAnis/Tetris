package ru.ssau.fiit.tetris;

import java.io.Serializable;

public class Record implements Serializable {
    private String recordId;
    private String userId;

    private String points;
    private String time;

    public Record() {
    }

    public Record(String points, String time) {
        this.points = points;
        this.time = time;
    }

    public Record(String userId, String points, String time) {
        this.userId = userId;
        this.points = points;
        this.time = time;
    }

    public Record(String recordId, String userId, String points, String time) {
        this.recordId = recordId;
        this.userId = userId;
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

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
