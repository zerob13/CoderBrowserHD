/*
 * Filename:    GlobalConfig.java
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

public class GlobalConfig {

	/**
	 * constructor
	 */
	private GlobalConfig() {

	}

	public static final String[] ENCODE_TYPE = { "macroman", "windows-1255", "gb2312", "gbk", "unicode",
			"big5", "utf-8", "utf-16", "utf-2", "utf-32", "utf-7", "iso-10646", "iso-2022", "iso-2022-cn",
			"iso-2022-cn-ext", "iso-2022-jp", "iso-2022-kr", "iso-646", "iso-8859", "iso-ir-111", "acii",
			"ascii", "ascii-1963", "ascii-1968", "adobe standard encoding", "cp1250", "cp1251", "dbcs",
			"cp1252", "codepage", "hp-roman", "ibm dbcs", "shift-jis", "sjis" };
	public static final String[] CPP_ALIASES = { "cpp", "cc", "h", "hpp", "cxx", "hxx", "c", "c++" };
	public static final String[] CSHARP_ALIASES = { "cs", "c#", "c-sharp", "csharp" };
	public static final String[] CSS_ALIASES = { "css" };
	public static final String[] JAVA_ALIASES = { "jav", "java" };
	public static final String[] PHP_ALIASES = { "php", "php4" };
	public static final String[] PYTHON_ALIASES = { "py", "python" };
	public static final String[] BASH_ALIASES = { "sh", "ksh", "csh", "shell", "rc", "init" };
	public static final String[] XML_ALIASES = { "asp", "jsp", "aspx", "htt", "htx", "phtml", "wml", "rss",
			"xhtml", "shtml", "dhtml", "dtd", "html", "htm", "xhtml", "xml", "xsd", "xsl", "xslt", "config" };
	public static final String[] JS_ALIASES = { "js", "jscript", "javascript" };
	public static final boolean DEBUG = false;
	public static int sWidth;
	public static int sHeight;
	public static double sDes;
	public static String sCodeTemp;
}
