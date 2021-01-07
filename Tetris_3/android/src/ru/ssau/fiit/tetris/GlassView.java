package ru.ssau.fiit.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class GlassView extends View {
    private final int CELL_SIZE = 100;

    private Rect rect;
    private Paint paint;

    private Glass glass;

    public GlassView(Context context) {
        super(context);

        init(null);
    }

    private void init(@Nullable AttributeSet set) {
        rect = new Rect();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        glass = new Glass(1, 1,
                getResources().getColor(R.color.check_box_color),
                1.0, 1.0);
    }

    public void setGlass (Glass glass) {
        this.glass.setWidth(glass.getWidth());
        this.glass.setHeight(glass.getHeight());
        this.glass.setColor(glass.getColor());
        this.glass.setSpeed_k(glass.getSpeed_k());
        this.glass.setPoints_k(glass.getPoints_k());

        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(glass.getColor());
        paint.setStyle(Paint.Style.STROKE);
        
        rect.left = 0;
        rect.top = 0;
        rect.right = rect.left + CELL_SIZE;
        rect.bottom = rect.top + CELL_SIZE;
        canvas.drawRect(rect, paint);
    }
}
