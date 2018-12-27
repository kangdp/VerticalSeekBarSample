package com.kdp.seekbar.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.kdp.seekbar.R;

/***
 * @author kdp
 * @date 2018/12/26 15:43
 * @description
 */
public class VerticalSeekbar extends android.support.v7.widget.AppCompatSeekBar {
    private OnSeekBarChangeListener onSeekBarChangeListener;
    private int bgColor;
    private int bgRadius;
    private int progressColor;
    private int thumbShadowColor;
    private int thumbShadowRadius;
    private int thumbColor;
    private int thumbStrokeWidth;
    private int thumbStrokeColor;
    private int progressWidth;
    private int width, height;
    private int seekBarHeight;
    float percent;
    private Paint mPaint;

    public VerticalSeekbar(Context context) {
        this(context, null);
    }

    public VerticalSeekbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalSeekbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.VerticalSeekbar, defStyleAttr, 0);
        bgColor = typedArray.getColor(R.styleable.VerticalSeekbar_bgColor, 0xFFEEEEEE);
        bgRadius = (int) typedArray.getDimension(R.styleable.VerticalSeekbar_bgRadius,0);
        progressColor = typedArray.getColor(R.styleable.VerticalSeekbar_progressColor, 0xFF2F9DE3);
        progressWidth = (int) typedArray.getDimension(R.styleable.VerticalSeekbar_pregressWidth, 10);
        thumbShadowColor = typedArray.getColor(R.styleable.VerticalSeekbar_thumbShadowColor, 0x00000000);
        thumbShadowRadius = (int) typedArray.getDimension(R.styleable.VerticalSeekbar_thumbShadowRadius, 0);
        thumbStrokeWidth = (int) typedArray.getDimension(R.styleable.VerticalSeekbar_thumbStrokeWidth, 0);
        thumbStrokeColor = typedArray.getColor(R.styleable.VerticalSeekbar_thumbStrokeColor, 0x00000000);
        thumbColor = typedArray.getColor(R.styleable.VerticalSeekbar_thumbColor, 0xFF2F9DE3);
        typedArray.recycle();
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        percent = (float) getProgress() / getMax();
        if (thumbShadowRadius > 0)
            setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        seekBarHeight = height - width;
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        drawSeekbarBackground(canvas);
        drawSeekbarProgress(canvas);
        drawThumb(canvas);

    }

    private void drawThumb(Canvas canvas) {


        //画外圆
        mPaint.setColor(thumbStrokeColor);
        //设置阴影
        if (thumbShadowRadius > 0) {
            mPaint.setShadowLayer(thumbShadowRadius, 0, 0, thumbShadowColor);
        }
        if (thumbStrokeWidth > 0) {
            canvas.drawCircle(width / 2, seekBarHeight * (1 - percent) + width / 2, width / 2 - thumbShadowRadius, mPaint);
        }
        //画内圆
        mPaint.setColor(thumbColor);
        mPaint.clearShadowLayer();
        canvas.drawCircle(width / 2, seekBarHeight * (1 - percent) + width / 2, width / 2 - thumbStrokeWidth - thumbShadowRadius, mPaint);

    }

    private void drawSeekbarBackground(Canvas canvas) {
        if (getProgress() < getMax()) {
            mPaint.setColor(bgColor);
            canvas.drawRoundRect(new RectF(width / 2 - progressWidth / 2, width / 2, width / 2 + progressWidth / 2, seekBarHeight * (1 - percent) + width / 2), bgRadius, bgRadius, mPaint);
        }
    }

    private void drawSeekbarProgress(Canvas canvas) {
        mPaint.setColor(progressColor);
        canvas.drawRoundRect(new RectF(width / 2 - progressWidth / 2, seekBarHeight * (1 - percent) + width / 2, width / 2 + progressWidth / 2, height - width / 2), bgRadius, bgRadius, mPaint);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                int downY = (int) event.getY();
                percent = 1 - (float) (downY - width / 2) / seekBarHeight;
                if (percent >1) percent =1;
                setProgress((int) (getMax() * percent));
                if (onSeekBarChangeListener!=null)
                    onSeekBarChangeListener.onProgressChanged(this,getProgress(),false);
                break;
        }
        return true;
    }

    @Override
    public void setOnSeekBarChangeListener(OnSeekBarChangeListener l) {
        this.onSeekBarChangeListener = l;
    }
}
