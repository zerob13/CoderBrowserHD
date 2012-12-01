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
package in.zerob13.CodeBrowserHD.View;

import in.zerob13.CodeBrowserHD.GlobalConfig;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;

public class CodeView extends ViewGroup {

	private ZeroWebView browser;
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
		aLine.setBackgroundColor(Color.RED);
		browser = new ZeroWebView(context);
		browser.getSettings().setBuiltInZoomControls(true);
		browser.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		browser.setHorizontalScrollBarEnabled(true);
		browser.setHorizontalScrollbarOverlay(true);
		browser.setVerticalScrollBarEnabled(true);
		browser.resumeTimers();
		this.addView(browser);
		this.addView(aLine);

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
		int width = GlobalConfig.my_width;
		int height = GlobalConfig.my_height;
		double des = GlobalConfig.my_des;
		browser.measure(GlobalConfig.my_width, GlobalConfig.my_height - (int) (50 * GlobalConfig.my_des));
		browser.layout(0, 0, browser.getMeasuredWidth(),browser.getMeasuredHeight());
		aLine.layout(0, height-(int) (50 * des), width, height);

	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(GlobalConfig.my_width, GlobalConfig.my_height);
	}

}
