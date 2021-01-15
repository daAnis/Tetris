package ru.ssau.fiit.tetris;

public class Constants {
    public static int STAGE_WIDTH = 160;
    public static int STAGE_HEIGHT = 160;
    static final int CELL_SIZE = 32;

    static final int INDEX_COLUMN = 0;
    static final int INDEX_ROW = 1;

    public static void setStageWidth(int stageWidth) {
        STAGE_WIDTH = stageWidth;
    }

    public static void setStageHeight(int stageHeight) {
        STAGE_HEIGHT = stageHeight;
    }
}
