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
	protected int mLayout;	
	
	protected RelativeLayout mRootView;
	protected RelativeLayout mDialogView;
	

	private int mBackgroundColor = Color.parseColor("#ffffff");

	public Dialog(Context context, int layout) {
		super(context, android.R.style.Theme_Translucent);
		mLayout = layout;
	}
	
	
//	public Dialog(Context context, int layout, String title, String message) {
//		super(context, android.R.style.Theme_Translucent);
//		mContext = context;
//		mTitle = title;
//		mMessage = message;
//	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
	    setContentView(mLayout);
	    
	    mDialogView = (RelativeLayout)findViewById(R.id.contentDialog);
	    mRootView = (RelativeLayout)findViewById(R.id.dialog_rootView);
	    
	    initializeBackground();
	    
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
	}

	
	public void setBackground(int color) {
		mBackgroundColor = color;
		initializeBackground();
	}
	
	private void initializeBackground() {
		LayerDrawable layer = (LayerDrawable) mDialogView.getBackground();
		GradientDrawable shape = (GradientDrawable) layer.findDrawableByLayerId(R.id.shape_bacground);
		shape.setColor(mBackgroundColor);			
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
