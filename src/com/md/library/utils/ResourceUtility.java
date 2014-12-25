package com.md.library.utils;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

public class ResourceUtility {
	
	private final static String MATERIALDESIGNXML = "http://schemas.android.com/apk/res-auto";
	private final static String ANDROIDXML = "http://schemas.android.com/apk/res/android";

	
	private static ResourceUtility mAndroidInstance;
	private static ResourceUtility mMaterialInstance;
	
	private String mNamespace;

	private ResourceUtility(String namespace) {
		mNamespace = namespace;
	}
	
	
	public int getAttributeColor(Context context, AttributeSet attrs, String attr, int def) {
		int colorId = getAttributeResourceValue(attrs, attr, -1);
		if(colorId != -1) {
			return context.getResources().getColor(colorId);
		} else {
			String colorStr = getAttributeStringValue(attrs, attr);
			if(null == colorStr || colorStr.length() == 0) return def;
			return Color.parseColor(colorStr);
		}
	}	
	
	
	public String getAttributeString(Context context, AttributeSet attrs, String attr) {
		int strId = getAttributeResourceValue(attrs, attr, -1);
		if(strId != -1) {
			return context.getResources().getString(strId);
		}else {
			return getAttributeStringValue(attrs, attr);
		}
	}
	
	
	public int getAttributeResourceValue(AttributeSet attrs, String attr, int def) {
		return attrs.getAttributeResourceValue(mNamespace, attr, def);
	}

	public int getAttributeIntValue(AttributeSet attrs, String attr, int def) {
		return attrs.getAttributeIntValue(mNamespace, attr, def);
	}	
	
	public boolean getAttributeBooleanValue(AttributeSet attrs, String attr, boolean def) {
		return attrs.getAttributeBooleanValue(mNamespace, attr, def);
	}
	
	public float getAttributeFloatValue(AttributeSet attrs, String attr, float def) {
		return attrs.getAttributeFloatValue(mNamespace, attr, def);
	}

	public String getAttributeStringValue(AttributeSet attrs, String attr) {
		return attrs.getAttributeValue(mNamespace, attr);
	}
	
	
	public static ResourceUtility getAndroidInstance() {
		if(null == mAndroidInstance) {
			mAndroidInstance = new ResourceUtility(ANDROIDXML);
		}
		return mAndroidInstance;
	}
	
	public static ResourceUtility getMaterialInstance() {
		if(null == mMaterialInstance) {
			mMaterialInstance = new ResourceUtility(MATERIALDESIGNXML);
		}
		return mMaterialInstance;
	}
}
