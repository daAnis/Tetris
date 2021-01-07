package ru.ssau.fiit.tetris;

import android.content.Context;
import android.graphics.Paint;

public class MyPaint {

    public static Paint setPaint(Context context) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(2 * context.getResources().getDisplayMetrics().density);
        paint.setStyle(Paint.Style.STROKE);
        return paint;
    }
}
