package com.md.library.views;

import com.md.library.R;
import com.md.library.utils.Utility;

import android.content.Context;
import android.util.AttributeSet;

public class FlatButton extends Button {

	public FlatButton(Context context) {
		super(context);
	}
	
	public FlatButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public FlatButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	
	@Override
	protected void onPreInitialize(Context context) {
		super.onPreInitialize(context);		
		mBackgroundResourceId = R.drawable.flat_button_background;
	}
}

