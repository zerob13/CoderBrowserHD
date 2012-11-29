package in.zerob13.CodeBrowserHD;

import in.zerob13.CodeBrowserHD.View.CodeView;
import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import net.youmi.android.AdManager;
import net.youmi.android.AdView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class CodeBrowser extends Activity {
	/** Called when the activity is first created. */
	// WebView browser;
	private boolean DEBUG = true;
	private WebView browser;
	private int flag;
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
	String[] sqlAliases = { "4gl", "proc", "sql" };
	String[] vbAliases = { "bas", "frm", "cls", "vbs", "ctl", "vb", "vb.net" };
	String[] xmlAliases = { "asp", "jsp", "aspx", "htt", "htx", "phtml", "wml", "rss", "xhtml", "shtml",
			"dhtml", "dtd", "html", "htm", "xhtml", "xml", "xsd", "xsl", "xslt", "config" };
	String[] jsAliases = { "js", "jscript", "javascript" };

	String html = null;
	CodeView rootView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//		Arrays.sort(cppAliases);
		//		Arrays.sort(csharpAliases);
		//		Arrays.sort(javaAliases);
		//		Arrays.sort(phpAliases);
		//		Arrays.sort(pythonAliases);
		//		Arrays.sort(bashAliases);
		//		Arrays.sort(sqlAliases);
		//		Arrays.sort(vbAliases);
		//		Arrays.sort(xmlAliases);
		//		Arrays.sort(jsAliases);
		rootView = new CodeView(this);
		rootView.requestLayout();
		transMap.put(' ', "&nbsp;");
		transMap.put('<', "&lt;");
		transMap.put('&', "&amp;");
		transMap.put('>', "&gt;");
//		setContentView(R.layout.main);
		setTitle("CodeBrowser By zerob13(www.zerob13.in)");
		browser = rootView.getWebview();
//		browser = (WebView)findViewById(R.id.widget31);
		
		
//		aLayout.setOrientation(LinearLayout.VERTICAL);
//		LinearLayout aLayout = rootView.getALine();
//		aLayout.addView(new AdView(this),
//				new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//		setContentView(rootView);
//		browser.getSettings().setBuiltInZoomControls(true);
//		browser.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
//		browser.setHorizontalScrollBarEnabled(true);
//		browser.setHorizontalScrollbarOverlay(true);
//		browser.setVerticalScrollBarEnabled(true);
//		browser.resumeTimers();
		rootView.getALine().addView(new AdView(this));
		setContentView(rootView);
		Bundle bundle = this.getIntent().getExtras();
		mPath = bundle.getString("filename");
		File tFile = new File(mPath);
		if (tFile.length() < 1024 * 32 * 8) {
			enCode = getEncode(mPath);
		} else {
			enCode = "UTF-8";
		}
		if (DEBUG)
			Log.v("encode", enCode);
		loadCode();
		flag = 0;

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
//			browser.destroy();
		}
		super.onDestroy();
		if (DEBUG)
			Log.d("onDestroy", "destory");
	}

	private void loadCode() {
		StringBuffer temp2 = new StringBuffer();
		//		browser.clearCache(true);
		//		browser.clearView();
		for (int i = mPath.length() - 1; i >= 0; i--) {
			if (mPath.charAt(i) != '.') {

				if (mPath.charAt(i) >= 'A' && mPath.charAt(i) <= 'Z') {
					temp2.append(mPath.charAt(i) - 'A' + 'a');
				} else {
					temp2.append(mPath.charAt(i));
				}
			} else
				break;
		}

		temp2.reverse();
		temp2.delete(0, temp2.length());

		try {
			// 构建一个带缓冲的字符型输入流
			String line = null;
			StringBuffer aBuffer = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(mPath), enCode));
			aBuffer.append("<head><META http-equiv=\"Content-Type\" content=\"text/html; charset=" + enCode
					+ "\"><title>CoderBrowser by zerob13(www.zerob13.in)</title>");
			aBuffer.append("\n");
			aBuffer.append("<link href=\"prettify.css\" rel=\"stylesheet\" type=\"text/css\" />");
			aBuffer.append("\n");
			aBuffer.append("<script type=\"text/javascript\" src=\"prettify.js\"></script>");
			aBuffer.append("\n");

			aBuffer.append("</head>" + "\n<body onload=\"prettyPrint()\">");
			aBuffer.append("\n");
			aBuffer.append("<pre class=\"prettyprint\">");
			aBuffer.append("\n");
			line = br.readLine();
			while (line != null) {
				temp2.delete(0, temp2.length());
				char chars[] = line.toCharArray();
				for (char c : chars) {
					temp2.append(((transMap.get(c) == null) ? c : transMap.get(c)));
				}
				temp2.append("\n");
				aBuffer.append(temp2);
				line = br.readLine();
			}
			aBuffer.append("</pre>");
			aBuffer.append("\n");
			aBuffer.append("</body>");
			aBuffer.append("\n");
			html = aBuffer.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
		try {
			//			super.onConfigurationChanged(newConfig);
			if (flag == 1) {
				setTitle("CodeBrowser By zerob13(www.zerob13.in)");
				setContentView(R.layout.main);
				browser = (WebView) findViewById(R.id.widget31);
				browser.getSettings().setBuiltInZoomControls(true);
				browser.getSettings().setJavaScriptEnabled(true);
				browser.getSettings().setUseWideViewPort(true);
				browser.getSettings().setAllowFileAccess(true);
				browser.loadDataWithBaseURL("file:///android_asset/", html, "text/html", enCode, null);

			}
		} catch (Exception ex) {
		}
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
		// TODO Auto-generated method stub
		if (DEBUG)
			Log.v("encode", String.valueOf((item.getItemId())));
		if (item.getItemId() != -1) {
			enCode = encodeType[item.getItemId()];
			loadCode();
			//			browser.reload();
		}
		return super.onOptionsItemSelected(item);
	}

}
