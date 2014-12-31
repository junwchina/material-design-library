package com.md.library.widgets;


import com.md.library.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.RelativeLayout;


public abstract class Dialog extends android.app.Dialog {
	
	protected Context mContext;
	
	protected RelativeLayout mRootView;
	protected RelativeLayout mDialogView;
	
	private int mLayout = R.layout.dialog;

	private int mBackgroundColor = Color.parseColor("#ffffff");

	public Dialog(Context context) {
		super(context, android.R.style.Theme_Translucent);
		mContext = context;
	}
	
	
	public Dialog(Context context, int layout) {
		this(context);
		mLayout = layout;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(mLayout);
	    
	    mDialogView = (RelativeLayout)findViewById(R.id.contentDialog);
	    mRootView = (RelativeLayout)findViewById(R.id.dialog_rootView);
	    
	    initializeBackground();
	    
	    mRootView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getX() < mDialogView.getLeft() 
						|| event.getX() >mDialogView.getRight()
						|| event.getY() > mDialogView.getBottom() 
						|| event.getY() < mDialogView.getTop()) {
					dismiss();
				}
				return false;
			}
		});
	    
	    onCreateDialogView(LayoutInflater.from(mContext), mDialogView);
	}

	
	
	protected View onCreateDialogView(LayoutInflater layoutInflater, ViewGroup parent) {
		return null;
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
