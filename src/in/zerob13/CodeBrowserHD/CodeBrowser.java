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
import android.content.Context;
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
	private boolean DEBUG = true;
	private WebView browser;
	private String mPath;
	private String enCode;
	private String[] encodeType = { "macroman", "windows-1255", "gb2312", "gbk", "unicode", "big5", "utf-8",
			"utf-16", "utf-2", "utf-32", "utf-7", "iso-10646", "iso-2022", "iso-2022-cn", "iso-2022-cn-ext",
			"iso-2022-jp", "iso-2022-kr", "iso-646", "iso-8859", "iso-ir-111", "acii", "ascii", "ascii-1963",
			"ascii-1968", "adobe standard encoding", "cp1250", "cp1251", "dbcs", "cp1252", "codepage",
			"hp-roman", "ibm dbcs", "shift-jis", "sjis" };
	private Map<Character, String> transMap = new HashMap<Character, String>();
	Context me;
	String[] cppAliases = { "cpp", "cc", "h", "hpp", "cxx", "hxx", "c", "c++" };
	String[] csharpAliases = { "cs", "c#", "c-sharp", "csharp" };
	String[] cssAliases = { "css" };
	String[] javaAliases = { "jav", "java" };
	String[] phpAliases = { "php", "php4" };
	String[] pythonAliases = { "py", "python" };
	String[] bashAliases = { "sh", "ksh", "csh", "shell", "rc", "init" };
	String[] xmlAliases = { "asp", "jsp", "aspx", "htt", "htx", "phtml", "wml", "rss", "xhtml", "shtml",
			"dhtml", "dtd", "html", "htm", "xhtml", "xml", "xsd", "xsl", "xslt", "config" };
	String[] jsAliases = { "js", "jscript", "javascript" };

	String html = null;
	private boolean mIssort = false;
	private String codeType;
	private String codeBody;

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
			Arrays.sort(cppAliases);
			Arrays.sort(csharpAliases);
			Arrays.sort(javaAliases);
			Arrays.sort(phpAliases);
			Arrays.sort(pythonAliases);
			Arrays.sort(bashAliases);
			Arrays.sort(xmlAliases);
			Arrays.sort(jsAliases);
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
			if (Arrays.binarySearch(cppAliases, endString) >= 0) {
				codeType = " lang-cpp";
			}
			if (Arrays.binarySearch(csharpAliases, endString) >= 0) {
				codeType = " lang-cs";
			}
			if (Arrays.binarySearch(javaAliases, endString) >= 0) {
				codeType = " lang-java";
			}
			if (Arrays.binarySearch(phpAliases, endString) >= 0) {
				codeType = " lang-php";
			}
			if (Arrays.binarySearch(pythonAliases, endString) >= 0) {
				codeType = " lang-python";
			}
			if (Arrays.binarySearch(bashAliases, endString) >= 0) {
				codeType = " lang-bsh";
			}
			if (Arrays.binarySearch(xmlAliases, endString) >= 0) {
				codeType = " lang-xml";
			}
			if (Arrays.binarySearch(jsAliases, endString) >= 0) {
				codeType = " lang-js";
			}
		}
		if (tFile.length() < 1024 * 32 * 8) {
			enCode = getEncode(mPath);
		} else {
			enCode = "UTF-8";
		}
		if (DEBUG) {
			Log.v("encode", enCode);
			Log.v("codeType", codeType);
		}
		loadCode();

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
		if (DEBUG)
			Log.v("onnew Intent ", enCode);
		loadCode();
	}

	@Override
	protected void onDestroy() {
		if (browser != null) {
			browser.pauseTimers();
			browser.stopLoading();
			browser.clearView();
		}
		super.onDestroy();
		if (DEBUG)
			Log.d("onDestroy", "destory");
	}

	private void loadCode() {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		codeBody = aBuffer.toString();
		html = String.format(GlobalConfig.codeTemp, enCode, codeType, codeBody);
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
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		SubMenu filechose = menu.addSubMenu(Menu.NONE, -1, 0, "File Encoding");
		for (int i = 0; i < encodeType.length; i++) {
			filechose.add(Menu.NONE, i, 0, encodeType[i]);
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (DEBUG)
			Log.v("encode", String.valueOf((item.getItemId())));
		if (item.getItemId() != -1) {
			enCode = encodeType[item.getItemId()];
			loadCode();
		}
		return super.onOptionsItemSelected(item);
	}

}
