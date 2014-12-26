package com.md.library.views;

import com.md.library.R;
import com.md.library.utils.ResourceUtility;
import com.md.library.utils.Utility;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

public class RippleView extends RelativeLayout {
	protected final static String ANDROIDXML = "http://schemas.android.com/apk/res/android";

	private int FRAME_RATE = 10;
	private int mRadiusMax = 0;
	private int mPaintAlpha = 90;
	private int mDuration = 400;
	private int mTimer = 0;
	
	private int mWidth;
	private int mHeight;
	protected float x = -1;
	protected float y = -1;
	
	private boolean mZoom = false;
	private int mRippleColor;
	private boolean mClickAfterRipple = false;
	
	protected int mBackgroundColor = -1;
	
	private ScaleAnimation mScaleAnimation;
	private boolean mAnimationRunning = false;
	private Paint mPaint;
	
	private GestureDetector mGestureDetector;
	private Handler mHandler;
	
	private Runnable mRunnable = new Runnable() {

		@Override
		public void run() {
			invalidate();
		}
	};
	
	
	public RippleView(Context context) {
		super(context);
	}
	
	
	public RippleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}
	
	
	public RippleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}
	
	public void init(Context context, AttributeSet attrs) {
		onPreInitialize(context);
		onInitAttributes(context, attrs);
		onInit(context);
		onPostInitialize(context);
	}
	
	
	protected void onInitAttributes(Context context, AttributeSet attrs) {	
		TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RippleView, 0, 0);
		
    	mBackgroundColor = ResourceUtility.getAndroidInstance().getAttributeColor(context, attrs, "background", android.R.color.white);
		mRippleColor = typedArray.getColor(R.styleable.RippleView_rippleColor, Utility.makeAlphaColor(mBackgroundColor, 150));
		mZoom = ResourceUtility.getMaterialInstance().getAttributeBooleanValue(attrs, "zoom", mZoom);
		mClickAfterRipple = ResourceUtility.getMaterialInstance().getAttributeBooleanValue(attrs, "clickAfterRipple", mClickAfterRipple);
		mPaintAlpha = ResourceUtility.getMaterialInstance().getAttributeIntValue(attrs, "rippleAlpha", mPaintAlpha);
		mDuration = ResourceUtility.getMaterialInstance().getAttributeIntValue(attrs, "duration", mDuration);
		
		onPostInitAttributes(context, attrs, typedArray);
		typedArray.recycle();
	}
	
	protected void onInit(Context context) {
		if(isInEditMode()) {
			return;	
		}
		
		mHandler = new Handler();
        
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mRippleColor);
        mPaint.setAlpha(mPaintAlpha);
        setWillNotDraw(false);
        setDrawingCacheEnabled(true);
        
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener()
        {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e)
            {
                return true;
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e)
            {
                return true;
            }
        });
        
        onPostInit(context);
	}
	

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		if(mAnimationRunning) {
			
			if(mDuration <= mTimer * FRAME_RATE) {
				mAnimationRunning = false;
				mTimer = 0;
				canvas.restore();
				invalidate();
				return;
			}else {
				mHandler.postDelayed(mRunnable, FRAME_RATE);
			}
			
			if(mTimer == 0) canvas.save();
						
			float progress = (mTimer * FRAME_RATE * 1.0f) / mDuration;
			int alpha = (int) Math.round(mPaintAlpha * progress);
			mPaint.setAlpha(mPaintAlpha - alpha);
			float radius = (mRadiusMax * (((float) mTimer * FRAME_RATE) / mDuration));
			onDrawCircle(canvas, x, y, radius, mPaint);
			mTimer++;
		}
	}
	
	
	protected void onDrawCircle(Canvas canvas, float x, float y, float radius, Paint paint) {
        canvas.drawCircle(x, y, radius, paint);
	}
	
	
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event)
    {
       return true;
    }

	@Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mRadiusMax = Math.max(mWidth, mHeight) ;

        mScaleAnimation = new ScaleAnimation(1.0f, 1.03f, 1.0f, 1.03f);
        mScaleAnimation.setDuration(mDuration/2);
        mScaleAnimation.setRepeatMode(Animation.REVERSE);
        mScaleAnimation.setRepeatCount(1);
    }
    
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	if(mGestureDetector.onTouchEvent(event) && !mAnimationRunning) {
    		if(mZoom) startAnimation(mScaleAnimation);
    		
    		x = event.getX();
    		y = event.getY();
    		
    		mAnimationRunning = true;

    		invalidate();
    		performClick();
    	}
    	dispatchEventToChildren(event);
    	return true;
    }
    
    
    private void dispatchEventToChildren(MotionEvent event) {
    	for(int i=0; i < getChildCount(); i++) {
    		getChildAt(i).onTouchEvent(event);
    	}
    }
    
    
    @Override
    public void setOnClickListener(OnClickListener l) {
    	
    	final OnClickListener listener = l;
    	super.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
		          Handler  myHandler = new Handler();
		          myHandler.postDelayed( new DelayListenerRunnable(view, listener), (long)mDuration);
			}
    	});
    }
    
    
    public static class DelayListenerRunnable implements Runnable {
    	
    	private View mView;
    	private OnClickListener mListener;
    	public DelayListenerRunnable(final View v, final OnClickListener l) {
    		mView = v;
    		mListener = l;
    	}
    	
		@Override
		public void run() {
			mListener.onClick(mView);
		}    	
    }
    
	protected void onPreInitialize(Context context) {}
	protected void onPostInitialize(Context context) {}
	protected void onPostInitAttributes(Context context, AttributeSet attrs, TypedArray typedArray) {}
	protected void onPostInit(Context context) {}
}
