package com.mikekuzn.buttonfeedbacktest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class GraphView extends View {
    Paint paint = new Paint();
    short[] mValues = new short[0];

    public GraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(Color.BLACK);
        paint.setStrokeWidth(3);
    }

    public void SetValues(short[] values) {
        mValues = values;
        this.invalidate();      // Run update
    }

    private static final int divider =  0x3000 / 127;

    @Override
    public void onDraw(Canvas canvas) {
        final int MARGIN = 10;
        int width = getWidth();
        int effectiveWidth = width;
        int height = getHeight();
        super.onDraw(canvas);
        paint.setColor(Color.WHITE);
        canvas.drawLine(MARGIN, MARGIN, MARGIN, height - MARGIN, paint);
        canvas.drawLine(MARGIN, 150, width - MARGIN, 150, paint);
        paint.setColor(Color.GREEN);
        float step = (float)effectiveWidth / mValues.length;
        for (int i = 0; i < mValues.length - 1; ++i) {
                    canvas.drawLine(
                            MARGIN + step * i,
                            height / 2 - mValues[i] / divider,
                            MARGIN + step * (i + 1),
                            height / 2 - mValues[i + 1] / divider,
                    paint);
        }
    }
}