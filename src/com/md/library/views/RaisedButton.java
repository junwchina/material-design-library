package com.md.library.views;

import com.md.library.R;

import android.content.Context;
import android.util.AttributeSet;

public class RaisedButton extends Button {

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
	protected void onPreInitialize() {
		super.onPreInitialize();
		mBackgroundResourceId = R.drawable.background_button_rectangle;
	}
	
}
