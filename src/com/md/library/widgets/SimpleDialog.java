package com.md.library.widgets;

import com.md.library.R;
import com.md.library.views.FlatButton;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SimpleDialog extends Dialog {
	
	private String mTitle;
	private String mMessage;
	
	private TextView mTitleView;
	private TextView mMessageView;
	
	private FlatButton mAcceptButton;
	private FlatButton mCancelButton;
	
	private View.OnClickListener onAcceptButtonClickListener;
	private View.OnClickListener onCancelButtonClickListener;



	public SimpleDialog(Context context, String title, String message) {
		super(context, R.layout.dialog);
		mTitle = title;
		mMessage = message;
	}
	
	@Override
	protected View onCreateDialogView(LayoutInflater layoutInflater, ViewGroup parent) {
		return layoutInflater.inflate(R.layout.simple_dialog, parent);
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mTitleView = (TextView) findViewById(R.id.title);
	    mMessageView = (TextView) findViewById(R.id.message);
	    mAcceptButton = (FlatButton) findViewById(R.id.button_accept);
	    mCancelButton = (FlatButton)findViewById(R.id.button_cancel);
	    
	    setOnClickEventToButtons();
	    setTitle(mTitle);
	    setMessage(mMessage);

	}
	
	
	private void setOnClickEventToButtons() {
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
	
	public FlatButton getCancelButton() {
		return mCancelButton;
	}
	
	public FlatButton getAcceptButton() {
		return mAcceptButton;
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
}
