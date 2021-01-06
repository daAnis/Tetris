package ru.ssau.fiit.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class GlassView extends View {
    private int width;
    private int height;
    private int color = getResources().getColor(R.color.check_box_color);

    private final int CELL_WIDTH = 10;
    private final int CELL_HEIGHT = 10;
    Paint paint = new Paint();

    public GlassView(Context context) {
        super(context);
    }

    public GlassView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.width = widthMeasureSpec;
        this.height = heightMeasureSpec;
        //this.color = полученный цвет из элемента цвет
        super.onMeasure(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(color);
        Rect r = new Rect();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                r.set(i * CELL_WIDTH, j * CELL_HEIGHT,
                        i * CELL_WIDTH + CELL_WIDTH,
                        j * CELL_HEIGHT + CELL_HEIGHT);
                canvas.drawRect(r, paint);
            }
        }
    }
}
