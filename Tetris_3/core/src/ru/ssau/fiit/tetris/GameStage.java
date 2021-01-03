package ru.ssau.fiit.tetris;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.HashSet;
import java.util.Set;

import static ru.ssau.fiit.tetris.Constants.CELL_SIZE;
import static ru.ssau.fiit.tetris.Constants.INDEX_COLUMN;
import static ru.ssau.fiit.tetris.Constants.INDEX_ROW;

public class GameStage extends Actor {
    public static final int NUM_COLUMNS = 10;
    public static final int NUM_ROWS = 22;

    private boolean[][] isFilled = new boolean[NUM_COLUMNS][NUM_ROWS];
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    public void reset() {
        for (int i = 0; i < NUM_COLUMNS; i++) {
            for (int j = 0; j < NUM_ROWS; j++) {
                isFilled[i][j] = false;
            }
        }
    }

    /**
     * Set the given blocks on stage.
     * @return number of rows erased, if any.
     */
    public int setBlocks(int[][] blocks) {
        for (int[] block: blocks) {
            isFilled[block[INDEX_COLUMN]][block[INDEX_ROW]] = true;
        }

        // Check if any row is filled
        Set<Integer> rowsToDelete = new HashSet<Integer>();
        for (int[] block: blocks) {
            if (isRowFilled(block[INDEX_ROW])) {
                rowsToDelete.add(block[INDEX_ROW]);
            }
        }
        if (rowsToDelete.isEmpty()) {
            return 0;
        }
        int delta = 0;
        for (int r = 0; r < NUM_ROWS; r++) {
            if (rowsToDelete.contains(r)) {
                delta++;
            }
            // Copy the row
            for (int c = 0;  c < NUM_COLUMNS; c++) {
                isFilled[c][r] = r + delta >= NUM_ROWS ? false : isFilled[c][r + delta];
            }
        }
        return rowsToDelete.size();
    }

    public boolean isOnGround(int[][] blocks) {
        for (int[] block: blocks) {
            if (block[INDEX_ROW] - 1 >= NUM_ROWS) {
                continue;
            }
            if (block[INDEX_ROW] <= 0 || isFilled[block[INDEX_COLUMN]][block[INDEX_ROW] - 1]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns if given blocks are inside the stage and has no conflict with existing blocks.
     */
    public boolean canPlaceBlocks(int[][] blocks) {
        for (int[] block: blocks) {
            int row = block[INDEX_ROW];
            int column = block[INDEX_COLUMN];
            if (row < 0 || column < 0 || row >= NUM_ROWS || column >= NUM_COLUMNS || isFilled[column][row]) {
                return false;
            }
        }
        return true;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
        shapeRenderer.translate(getX(), getY(), 0);

        // Border
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(0, 0, CELL_SIZE * NUM_COLUMNS + 2, CELL_SIZE * NUM_ROWS + 2);
        shapeRenderer.end();

        // Background
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        float r = 167f / 255f, g = 138f / 255f, b = 177f /  255f;
        shapeRenderer.setColor(new Color(r, g, b, 1));
        shapeRenderer.rect(1, 1, CELL_SIZE * NUM_COLUMNS, CELL_SIZE * NUM_ROWS);

        //shapeRenderer.setColor(Color.GRAY);
        float rr = 181f / 255f, gr = 79f / 255f, br = 135f / 255f;
        shapeRenderer.setColor(new Color(rr, gr, br, 1));
        //shapeRenderer.setColor(Tetromino.tetromoniColor());
        for (int i = 0; i < NUM_COLUMNS; i++) {
            for (int j = 0; j < NUM_ROWS; j++) {
                if (isFilled[i][j]) {
                    shapeRenderer.rect(i * CELL_SIZE + 1, j * CELL_SIZE + 1, CELL_SIZE, CELL_SIZE);
                }
            }
        }
        shapeRenderer.end();

        batch.begin();
    }

    private boolean isRowFilled(int r) {
        for (int c = 0;  c < NUM_COLUMNS; c++) {
            if (!isFilled[c][r]) {
                return false;
            }
        }
        return true;
    }
}
