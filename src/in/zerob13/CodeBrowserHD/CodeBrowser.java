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

package in.zerob13.CodeBrowserHD;

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

public class CodeBrowser extends Activity {
	private WebView browser;
	private String mPath;
	private String enCode;
	private Map<Character, String> transMap = new HashMap<Character, String>();

	String html = null;
	private boolean mIssort = false;
	private String codeType;
	private String codeBody;
	private boolean isDayTheme = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		GlobalConfig.my_width = dm.widthPixels;
		GlobalConfig.my_height = dm.heightPixels;
		GlobalConfig.my_des = dm.density;
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
		transMap.put(' ', "&nbsp;");
		transMap.put('<', "&lt;");
		transMap.put('&', "&amp;");
		transMap.put('>', "&gt;");
		setTitle("CodeBrowser By zerob13(www.zerob13.in)");
		browser = new WebView(this);
		browser.getSettings().setBuiltInZoomControls(true);
		browser.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		browser.setHorizontalScrollBarEnabled(true);
		browser.setHorizontalScrollbarOverlay(true);
		browser.setVerticalScrollBarEnabled(true);
		browser.resumeTimers();
		setContentView(browser);
		Bundle bundle = this.getIntent().getExtras();
		mPath = bundle.getString("filename");
		File tFile = new File(mPath);
		codeType = "";
		String[] tnameString = tFile.getName().split("\\.");
		if (tnameString.length > 1) {
			String endString = tnameString[tnameString.length - 1].toLowerCase();
			if (Arrays.binarySearch(GlobalConfig.CPP_ALIASES, endString) >= 0) {
				codeType = " lang-cpp";
			}
			if (Arrays.binarySearch(GlobalConfig.CSHARP_ALIASES, endString) >= 0) {
				codeType = " lang-cs";
			}
			if (Arrays.binarySearch(GlobalConfig.JAVA_ALIASES, endString) >= 0) {
				codeType = " lang-java";
			}
			if (Arrays.binarySearch(GlobalConfig.PHP_ALIASES, endString) >= 0) {
				codeType = " lang-php";
			}
			if (Arrays.binarySearch(GlobalConfig.PYTHON_ALIASES, endString) >= 0) {
				codeType = " lang-python";
			}
			if (Arrays.binarySearch(GlobalConfig.BASH_ALIASES, endString) >= 0) {
				codeType = " lang-bsh";
			}
			if (Arrays.binarySearch(GlobalConfig.XML_ALIASES, endString) >= 0) {
				codeType = " lang-xml";
			}
			if (Arrays.binarySearch(GlobalConfig.JS_ALIASES, endString) >= 0) {
				codeType = " lang-js";
			}
		}
		if (tFile.length() < 1024 * 32 * 8) {
			enCode = getEncode(mPath);
		} else {
			enCode = "UTF-8";
		}
		if (GlobalConfig.DEBUG) {
			Log.v("encode", enCode);
			Log.v("codeType", codeType);
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
			enCode = getEncode(mPath);
		} else {
			enCode = "UTF-8";
		}
		if (GlobalConfig.DEBUG)
			Log.v("onnew Intent ", enCode);
		loadCode(false);
	}

	@Override
	protected void onDestroy() {
		if (browser != null) {
			browser.pauseTimers();
			browser.stopLoading();
			browser.clearView();
		}
		super.onDestroy();
		if (GlobalConfig.DEBUG)
			Log.d("onDestroy", "destory");
	}

	private void loadCode(boolean noReset) {
		if (GlobalConfig.codeTemp == null) {
			InputStream inptemp = this.getResources().openRawResource(R.raw.htmltemp);
			Scanner aScanner = new Scanner(inptemp);
			StringBuffer tempBuffer = new StringBuffer();
			while (aScanner.hasNextLine()) {
				tempBuffer.append(aScanner.nextLine());
				tempBuffer.append("\n");
			}
			GlobalConfig.codeTemp = tempBuffer.toString();
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
						aBuffer.append(((transMap.get(c) == null) ? c : transMap.get(c)));
					}
					aBuffer.append("\n");
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			codeBody = aBuffer.toString();
		}
		if (isDayTheme) {
			html = String.format(GlobalConfig.codeTemp, enCode, "prettify-day.css", codeType, codeBody);
		} else {
			html = String.format(GlobalConfig.codeTemp, enCode, "prettify.css", codeType, codeBody);
		}
		browser.clearView();
		browser.stopLoading();
		browser.getSettings().setSupportZoom(true);
		browser.getSettings().setJavaScriptEnabled(true);
		browser.getSettings().setAllowFileAccess(true);
		browser.getSettings().setUseWideViewPort(true);
		browser.loadDataWithBaseURL("file:///android_asset/", html, "text/html", enCode, null);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		GlobalConfig.my_width = dm.widthPixels;
		GlobalConfig.my_height = dm.heightPixels;
		GlobalConfig.my_des = dm.density;
		//		rootView.requestLayout();
		super.onConfigurationChanged(newConfig);
		//		setContentView(rootView);
	}

	public void onClick(View v) {

	}

	private String getEncode(String mPath) {

		CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
		detector.add(new ParsingDetector(false));

		detector.add(JChardetFacade.getInstance());
		detector.add(ASCIIDetector.getInstance());
		detector.add(UnicodeDetector.getInstance());

		java.nio.charset.Charset charset = null;
		File f = new File(mPath);
		try {

			charset = detector.detectCodepage(f.toURI().toURL());

		} catch (Exception ex) {

			ex.printStackTrace();
		}
		if (charset != null)
			return charset.name();
		else
			return "UTF-8";
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		SubMenu filechose = menu.addSubMenu(1, -1, 0, R.string.encode_select);

		SubMenu ThemeChose = menu.addSubMenu(2, -2, 1, R.string.theme_select);
		ThemeChose.add(2, 0, 0, R.string.theme_day);
		ThemeChose.add(2, 1, 0, R.string.theme_night);
		for (int i = 0; i < GlobalConfig.ENCODE_TYPE.length; i++) {
			filechose.add(1, i, 0, GlobalConfig.ENCODE_TYPE[i]);
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (GlobalConfig.DEBUG)
			Log.v("encode", String.valueOf((item.getItemId())));
		if (item.getGroupId() == 1) {
			if (item.getItemId() != -1) {
				enCode = GlobalConfig.ENCODE_TYPE[item.getItemId()];
				loadCode(true);
			}
		} else if (item.getGroupId() == 2) {

			if (item.getItemId() == 0) {
				isDayTheme = true;
				loadCode(true);
			} else if (item.getItemId() == 1) {
				isDayTheme = false;
				loadCode(true);
			}

		}
		return super.onOptionsItemSelected(item);
	}

}
