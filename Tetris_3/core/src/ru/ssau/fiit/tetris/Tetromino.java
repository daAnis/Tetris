package ru.ssau.fiit.tetris;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;

import static ru.ssau.fiit.tetris.Constants.INDEX_COLUMN;
import static ru.ssau.fiit.tetris.Constants.INDEX_ROW;
import static ru.ssau.fiit.tetris.GameStage.NUM_COLUMNS;
import static ru.ssau.fiit.tetris.GameStage.NUM_ROWS;

public class Tetromino {
    private static final Type[] TYPE = Type.values();
    private static Random random = new Random();

    //Начальная позиция
    private int originColumn;
    private int originRow;
    private final Type type;
    //Позиция относительно начальной позиции
    private int[][] relativePositions;
    private final int[][] relativePositionsOriginal;
    private final float[] originDelta;

    private static float r, g, b;

    public static Tetromino getInstance() {
        //случайно определяем вид фигуры
        Tetromino tetromino = new Tetromino(TYPE[random.nextInt(TYPE.length)]);
        //устанавливаем точку падения
        tetromino.initPosition();

        //случайно определяем цвет
        Random rand = new Random();
        r = rand.nextFloat();
        g = rand.nextFloat();
        b = rand.nextFloat() ;

        return tetromino;
    }

    public static Color tetromoniColor() {
        return new Color(r, g, b, 1);
    }

    Tetromino(Type type) {
        this.type = type;
        this.relativePositionsOriginal = type.relativePositions;
        this.relativePositions = type.relativePositions;
        this.originDelta = type.originDelta;
    }

    public void initPosition() {
        originColumn = NUM_COLUMNS / 2;
        originRow = NUM_ROWS - 1;
    }

    public int[][] getBlocks() {
        return getBlocks(relativePositions);
    }

    //переместить ниже
    public void fall() {
        originRow--;
    }

    //перевернуть
    public void rotate(GameStage gameStage) {
        if (this.type == Type.SQUARE) {
            return;
        }
        int[][] rotated = new int[][] {
                new int[] {-relativePositions[0][INDEX_ROW], relativePositions[0][INDEX_COLUMN]},
                new int[] {-relativePositions[1][INDEX_ROW], relativePositions[1][INDEX_COLUMN]},
                new int[] {-relativePositions[2][INDEX_ROW], relativePositions[2][INDEX_COLUMN]},
                new int[] {-relativePositions[3][INDEX_ROW], relativePositions[3][INDEX_COLUMN]}};
        int[][] newPositions = getBlocks(rotated);
        if (gameStage.canPlaceBlocks(newPositions)) {
            relativePositions = rotated;
        }
    }

    //влево
    public void moveToLeft(GameStage gameStage) {
        int[][] blocks = getBlocks();
        for (int[] block: blocks) {
            block[INDEX_COLUMN]--;
        }
        if (gameStage.canPlaceBlocks(blocks)) {
            originColumn--;
        }
    }

    //вправо
    public void moveToRight(GameStage gameStage) {
        int[][] blocks = getBlocks();
        for (int[] block: blocks) {
            block[INDEX_COLUMN]++;
        }
        if (gameStage.canPlaceBlocks(blocks)) {
            originColumn++;
        }
    }

    //отрисовка
    public void render(ShapeRenderer renderer) {
        renderer.setColor(new Color(r, g, b, 1));
        for (int[] block: getBlocks()) {
            Tetris.renderBlock(renderer, block[INDEX_COLUMN], block[INDEX_ROW]);
        }
    }

    //отрисовка следующей фигуры
    public void render(ShapeRenderer renderer, int startX, int startY, int boxSize) {
        renderer.setColor(new Color(r, g, b, 1));
        int originX = startX + boxSize * 2;
        int originY = startY + boxSize * 2;
        for (int[] block: relativePositionsOriginal) {
            renderer.rect(originX + (block[INDEX_COLUMN] + originDelta[INDEX_COLUMN]) * boxSize,
                    originY + (block[INDEX_ROW] + originDelta[INDEX_ROW]) * boxSize,
                    boxSize,
                    boxSize);
        }
    }

    //
    private int[][] getBlocks(int[][] relativePositions) {
        return new int[][] {
                new int[] {originColumn + relativePositions[0][INDEX_COLUMN], originRow + relativePositions[0][INDEX_ROW]},
                new int[] {originColumn + relativePositions[1][INDEX_COLUMN], originRow + relativePositions[1][INDEX_ROW]},
                new int[] {originColumn + relativePositions[2][INDEX_COLUMN], originRow + relativePositions[2][INDEX_ROW]},
                new int[] {originColumn + relativePositions[3][INDEX_COLUMN], originRow + relativePositions[3][INDEX_ROW]}
        };
    }

    private enum Type {
        // xx
        // xo
        SQUARE(new int[][] {new int[]{-1, 0}, new int[]{0, 0}, new int[]{0, 1}, new int[]{-1, 1}}, new float[] {0.f, -1f}),
        //  x
        // xox
        MOUNTAIN(new int[][] {new int[]{-1, 0}, new int[]{0, 0}, new int[]{1, 0}, new int[]{0, 1}}, new float[] {-0.5f, -1f}),
        // x
        // xox
        MIRROR_L(new int[][] {new int[]{-1, 0}, new int[]{0, 0}, new int[]{1, 0}, new int[]{-1, 1}}, new float[] {-0.5f, -1f}),
        //   x
        // xox
        L(new int[][] {new int[]{-1, 0}, new int[]{0, 0}, new int[]{1, 0}, new int[]{1, 1}}, new float[] {-0.5f, -1f}),
        // xx
        //  ox
        Z(new int[][] {new int[]{1, 0}, new int[]{0, 0}, new int[]{0, 1}, new int[]{-1, 1}}, new float[] {-0.5f, -1f}),
        //  xx
        // xo
        S(new int[][] {new int[]{-1, 0}, new int[]{0, 0}, new int[]{0, 1}, new int[]{1, 1}}, new float[] {-0.5f, -1f}),
        // xoxx
        BAR(new int[][] {new int[]{-1, 0}, new int[]{0, 0}, new int[]{1, 0}, new int[]{2, 0}}, new float[] {-1f, -0.5f});

        private int[][] relativePositions;
        private final float[] originDelta;

        //задаем относительные позиции и коэффициенты сжатия для отрисовки следующей фигуры
        Type(int[][] relativePositions, float[] originDelta) {
            this.relativePositions = relativePositions;
            this.originDelta = originDelta;
        }
    }

}
