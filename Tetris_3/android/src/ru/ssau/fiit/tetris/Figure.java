package ru.ssau.fiit.tetris;

import androidx.annotation.Nullable;

import java.io.Serializable;

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
            System.arraycopy(structure[i], 0, this.structure[i], 0, 4);
        }
    }

    public void setStructureToTopLeft() {
        //задаем новую структуру фигуры
        byte [][] newStructure = new byte[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                newStructure[i][j] = 0;
            }
        }
        //ищем первый заполненный столбец и первую заполненную строку страрой фигуры
        int l = 4, c = 4;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (structure[i][j] == 1) {
                    if (i < l) l = i;
                    if (j < c) c = j;
                }
            }
        }
        //перемещаем старую фигуру в верхний левый угол новой
        for (int i = l; i < 4; i++) {
            for (int j = c; j < 4; j++) {
                if (structure[i][j] == 1)
                    newStructure[i - l][j - c] = 1;
            }
        }
        //сохраняем изменения
        this.setStructure(newStructure);
    }

    //проверка на уникальность
    @Override
    public boolean equals(@Nullable Object obj) {
        //перемещаем фигуру в угол
        this.setStructureToTopLeft();
        Figure figure = (Figure) obj;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (this.getStructure()[i][j] != figure.getStructure()[i][j]) return false;
            }
        }
        return true;
    }

    //количество ячеек фигуры
    private int amountOfFilled() {
        int filled = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (structure[i][j] == 1) filled++;
            }
        }
        return filled;
    }

    //проверка фигуры на целостность
    public boolean hasDiscontinuities() {
        //перемещаем фигуру в угол
        this.setStructureToTopLeft();
        //если фигура состоит из одной ячейки, то разрыва нет
        if (amountOfFilled() == 1) return false;
        boolean result;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (structure[i][j] == 1) {
                    result = true;
                    if (i > 0){
                        if (structure[i-1][j] == 1)
                            result = false;
                    }
                    if (i < 3) {
                        if (structure[i+1][j] == 1)
                            result = false;
                    }
                    if (j > 0) {
                        if (structure[i][j-1] == 1)
                            result = false;
                    }
                    if (j < 3) {
                        if (structure[i][j+1] == 1)
                            result = false;
                    }
                    //если нет соседей, то разрыв
                    if (result) return true;
                }
            }
        }
        return false;
    }
}
