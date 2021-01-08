package ru.ssau.fiit.tetris;

import java.util.Arrays;

public class Figure {
    private int level;
    private byte [][] structure;

    public Figure() { }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public byte[][] getStructure() { return structure; }

    public void setStructure(byte[][] structure) {
        for (int i = 0; i < structure.length; i++) {
            for (int j = 0; j < structure[i].length; j++) {
                this.structure[i][j] = structure[i][j];
            }
        }
    }
}
