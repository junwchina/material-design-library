package com.md.library.widgets;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.TextView;

import com.md.library.R;

public class CustomProgressDialog extends AlertDialog {

	private CharSequence mMessage;
	
	public CustomProgressDialog(Context context) {
		super(context);
	}
	
	public static CustomProgressDialog show(Context context,CharSequence message) {
		CustomProgressDialog dialog = new CustomProgressDialog(context);
		dialog.mMessage = message;
		dialog.show();
		return dialog;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customprogressdialog);
        ViewGroup bg = (ViewGroup)findViewById(R.id.background);
        TextView mTextView = (TextView) bg.findViewById(R.id.textView1);
        mTextView.setText(mMessage);
//        LayerDrawable layer = (LayerDrawable) mDialogView.getBackground();
//		GradientDrawable shape = (GradientDrawable) layer.findDrawableByLayerId(R.id.shape_bacground);
//		shape.setColor(mBackgroundColor);
//        setView(LayoutInflater.from(mContext));
//        LayoutInflater.from(mContext).inflate(R.layout.customprogressdialog,this);
    }
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return true;
	}
	
	@Override
	public void onBackPressed() {
		
	}
}
