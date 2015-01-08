package com.md.library.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.md.library.R;

public class CircleProgressBarView extends View {
	private Paint paint;
	private RectF oval;
	
	// 弧度
	private float maxRadian;
	private float minRadian;
	// 半径
	private float radius;
	// 弧宽
	private int racWidth;
	private float speed;
	// 方向 ： -1为收缩，1为拉伸
	private int direct = -1;	
	private int offAngle;
	
	// 初始化为逆时针
	private float antiAngle;
	private float nowAngle;
	private float nowRadian;
	
	// 极限时的长度
	private float stepLength;
	
	// 
	private float a;
	
	private Handler handler = new Handler();  
	  
    private Runnable myRunnable= new Runnable() {    
        public void run() {  
             invalidate();
             handler.postDelayed(myRunnable, 10);
        }  
    }; 
    
    private void LOG(String msg) {
    	Log.d("init", msg);
    }
    
	public CircleProgressBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = new Paint();
		oval = new RectF();

		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar);
		racWidth = array.getDimensionPixelSize(R.styleable.CircleProgressBar_LineWidth, 10);
		speed = array.getFloat(R.styleable.CircleProgressBar_Speed, 1.0f);
		maxRadian = array.getInt(R.styleable.CircleProgressBar_MaxRadian, 270);
		minRadian = array.getInt(R.styleable.CircleProgressBar_MinRadian, 20);
		offAngle = array.getInt(R.styleable.CircleProgressBar_offAngle, 0);
		
		antiAngle = (maxRadian - 360)/2 - 90;
		nowAngle = antiAngle;
		nowRadian = -maxRadian;
		
		nowRadian = maxRadian;
		float disAngle = 360 - maxRadian;
		stepLength = (disAngle + minRadian)/2;
		a = -(minRadian-maxRadian)/stepLength/stepLength;
		
		paint.setAntiAlias(true);// 设置是否抗锯齿
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);// 帮助消除锯齿
		paint.setColor(array.getColor(R.styleable.CircleProgressBar_PaintColor, Color.WHITE));// 设置画笔颜色
		paint.setStrokeWidth(racWidth);// 设置画笔宽度
		paint.setStyle(Paint.Style.STROKE);// 设置中空的样式
		int type = array.getInt(R.styleable.CircleProgressBar_CapType,0);
		switch (type) {
		case 0:
			paint.setStrokeCap(Cap.BUTT);
			break;
		case 1:
			paint.setStrokeCap(Cap.ROUND);
			break;
		case 2:
			paint.setStrokeCap(Cap.SQUARE);
			break;
		default:
			break;
		}
		
		array.recycle();
		
		LOG("max : "+maxRadian);
		LOG("min : "+minRadian);
		LOG("antiAngle : "+antiAngle);
		LOG("a : "+a);
		LOG("stepLength" + stepLength);
		
		handler.postDelayed(myRunnable, 0);
		
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		radius = getMeasuredWidth()/2;
		oval.set(racWidth/2+5, racWidth/2+5,radius*2-racWidth-5,radius*2-racWidth-5);
		Log.d("onLayout", "onLayout...");
    }
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		switch (direct) {
		case -1:
			anitiRotate(canvas);
			break;
		case 1:
			clockwishRotate(canvas);
			break;
		}
	}
	
	private void anitiRotate(Canvas canvas) {
		
		if (nowAngle-minRadian/2 + 90 <=0) {
			float dis = nowAngle - antiAngle;
			nowRadian = a*(dis - stepLength*2)*dis + maxRadian;
			canvas.drawArc(oval, nowAngle + offAngle, -nowRadian, false, paint);
//			LOG("a : "+a);
//			LOG("anit nowAngle : "+nowAngle);
//			LOG("nowRadian"+nowRadian);
			nowAngle += speed;
		}else {
			direct = 1;
			
			clockwishRotate(canvas);
		}
	}
	
	private void clockwishRotate(Canvas canvas) {
		float dis = nowAngle - antiAngle;
		if (stepLength*2 - dis >=0) {
			nowRadian = -a*(dis-stepLength)*(dis - stepLength*3) + minRadian;
			canvas.drawArc(oval, nowAngle-minRadian + offAngle, nowRadian, false, paint);
//			LOG("nowAngle : "+nowAngle + "    "+"nowRadian"+nowRadian);
			nowAngle += speed;
		}else {
			direct = -1;
			nowAngle = antiAngle;
			anitiRotate(canvas);
		}
	}
}
