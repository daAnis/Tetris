package ru.ssau.fiit.tetris;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Arrays;

public class Figure implements Serializable {
    private int level;
    private byte [][] structure;

    public Figure() {
        structure = new byte[4][4];
        level = 1;
    }

    public Figure(int level, byte[][] structure) {
        this.level = level;
        this.structure = structure;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public byte[][] getStructure() { return structure; }

    public void setStructure(byte[][] structure) {
        this.structure = new byte[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.structure[i][j] = structure[i][j];
            }
        }
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Figure figure = (Figure) obj;
        if (this.getLevel() != figure.getLevel()) return false;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (this.getStructure()[i][j] != figure.getStructure()[i][j]) return false;
            }
        }
        return true;
    }
}
