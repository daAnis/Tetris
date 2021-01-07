package ru.ssau.fiit.tetris;

import java.io.Serializable;

public class Glass implements Serializable {

    private int width;
    private int height;
    private int color;
    private double speed_k;
    private double points_k;

    public Glass() { }

    public Glass(int width, int height, int color, double speed_k, double points_k) {
        this.width = width;
        this.height = height;
        this.color = color;
        this.speed_k = speed_k;
        this.points_k = points_k;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public double getSpeed_k() {
        return speed_k;
    }

    public void setSpeed_k(double speed_k) {
        this.speed_k = speed_k;
    }

    public double getPoints_k() {
        return points_k;
    }

    public void setPoints_k(double points_k) {
        this.points_k = points_k;
    }
}
