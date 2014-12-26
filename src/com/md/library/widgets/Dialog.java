package com.md.library.widgets;


import com.md.library.R;
import com.md.library.utils.Utility;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
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

	private int mBackgroundColor = Color.parseColor("#ffffff");

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
	    
	    initializeViews();
	    initializeBackground();
	    initializeEvents();
	    
	    setTitle(mTitle);
	    setMessage(mMessage);
	}
	
	
	private void initializeViews() {
	    mDialogView = (RelativeLayout)findViewById(R.id.contentDialog);
	    mRootView = (RelativeLayout)findViewById(R.id.dialog_rootView);
	    mTitleView = (TextView) findViewById(R.id.title);
	    mMessageView = (TextView) findViewById(R.id.message);
	    mAcceptButton = (FlatButton) findViewById(R.id.button_accept);
	    mCancelButton = (FlatButton)findViewById(R.id.button_cancel);
	}
	
	private void initializeBackground() {
		LayerDrawable layer = (LayerDrawable) mDialogView.getBackground();
		GradientDrawable shape = (GradientDrawable) layer.findDrawableByLayerId(R.id.shape_bacground);
		shape.setColor(mBackgroundColor);			
	}
	
	private void initializeEvents() {
		mRootView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Utility.logD("onTouchEvent");
				if (event.getX() < mDialogView.getLeft() 
						|| event.getX() >mDialogView.getRight()
						|| event.getY() > mDialogView.getBottom() 
						|| event.getY() < mDialogView.getTop()) {
					dismiss();
				}
				return false;
			}
		});
		
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
		if(null == title || title.length() == 0) {
			mTitleView.setVisibility(View.GONE);
		} else {
			mTitleView.setVisibility(View.VISIBLE);
			mTitleView.setText(title);
		}
	}
	
	public void setMessage(String message) {
		if(null == message || message.length() == 0) {
			mMessageView.setVisibility(View.GONE);
		}else {
			mMessageView.setVisibility(View.VISIBLE);
			mMessageView.setText(message);
		}
	}
	
	
	public void setAcceptText(String text) {
		mAcceptButton.setText(text);
	}

	public void setCancelText(String text) {
		mCancelButton.setText(text);
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
	
	
	@Override
	public void show() {
		super.show();
		mDialogView.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.dialog_main_show_amination));
		mRootView.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.dialog_root_show_amin));
	}
	
	
	@Override
	public void dismiss() {
		Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.dialog_main_hide_amination);
		anim.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				mDialogView.post(new Runnable() {
					@Override
					public void run() {
						mRootView.setVisibility(View.GONE);
			        	Dialog.super.dismiss();
			        }
			    });
				
			}
		});
		Animation backAnim = AnimationUtils.loadAnimation(mContext, R.anim.dialog_root_hide_amin);
		
		mDialogView.startAnimation(anim);
		mRootView.startAnimation(backAnim);
	}
}
