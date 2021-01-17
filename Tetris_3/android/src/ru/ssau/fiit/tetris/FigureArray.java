package ru.ssau.fiit.tetris;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class FigureArray extends Figure {
    public FigureArray() { }

    public FigureArray(String figureId, int level, String structureString) {
        super(figureId, level, structureString);
    }

    public byte[][] getStructure() {
        byte [][] structure = new byte[4][4];
        int c = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (structureString.charAt(c) == '0') {
                    structure[i][j] = 0;
                } else {
                    structure[i][j] = 1;
                }
                c++;
            }
        }
        return structure;
    }

    public void setStructure(byte[][] structure) {
        StringBuilder stringBuilder = new StringBuilder("0000000000000000");
        int c = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (structure[i][j] == 0) {
                    stringBuilder.setCharAt(c, '0');
                } else {
                    stringBuilder.setCharAt(c, '1');
                }
                c++;
            }
        }
        structureString = stringBuilder.toString();
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
                if (getStructure()[i][j] == 1) {
                    if (i < l) l = i;
                    if (j < c) c = j;
                }
            }
        }
        //перемещаем старую фигуру в верхний левый угол новой
        for (int i = l; i < 4; i++) {
            for (int j = c; j < 4; j++) {
                if (getStructure()[i][j] == 1)
                    newStructure[i - l][j - c] = 1;
            }
        }
        //сохраняем изменения
        this.setStructure(newStructure);
    }

    public void setStructureRotate() {
        byte [][] newStructure = new byte[4][4];
        int i = 0, j = 3;
        while ((i < 4) && (j > -1)) {
            for (int k = 0; k < 4; k++) {
                newStructure[k][j] = getStructure()[i][k];
            }
            i++; j--;
        }
        this.setStructure(newStructure);
    }

    //проверка на уникальность
    @Override
    public boolean equals(@Nullable Object obj) {
        //перемещаем фигуру в угол
        this.setStructureToTopLeft();
        FigureArray figureArray = (FigureArray) obj;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (this.getStructure()[i][j] != figureArray.getStructure()[i][j]) return false;
            }
        }
        return true;
    }

    //количество ячеек фигуры
    private int amountOfFilled() {
        int filled = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (getStructure()[i][j] == 1) filled++;
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
                if (getStructure()[i][j] == 1) {
                    result = true;
                    if (i > 0){
                        if (getStructure()[i-1][j] == 1)
                            result = false;
                    }
                    if (i < 3) {
                        if (getStructure()[i+1][j] == 1)
                            result = false;
                    }
                    if (j > 0) {
                        if (getStructure()[i][j-1] == 1)
                            result = false;
                    }
                    if (j < 3) {
                        if (getStructure()[i][j+1] == 1)
                            result = false;
                    }
                    //если нет соседей, то разрыв
                    if (result) return true;
                }
            }
        }
        return false;
    }

    public Figure getFigure() {
        return new Figure(figureId, level, structureString);
    }
}
