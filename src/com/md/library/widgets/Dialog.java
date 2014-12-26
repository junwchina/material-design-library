package com.md.library.widgets;


import com.md.library.R;
import com.md.library.views.FlatButton;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;
import android.widget.TextView;


public abstract class Dialog extends android.app.Dialog {
	
	protected Context mContext;
	private String mTitle;
	private String mMessage;
	
	private RelativeLayout mRootView;
	private RelativeLayout mDialogView;
	
	private TextView mTitleView;
	private TextView mMessageView;
	
	private FlatButton mAcceptButton;
	private FlatButton mCancelButton;
	
	View.OnClickListener onAcceptButtonClickListener;
	View.OnClickListener onCancelButtonClickListener;


	public Dialog(Context context,String title, String message) {
		super(context, android.R.style.Theme_Translucent);
		mContext = context;
		mTitle = title;
		mMessage = message;
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.dialog);
	    
	    mDialogView = (RelativeLayout)findViewById(R.id.contentDialog);
	    
		LayerDrawable layer = (LayerDrawable) mDialogView.getBackground();
		GradientDrawable shape = (GradientDrawable) layer.findDrawableByLayerId(R.id.shape_bacground);
		shape.setColor(Color.parseColor("#ffffff"));			

	    mRootView = (RelativeLayout)findViewById(R.id.dialog_rootView);
	    mRootView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getX() < mRootView.getLeft() 
						|| event.getX() >mRootView.getRight()
						|| event.getY() > mRootView.getBottom() 
						|| event.getY() < mRootView.getTop()) {
					dismiss();
				}
				return false;
			}
		});
	    
	    mTitleView = (TextView) findViewById(R.id.title);
	    setTitle(mTitle);
	    
	    mMessageView = (TextView) findViewById(R.id.message);
	    setMessage(mMessage);
	    
	    
	    mAcceptButton = (FlatButton) findViewById(R.id.button_accept);
	    mCancelButton = (FlatButton)findViewById(R.id.button_cancel);
	    
	    mAcceptButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
				if(onAcceptButtonClickListener != null)
			    	onAcceptButtonClickListener.onClick(v);
			}
		});
	    
	    mCancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
				if(onCancelButtonClickListener != null)
					onCancelButtonClickListener.onClick(v);
			}
		});
	}
	
	
	public void setTitle(String title) {
		mTitleView.setText(title);
	}
	
	public void setMessage(String message) {
		mMessageView.setText(message);
	}

	
	public void setOnAcceptButtonClickListener(View.OnClickListener onAcceptButtonClickListener) {
		this.onAcceptButtonClickListener = onAcceptButtonClickListener;
		if(mAcceptButton != null)
			mAcceptButton.setOnClickListener(onAcceptButtonClickListener);
	}

	public void setOnCancelButtonClickListener( View.OnClickListener onCancelButtonClickListener) {
		this.onCancelButtonClickListener = onCancelButtonClickListener;
		if(mCancelButton != null)
			mCancelButton.setOnClickListener(onCancelButtonClickListener);
	}
}
