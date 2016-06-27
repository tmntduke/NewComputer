package com.example.tmnt.newcomputer.DView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.tmnt.newcomputer.R;

import java.text.DecimalFormat;

/**
 * 进度条
 * Created by tmnt on 2016/5/21.
 */
public class PlanBar extends View {
    private float progess = 0;
    private int count, index;

    private float positionX, positionY, strochWidth;

    private float px, py;
    private float mPercent;

    private Paint outPaint;
    private Paint insidePaint;


    private float heightPX, widthPX;

    private Context cotext;

    private DecimalFormat fnum = new DecimalFormat("#.0");

    private static final String TAG = "PlanBar";

    public PlanBar(Context context) {
        super(context);
        this.cotext = context;
        createAttr(context, null);
        init(context);
    }

    public PlanBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        createAttr(context, attrs);
        this.cotext = context;
        init(context);
    }

    public float getProgess() {
        return progess;
    }

    public void setProgess(float progess) {
        this.progess = progess;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureHeight(int heightMeasureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else if (mode == MeasureSpec.AT_MOST) {
            result = 200;
            result = Math.min(size, 200);
        }
        return result;
    }

    private int measureWidth(int widthMeasureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else if (mode == MeasureSpec.AT_MOST) {
            result = 200;
            result = Math.min(size, 200);
        }
        return result;
    }

    public void createAttr(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.PlanBar);
        positionX = typedArray.getDimension(R.styleable.PlanBar_leftPositionX, 15);
        positionY = typedArray.getDimension(R.styleable.PlanBar_leftPositionY, 15);
        strochWidth = typedArray.getDimension(R.styleable.PlanBar_strochWidth, 15);
    }

    private void init(Context context) {
        outPaint = new Paint();
        outPaint.setAntiAlias(true);
        outPaint.setStrokeCap(Paint.Cap.ROUND);
        outPaint.setColor(Color.WHITE);
        outPaint.setStyle(Paint.Style.FILL);

        insidePaint = new Paint();
        insidePaint.setAntiAlias(true);
        insidePaint.setStrokeCap(Paint.Cap.ROUND);
        insidePaint.setStyle(Paint.Style.FILL);
        insidePaint.setStrokeWidth(strochWidth);
        insidePaint.setColor(getResources().getColor(R.color.colorPrimary));

    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawCircle(positionX / 3, getMeasuredHeight() / 4 + positionY / 3, getMeasuredHeight() / 4, outPaint);
        canvas.drawRect(positionX / 3, positionY / 3, getMeasuredWidth() / 3 * 2 + positionX / 3, getMeasuredHeight() / 2 + positionY / 3, outPaint);
        canvas.drawCircle(getMeasuredWidth() / 3 * 2 + positionX / 3, getMeasuredHeight() / 4 + positionY / 3, getMeasuredHeight() / 4, outPaint);

        canvas.drawLine(positionX / 3, getMeasuredHeight() / 4 + positionY / 3, 45 + progess, getMeasuredHeight() / 4 + positionY / 3, insidePaint);

    }

    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    public void update(int progess) {
        this.progess = progess;
        invalidate();
    }


}
