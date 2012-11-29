/** 
 * Filename:    CodeView.java
 * Description:  
 * Copyright:   Baidu MIC Copyright(c)2011 
 * @author:     zerob13 
 * @version:    1.0
 * Create at:   Nov 17, 2012 3:07:11 PM
 * 
 * Modification History: 
 * Date         Author      Version     Description 
 * ------------------------------------------------------------------ 
 * Nov 17, 2012    zerob13      1.0         1.0 Version 
 */
package in.zerob13.CodeBrowserHD;

import android.R.integer;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;

public class CodeView extends ViewGroup {

	private WebView browser;
	private LinearLayout aLine;

	/**
	 * @param context
	 */
	public CodeView(Context context) {
		this(context, null);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public CodeView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public CodeView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		aLine = new LinearLayout(context);
		browser = new WebView(context);
		browser.getSettings().setBuiltInZoomControls(true);
		browser.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		browser.setHorizontalScrollBarEnabled(true);
		browser.setHorizontalScrollbarOverlay(true);
		browser.setVerticalScrollBarEnabled(true);
		browser.resumeTimers();
		this.addView(browser);
		this.addView(aLine);
		requestLayout();

	}

	public WebView getWebview() {
		return browser;
	}

	public LinearLayout getALine() {
		return aLine;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.ViewGroup#onLayout(boolean, int, int, int, int)
	 */
	@Override
	protected void onLayout(boolean arg0, int arg1, int arg2, int arg3, int arg4) {
		DisplayMetrics dm = new DisplayMetrics();
		int width = dm.widthPixels;
		int height = dm.heightPixels;
		int des = dm.densityDpi;
		browser.layout(0, 0, width, height - 40 * des);
		aLine.layout(0, height - 40 * des, width, height);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		setMeasuredDimension(widthSize, heightSize);

	}

}
