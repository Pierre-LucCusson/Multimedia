package com.example.plcus.multimedia;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

//source: http://android-er.blogspot.ca/2015/02/create-audio-visualizer-for-mediaplayer.html?showComment=1484753490893#c4271957864257330230

public class VisualizerView extends View {

    private byte[] bytes;
    private float[] points;
    private Rect rectangle = new Rect();
    private Paint paint = new Paint();

    public VisualizerView(Context context) {
        super(context);
        init();
    }

    public VisualizerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VisualizerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        bytes = null;
        paint.setStrokeWidth(1f);
        paint.setAntiAlias(true);
        paint.setColor(Color.rgb(0, 128, 255));
    }

    public void updateVisualizer(byte[] bytes) {
        this.bytes = bytes;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bytes == null) {
            return;
        }
        if (points == null || points.length < bytes.length * 4) {
            points = new float[bytes.length * 4];
        }
        rectangle.set(0, 0, getWidth(), getHeight());
        for (int i = 0; i < bytes.length - 1; i++) {
            points[i * 4] = rectangle.width() * i / (bytes.length - 1);
            points[i * 4 + 1] = rectangle.height() / 2
                    + ((byte) (bytes[i] + 128)) * (rectangle.height() / 2) / 128;
            points[i * 4 + 2] = rectangle.width() * (i + 1) / (bytes.length - 1);
            points[i * 4 + 3] = rectangle.height() / 2
                    + ((byte) (bytes[i + 1] + 128)) * (rectangle.height() / 2)
                    / 128;
        }
        canvas.drawLines(points, paint);
    }

}