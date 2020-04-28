package com.example.drawingapp;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class PaintView extends View {

    private Paint mPAint;
    private Canvas mCanvas;
    private Bitmap mbitmap;

    public PaintView(Context context, AttributeSet attributeSet) {
        super(context,attributeSet);
        mPAint =new Paint();
        mPAint.setAntiAlias(true);
        mPAint.setDither(true);
        mPAint.setColor(Color.BLUE);
        mPAint.setStyle(Paint.Style.STROKE);
        mPAint.setStrokeJoin(Paint.Join.ROUND);
        mPAint.setStrokeCap(Paint.Cap.ROUND);
        mPAint.setXfermode(null);
        mPAint.setAlpha(0xff);
    }
    public void init(DisplayMetrics metrics){
        int height = (int) (metrics.heightPixels * 0.8);
        int width = metrics.widthPixels;

        mbitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mbitmap);
    }

    private ArrayList <MainActivity.FingerPath> paths = new ArrayList<>();
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);
    @Override
    protected void onDraw(Canvas canvas){
        canvas.save();
        mCanvas.drawColor(Color.WHITE);
        for(MainActivity.FingerPath fp : paths) {
            mPAint.setColor(fp.color);
            mPAint.setStrokeWidth(fp.strokeWidth);
            mPAint.setMaskFilter(null);

            mCanvas.drawPath(fp.path, mPAint);
        }
        canvas.drawBitmap(mbitmap, 0,0,mBitmapPaint);
        canvas.restore();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                touchStart(x,y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x,y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                invalidate();
                break;

        }
        return true;
    }

    private void touchUp()
    {
        mPath.lineTo(mx,my);
    }

    private static final float TOUCH_TOLERANCE = 4;

    private void touchMove(float x,float y)
    {
        float dx= Math.abs(x-mx);
        float dy= Math.abs(y-my);
        if(dx>= TOUCH_TOLERANCE || dy>= TOUCH_TOLERANCE){
            mPath.quadTo(mx,my,(x+mx)/2,(y+my)/2);
            mx=x;
            my=y;
        }
    }

    private Path mPath;
    private float mx;
    private float my;
    public int brushColor = Color.BLACK;
    public int brushSize = 10;
    private void touchStart(float x, float y){
        mPath = new Path();
        MainActivity.FingerPath fp = new MainActivity.FingerPath(brushColor, brushSize ,mPath);
        paths.add(fp);

        mPath.reset();
        mPath.moveTo(x,y);

        mx=x;
        my=y;
    }

    public void clear(){
        paths.clear();
        invalidate();
    }

}
