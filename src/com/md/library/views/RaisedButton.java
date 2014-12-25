package com.md.library.views;

import com.md.library.R;
import com.md.library.utils.Utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.util.AttributeSet;

public class RaisedButton extends Button {

	private Context mContext;
	
	private int mLeftOffset;
	private int mRightOffset;
	private int mTopOffset;
	private int mBottomOffset;
	
	public RaisedButton(Context context) {
		super(context);
	}

	public RaisedButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public RaisedButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	
	@Override
	protected void onPreInitialize(Context context) {
		super.onPreInitialize(context);
		mButtonHorizontalPadding = 16;
		
		mBackgroundResourceId = R.drawable.background_button_rectangle;
		
		mLeftOffset = Utility.convertDpToPx(context, 6);
		mRightOffset = Utility.convertDpToPx(context, 6);
		mTopOffset = Utility.convertDpToPx(context, 6);
		mBottomOffset = Utility.convertDpToPx(context, 7);
	}
	
	
	@Override
	protected void onDrawCircle(Canvas canvas, float x, float y, float radius, Paint paint) {
		Bitmap output = Bitmap.createBitmap(
				getWidth() - mLeftOffset - mRightOffset, 
				getHeight() - mTopOffset - mBottomOffset, Config.ARGB_8888);

		Canvas tmp = new Canvas(output);
		tmp.drawARGB(0, 0, 0, 0);
		tmp.drawCircle(x, y, radius, paint);
		
		canvas.drawBitmap(output, mTopOffset, mLeftOffset, null);
	}

}
