package ru.ssau.fiit.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

public class FigureEditableView extends FigureView {
    public FigureEditableView(Context context) {
        super(context);
        CELL_SIZE = 150;
    }

    public FigureEditableView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        CELL_SIZE = 150;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public boolean onTouchEvent(MotionEvent event) {
        boolean result = super.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int i = (int) Math.floor(event.getX() / CELL_SIZE);
            if ((i > 3) || (i < 0)) return true;
            int j = (int) Math.floor(event.getY() / CELL_SIZE);
            if ((j > 3) || (j < 0)) return true;
            if (filled[i][j] == 0)
                filled[i][j] = 1;
            else filled[i][j] = 0;
            postInvalidate();
            return true;
        }
        return result;
    }
}
