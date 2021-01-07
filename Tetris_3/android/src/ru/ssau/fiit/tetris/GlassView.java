package ru.ssau.fiit.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class GlassView extends View {
    private final int CELL_SIZE = 20;

    private Rect rect;
    private Paint paint;

    private Glass glass;

    public GlassView(Context context) {
        super(context);
        init(context, null);
    }

    public GlassView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Glass getGlass() { return glass; }

    private void init(Context context, @Nullable AttributeSet set) {
        rect = new Rect();
        paint = MyPaint.setPaint(context);
        glass = new Glass(1, 1,
                getResources().getColor(R.color.check_box_color),
                1.0, 1.0);
    }

    public void setGlassSize(int width, int height) {
        this.glass.setWidth(width);
        this.glass.setHeight(height);

        postInvalidate();
    }

    public void setGlassColor(int color) {
        this.glass.setColor(color);

        postInvalidate();
    }

    public void setGlass(Glass glass) {
        this.glass.setWidth(glass.getWidth());
        this.glass.setHeight(glass.getHeight());
        this.glass.setColor(glass.getColor());

        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(glass.getColor());

        for (int i = 0; i < glass.getWidth(); i++) {
            for (int j = 0; j < glass.getHeight(); j++) {
                rect.left = i * CELL_SIZE;
                rect.top = j * CELL_SIZE;
                rect.right = rect.left + CELL_SIZE;
                rect.bottom = rect.top + CELL_SIZE;
                canvas.drawRect(rect, paint);
            }
        }
    }
}
