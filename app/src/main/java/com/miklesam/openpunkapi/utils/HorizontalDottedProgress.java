package com.miklesam.openpunkapi.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

import com.miklesam.openpunkapi.R;

public class HorizontalDottedProgress extends View {

    Bitmap bitmapSource;
    Bitmap bitmapSec;

    private RectF rect = new RectF();
    Paint paint = new Paint();
    //actual dot radius
    private int mDotRadius = 10;

    //Bounced Dot Radius
    private int mBounceDotRadius = 16;

    //to get identified in which position dot has to bounce
    private int  mDotPosition;

    //specify how many dots you need in a progressbar
    private int mDotAmount = 5;

    public HorizontalDottedProgress(Context context) {
        super(context);
    }

    public HorizontalDottedProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalDottedProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //Method to draw your customized dot on the canvas
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);



        //set the color for the dot that you want to draw

        //canvas.drawBitmap(b, 0, 0, paint);

        createDot(canvas,paint,bitmapSource,bitmapSec);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        rect.set(0f, 0f, getWidth(), getHeight());
        paint.setColor(getResources().getColor(R.color.colorAccent));
        //function to create dot

        bitmapSource = BitmapFactory.decodeResource(getResources(), R.drawable.beer16_empty);
        bitmapSec = BitmapFactory.decodeResource(getResources(), R.drawable.beer16);
        //Animation called when attaching to the window, i.e to your screen
        startAnimation();
    }



    private void createDot(Canvas canvas, Paint paint,Bitmap b,Bitmap b2) {

        //here i have setted progress bar with 10 dots , so repeat and wnen i = mDotPosition  then increase the radius of dot i.e mBounceDotRadius
        for(int i = 0; i < mDotAmount; i++ ){
            if(i == mDotPosition){
                //canvas.drawRoundRect(rect,getWidth(),getHeight(),paint);
                //canvas.drawCircle(10+(i*20), mBounceDotRadius, mBounceDotRadius, paint);
                canvas.drawBitmap(b2, 30+(i*75), mBounceDotRadius, paint);


            }else {
                canvas.drawBitmap(b, 30+(i*75), mBounceDotRadius, paint);
                //canvas.drawCircle(30+(i*30), mBounceDotRadius, mDotRadius, paint);
            }
        }


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width;
        int height;

        //calculate the view width
        int calculatedWidth = (75*(mDotAmount+1));

        width = calculatedWidth;
        height = (65*2);



        //MUST CALL THIS
        setMeasuredDimension(width, height);
    }

    private void startAnimation() {
        BounceAnimation bounceAnimation = new BounceAnimation();
        bounceAnimation.setDuration(150);
        bounceAnimation.setRepeatCount(Animation.INFINITE);
        bounceAnimation.setInterpolator(new LinearInterpolator());
        bounceAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                mDotPosition++;
                //when mDotPosition == mDotAmount , then start again applying animation from 0th positon , i.e  mDotPosition = 0;
                if (mDotPosition == mDotAmount) {
                    mDotPosition = 0;
                }


            }
        });
        startAnimation(bounceAnimation);
    }


    private class BounceAnimation extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            //call invalidate to redraw your view againg.
            invalidate();
        }
    }
}