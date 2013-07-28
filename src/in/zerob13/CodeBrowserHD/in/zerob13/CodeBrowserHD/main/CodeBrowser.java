/*
 * Filename:    CodeBrowser.java
 * Description:
 * Copyright:   www.zerob13.in 2013
 * @author:     Yang Lingfeng (zerob13)
 * @version:    1.0
 * Create at:   2013-7-28
 *
 *  Modification History:
 *  Date         Author      Version     Description
 *  ------------------------------------------------------------------
 *  2013-7-28    Yang Lingfeng      1.0         1.0 Version
 */

package in.zerob13.CodeBrowserHD.in.zerob13.CodeBrowserHD.main;

import in.zerob13.CodeBrowserHD.R;
import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;

/**
 * 浏览代码Activity
 */
public class CodeBrowser extends Activity {

	private WebView mCodeView;
	private String mPath;
	private String mEncode;
	private Map<Character, String> mTransMap = new HashMap<Character, String>();
	private String html = null;
	private boolean mIssort = false;
	private String mCodeType;
	private String mCodeBody;
	private boolean mIsDayTime = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		GlobalConfig.sWidth = dm.widthPixels;
		GlobalConfig.sHeight = dm.heightPixels;
		GlobalConfig.sDes = dm.density;
		if (!mIssort) {
			Arrays.sort(GlobalConfig.CPP_ALIASES);
			Arrays.sort(GlobalConfig.CSHARP_ALIASES);
			Arrays.sort(GlobalConfig.JAVA_ALIASES);
			Arrays.sort(GlobalConfig.PHP_ALIASES);
			Arrays.sort(GlobalConfig.PYTHON_ALIASES);
			Arrays.sort(GlobalConfig.BASH_ALIASES);
			Arrays.sort(GlobalConfig.XML_ALIASES);
			Arrays.sort(GlobalConfig.JS_ALIASES);
			mIssort = true;
		}
		mTransMap.put(' ', "&nbsp;");
		mTransMap.put('<', "&lt;");
		mTransMap.put('&', "&amp;");
		mTransMap.put('>', "&gt;");
		setTitle(R.string.app_title);
		mCodeView = new WebView(this);
		mCodeView.getSettings().setBuiltInZoomControls(true);
		mCodeView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		mCodeView.setHorizontalScrollBarEnabled(true);
		mCodeView.setHorizontalScrollbarOverlay(true);
		mCodeView.setVerticalScrollBarEnabled(true);
		mCodeView.resumeTimers();
		setContentView(mCodeView);
		Bundle bundle = this.getIntent().getExtras();
		mPath = bundle.getString("filename");
		File tFile = new File(mPath);
		mCodeType = "";
		String[] tnameString = tFile.getName().split("\\.");
		if (tnameString.length > 1) {
			String endString = tnameString[tnameString.length - 1].toLowerCase();
			if (Arrays.binarySearch(GlobalConfig.CPP_ALIASES, endString) >= 0) {
				mCodeType = " lang-cpp";
			}
			if (Arrays.binarySearch(GlobalConfig.CSHARP_ALIASES, endString) >= 0) {
				mCodeType = " lang-cs";
			}
			if (Arrays.binarySearch(GlobalConfig.JAVA_ALIASES, endString) >= 0) {
				mCodeType = " lang-java";
			}
			if (Arrays.binarySearch(GlobalConfig.PHP_ALIASES, endString) >= 0) {
				mCodeType = " lang-php";
			}
			if (Arrays.binarySearch(GlobalConfig.PYTHON_ALIASES, endString) >= 0) {
				mCodeType = " lang-python";
			}
			if (Arrays.binarySearch(GlobalConfig.BASH_ALIASES, endString) >= 0) {
				mCodeType = " lang-bsh";
			}
			if (Arrays.binarySearch(GlobalConfig.XML_ALIASES, endString) >= 0) {
				mCodeType = " lang-xml";
			}
			if (Arrays.binarySearch(GlobalConfig.JS_ALIASES, endString) >= 0) {
				mCodeType = " lang-js";
			}
		}
		if (tFile.length() < 1024 * 32 * 8) {
			mEncode = getEncode(mPath);
		} else {
			mEncode = "UTF-8";
		}
		if (GlobalConfig.DEBUG) {
			Log.v("encode", mEncode);
			Log.v("mCodeType", mCodeType);
		}
		loadCode(false);

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Bundle bundle = intent.getExtras();
		mPath = bundle.getString("filename");
		File tFile = new File(mPath);
		if (tFile.length() < 1024 * 32 * 8) {
			mEncode = getEncode(mPath);
		} else {
			mEncode = "UTF-8";
		}
		if (GlobalConfig.DEBUG) {
			Log.v("onnew Intent ", mEncode);
		}
		loadCode(false);
	}

	@Override
	protected void onDestroy() {
		if (mCodeView != null) {
			mCodeView.pauseTimers();
			mCodeView.stopLoading();
			mCodeView.clearView();
		}
		super.onDestroy();
		if (GlobalConfig.DEBUG) {
			Log.d("onDestroy", "destory");
		}
	}

	private void loadCode(boolean noReset) {
		if (GlobalConfig.sCodeTemp == null) {
			InputStream inptemp = this.getResources().openRawResource(R.raw.htmltemp);
			Scanner aScanner = new Scanner(inptemp);
			StringBuffer tempBuffer = new StringBuffer();
			while (aScanner.hasNextLine()) {
				tempBuffer.append(aScanner.nextLine());
				tempBuffer.append("\n");
			}
			GlobalConfig.sCodeTemp = tempBuffer.toString();
		}
		// 构建一个带缓冲的字符型输入流
		if (!noReset) {
			String line = null;
			StringBuffer aBuffer = new StringBuffer();
			Scanner aScanner;
			try {
				aScanner = new Scanner(new File(mPath));
				while (aScanner.hasNextLine()) {
					line = aScanner.nextLine();
					char chars[] = line.toCharArray();
					for (char c : chars) {
						aBuffer.append(((mTransMap.get(c) == null) ? c : mTransMap.get(c)));
					}
					aBuffer.append("\n");
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			mCodeBody = aBuffer.toString();
		}
		if (mIsDayTime) {
			html = String.format(GlobalConfig.sCodeTemp, mEncode, "prettify-day.css", mCodeType, mCodeBody);
		} else {
			html = String.format(GlobalConfig.sCodeTemp, mEncode, "prettify.css", mCodeType, mCodeBody);
		}
		mCodeView.clearView();
		mCodeView.stopLoading();
		mCodeView.getSettings().setSupportZoom(true);
		mCodeView.getSettings().setJavaScriptEnabled(true);
		mCodeView.getSettings().setAllowFileAccess(true);
		mCodeView.getSettings().setUseWideViewPort(true);
		mCodeView.loadDataWithBaseURL("file:///android_asset/", html, "text/html", mEncode, null);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		GlobalConfig.sWidth = dm.widthPixels;
		GlobalConfig.sHeight = dm.heightPixels;
		GlobalConfig.sDes = dm.density;
		//		rootView.requestLayout();
		super.onConfigurationChanged(newConfig);
		//		setContentView(rootView);
	}

	public void onClick(View v) {

	}

	private String getEncode(String aPath) {

		CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
		detector.add(new ParsingDetector(false));

		detector.add(JChardetFacade.getInstance());
		detector.add(ASCIIDetector.getInstance());
		detector.add(UnicodeDetector.getInstance());

		java.nio.charset.Charset charset = null;
		File f = new File(aPath);
		try {

			charset = detector.detectCodepage(f.toURI().toURL());

		} catch (Exception ex) {

			ex.printStackTrace();
		}
		if (charset != null) {
			return charset.name();
		} else {
			return "UTF-8";
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		SubMenu filechose = menu.addSubMenu(1, -1, 0, R.string.encode_select);

		SubMenu themeChose = menu.addSubMenu(2, -2, 1, R.string.theme_select);
		themeChose.add(2, 0, 0, R.string.theme_day);
		themeChose.add(2, 1, 0, R.string.theme_night);
		for (int i = 0; i < GlobalConfig.ENCODE_TYPE.length; i++) {
			filechose.add(1, i, 0, GlobalConfig.ENCODE_TYPE[i]);
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (GlobalConfig.DEBUG) {
			Log.v("encode", String.valueOf((item.getItemId())));
		}
		if (item.getGroupId() == 1) {
			if (item.getItemId() != -1) {
				mEncode = GlobalConfig.ENCODE_TYPE[item.getItemId()];
				loadCode(true);
			}
		} else if (item.getGroupId() == 2) {

			if (item.getItemId() == 0) {
				mIsDayTime = true;
				loadCode(true);
			} else if (item.getItemId() == 1) {
				mIsDayTime = false;
				loadCode(true);
			}

		}
		return super.onOptionsItemSelected(item);
	}

}
