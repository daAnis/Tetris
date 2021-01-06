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

    public GlassView(Context context) {
        super(context);

        init(null);
    }

    private void init(@Nullable AttributeSet set) {
        rect = new Rect();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(getResources().getColor(R.color.check_box_color));
        paint.setStyle(Paint.Style.STROKE);
        rect.left = 50;
        rect.top = 50;
        rect.right = rect.left + CELL_SIZE;
        rect.bottom = rect.top + CELL_SIZE;
        canvas.drawRect(rect, paint);
    }
}
