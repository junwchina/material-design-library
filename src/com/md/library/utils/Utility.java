package com.md.library.utils;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

public class Utility {

	public static final String TAG = "MDLibrary";
	
	public static int makeAlphaColor(int color, int alpha) {
		int r = (color >> 16) & 0xFF;
		int g = (color >> 8) & 0xFF;
		int b = (color >> 0) & 0xFF;
		r = (r - 50 < 0) ? 0 : r - 50;
		g = (g - 50 < 0) ? 0 : g - 50;
		b = (b - 50 < 0) ? 0 : b - 50;
		return Color.argb(alpha, r, g, b);
	}

	
	
	public static void logD(String msg) {
		Log.d(TAG, msg);
	}
	
	
	public static int convertDpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * displayMetrics.density);       
        return px;
    }
    
    
    public static float getDensity(Context context) {
    	return context.getResources().getDisplayMetrics().density;
    }
    
    public static int convertPxToDp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int dp = Math.round(px / displayMetrics.density);   
        return dp;
    }
	
    
	public static int getMeasurement(int measureSpec, int contentSize) {
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);
        int resultSize = 0;
        switch (specMode) {
            case View.MeasureSpec.UNSPECIFIED:
                //Big as we want to be
                resultSize = contentSize;
                break;
            case View.MeasureSpec.AT_MOST:
                //Big as we want to be, up to the spec
                resultSize = Math.min(contentSize, specSize);
                break;
            case View.MeasureSpec.EXACTLY:
                //Must be the spec size
                resultSize = specSize;
                break;
        }

        return resultSize;
    }
}