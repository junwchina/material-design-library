package com.md.library.views;

import com.md.library.R;
import com.md.library.utils.Utility;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;

public abstract class Button extends RippleView {
	
	protected TextView mButtonTextView;
	protected int mButtonTextColor;
	protected float mButtonTextSize;
	protected String mButtonText;
	
	protected int mButtonHeight;
	protected int mButtonHorizontalMargin;
	protected int mButtonVerticalMargin;
	protected int mButtonHorizontalPadding;
	
	protected int mDefaultTextSize;
	protected int mBackgroundResourceId;

	
	public Button(Context context) {
		super(context);
	}
	
	public Button(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public Button(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onPreInitialize() {
		mButtonHeight = 36;
		mButtonHorizontalMargin = 4;		// 4dp
		mButtonVerticalMargin = 12;
		mButtonHorizontalPadding = 8;
		mDefaultTextSize = 14;
		mBackgroundResourceId = -1;
	}
	
	
	@Override
	protected void onPostInitAttributes(Context context, AttributeSet attrs, TypedArray typedArray) {
		super.onPostInitAttributes(context, attrs, typedArray);
		mButtonTextColor = typedArray.getColor(R.styleable.RippleView_textColor, android.R.color.primary_text_dark);
		mButtonTextSize = typedArray.getDimension(R.styleable.RippleView_textSize, -1);
		mButtonText = typedArray.getString(R.styleable.RippleView_text);
	}
	
	
	@Override
	protected void onInit(Context context) {
		super.onInit(context);
		
		if(mBackgroundResourceId != -1) {
			setBackgroundResource(mBackgroundResourceId);
		}
//		setBackgroundColor(mBackgroundColor);

		mButtonTextView = new TextView(context);

		if(null != mButtonText) {
			mButtonTextView.setText(mButtonText);
			mButtonTextView.setTypeface(null, Typeface.BOLD);
			mButtonTextView.setTextColor(mButtonTextColor);			
		}
		
		if(null != mButtonText) {
			if(-1 == mButtonTextSize)
				mButtonTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mButtonTextSize);
			else {
				mButtonTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mDefaultTextSize);
			}
		}
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, Utility.convertDpToPx(context, mButtonHeight));
		params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		mButtonTextView.setGravity(Gravity.CENTER);
		mButtonTextView.setLayoutParams(params);
		
		int hPadding = Utility.convertDpToPx(context, mButtonHorizontalPadding);
		mButtonTextView.setPadding(hPadding, 0, hPadding, 0);
		
		addView(mButtonTextView);
	}
	
	
	@Override
	public void setBackgroundColor(int color) {
		try {
			LayerDrawable layer = (LayerDrawable) getBackground();
			GradientDrawable shape = (GradientDrawable) layer.findDrawableByLayerId(R.id.shape_bacground);
			shape.setColor(color);			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}