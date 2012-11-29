package in.zerob13.CodeBrowser;

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
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;

public class CodeBrowser extends Activity {
	/** Called when the activity is first created. */
	// WebView browser;
	WebView browser;
	private int flag;
	private String mPath;
	private String enCode;
	private String[] encodeType = { "macroman", "windows-1255", "gb2312",
			"gbk", "unicode", "big5", "utf-8", "utf-16", "utf-2", "utf-32",
			"utf-7", "iso-10646", "iso-2022", "iso-2022-cn", "iso-2022-cn-ext",
			"iso-2022-jp", "iso-2022-kr", "iso-646", "iso-8859", "iso-ir-111",
			"acii", "ascii", "ascii-1963", "ascii-1968",
			"adobe standard encoding", "cp1250", "cp1251", "dbcs", "cp1252",
			"codepage", "hp-roman", "ibm dbcs", "shift-jis", "sjis" };
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
	String[] xmlAliases = { "asp", "jsp", "aspx", "htt", "htx", "phtml", "wml",
			"rss", "xhtml", "shtml", "dhtml", "dtd", "html", "htm", "xhtml",
			"xml", "xsd", "xsl", "xslt", "config" };
	String[] jsAliases = { "js", "jscript", "javascript" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		Arrays.sort(cppAliases);
		Arrays.sort(csharpAliases);
		Arrays.sort(javaAliases);
		Arrays.sort(phpAliases);
		Arrays.sort(pythonAliases);
		Arrays.sort(bashAliases);
		Arrays.sort(sqlAliases);
		Arrays.sort(vbAliases);
		Arrays.sort(xmlAliases);
		Arrays.sort(jsAliases);

		transMap.put(' ', "&nbsp;");
		transMap.put('<', "&lt;");
		transMap.put('&', "&amp;");
		transMap.put('>', "&gt;");
		me = this.getApplicationContext();
		String[] file = me.fileList();
		Arrays.sort(file);
		int res = Arrays.binarySearch(file, "temp.html");
		if (res >= 0) {
			me.deleteFile("temp.html");
		}
		setTitle("CodeBrowser By zerob13(www.zerob13.in)");

		setContentView(R.layout.main);
		browser = (WebView) findViewById(R.id.widget31);
		browser.getSettings().setBuiltInZoomControls(true);
		browser.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		browser.setHorizontalScrollBarEnabled(true);
		browser.setHorizontalScrollbarOverlay(true);
		browser.setInitialScale(70);
		browser.setVerticalScrollBarEnabled(true);
		Bundle bundle = this.getIntent().getExtras();
		mPath = bundle.getString("filename");
		enCode = getEncode(mPath);
		Log.v("encode", enCode);
		loadCode();
		flag = 1;

	}

	private void loadCode() {
		StringBuffer temp2 = new StringBuffer();
		browser.clearCache(true);
		browser.clearView();
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

			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(mPath), enCode));
			File target = new File(
					"/data/data/in.zerob13.CodeBrowser/files/temp.html");

			PrintWriter html = new PrintWriter(target, enCode);

			html.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
			html.println("<head><META http-equiv=\"Content-Type\" content=\"text/html; charset="
					+ enCode
					+ "\"><title>CoderBrowser by zerob13(www.zerob13.in)</title>");
			html.println("<link href=\"prettify.css\" rel=\"stylesheet\" type=\"text/css\" />");
			html.println("<script type=\"text/javascript\" src=\"prettify.js\"></script>");

			html.println("</head>" + "<body onload=\"prettyPrint()\">");
			html.println("<pre class=\"prettyprint\">");
			line = br.readLine();
			while (line != null) {
				// html.println(line);
				temp2.delete(0, temp2.length());
				char chars[] = line.toCharArray();
				for (char c : chars) {
					temp2.append(((transMap.get(c) == null) ? c : transMap
							.get(c)));
				}
				html.println(temp2.toString());
				line = br.readLine();
			}
			// html.println("</script>");
			html.println("</pre>");

			html.println("</body>");
			html.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		browser.clearHistory();
		browser.getSettings().setSupportZoom(true);
		browser.getSettings().setJavaScriptEnabled(true);
		// browser.getSettings().setLoadWithOverviewMode(true);
		browser.loadUrl("file:///data/data/in.zerob13.CodeBrowser/files/temp.html");
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		try {
			super.onConfigurationChanged(newConfig);
			if (flag == 1) {
				setTitle("CodeBrowser By zerob13(www.zerob13.in)");
				setContentView(R.layout.main);
				browser = (WebView) findViewById(R.id.widget31);
				browser.getSettings().setBuiltInZoomControls(true);
				browser.getSettings().setJavaScriptEnabled(true);
				browser.loadUrl("file:///data/data/in.zerob13.CodeBrowser/files/temp.html");

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
		Log.v("encode", String.valueOf((item.getItemId())));
		if (item.getItemId() != -1) {
			enCode = encodeType[item.getItemId()];
			loadCode();
			browser.reload();
		}
		return super.onOptionsItemSelected(item);
	}

}
