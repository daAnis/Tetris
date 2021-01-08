package ru.ssau.fiit.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class FigureView extends View {

    private final int CELL_SIZE = 150;

    private Rect rect;
    private Paint paint;

    private byte [][] filled;

    public FigureView(Context context) {
        super(context);
        init(context, null);
    }

    public FigureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init (Context context, @Nullable AttributeSet set) {
        rect = new Rect();
        paint = MyPaint.setPaint(context);
        paint.setColor(getResources().getColor(R.color.check_box_color));

        filled = new byte[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                filled[i][j] = 0;
            }
        }
    }

    public void setFigure(Figure figure) {
        byte [][] a = figure.getStructure();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                filled[i][j] = a[i][j];
            }
        }
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (filled[i][j] == 0)
                    paint.setStyle(Paint.Style.STROKE);
                else
                    paint.setStyle(Paint.Style.FILL_AND_STROKE);
                rect.left = i * CELL_SIZE;
                rect.top = j * CELL_SIZE;
                rect.right = rect.left + CELL_SIZE;
                rect.bottom = rect.top + CELL_SIZE;
                canvas.drawRect(rect, paint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = super.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int i = (int) Math.floor(event.getX() / CELL_SIZE);
            int j = (int) Math.floor(event.getY() / CELL_SIZE);
            if (filled[i][j] == 0)
                filled[i][j] = 1;
            else filled[i][j] = 0;
            postInvalidate();
            return true;
        }
        return result;
    }
}
