/*
 * Filename:    FileChoser.java
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

package in.zerob13.CodeBrowserHD.in.zerob13.CodeBrowserHD.fileChoser;

import java.io.File;

import android.os.Environment;
import in.zerob13.CodeBrowserHD.in.zerob13.CodeBrowserHD.main.CodeBrowser;
import in.zerob13.CodeBrowserHD.in.zerob13.CodeBrowserHD.main.GlobalConfig;
import in.zerob13.CodeBrowserHD.R;
import net.youmi.android.AdManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * 文件选择器
 */
public class FileChoser extends Activity {
	private File mCurrentDirectory = Environment.getExternalStorageDirectory();
	private ExpBaseAdapter mAdapter;
	private ListView mListView;

	private static final String FILE_ENDINGS[] = { "cpp", "cc", "h", "hpp", "cxx", "hxx", "c", "c++", "cs",
			"c#", "c-sharp", "csharp", "css", "jav", "java", "php", "php4", "py", "python", "sh", "ksh",
			"csh", "shell", "rc", "init", "4gl", "proc", "sql", "bas", "frm", "cls", "vbs", "ctl", "vb",
			"vb.net", "asp", "jsp", "aspx", "htt", "htx", "phtml", "wml", "rss", "xhtml", "shtml", "dhtml",
			"dtd", "html", "htm", "xhtml", "xml", "xsd", "xsl", "xslt", "config", "js", "jscript",
			"javascript" };

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setTitle(R.string.text_selectfile);
		setContentView(R.layout.frm_openfile);
		mListView = (ListView) findViewById(R.id.of_lv);
		mListView.setCacheColorHint(0x00000000);
		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		GlobalConfig.sWidth = dm.widthPixels;
		GlobalConfig.sHeight = dm.heightPixels;
		GlobalConfig.sDes = dm.density;
		mAdapter = new ExpBaseAdapter(this);
		mListView.setAdapter(mAdapter);
		AdManager.init(getApplicationContext(), "9820e7674f3eb1a7", "d59234552b2ee037", 30, false);
		ListView.OnItemClickListener lv2click = new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView parent, View view, int position, long id) {

				int fid = mAdapter.getItemType((int) id);
				String mPath = "";
				if (fid == -1) {
					return;
				}
				if (fid == 1) {
					String s1 = mAdapter.getItem((int) id).mName;

					if (s1.equals("..")) {
						mPath = mCurrentDirectory.getParent();
					} else {
						mPath = mCurrentDirectory.getPath() + "/" + s1 + "/";
					}
					mCurrentDirectory = new File(mPath);
					toListFile(mCurrentDirectory);
				} else {
					Bundle bundle = new Bundle();
					bundle.putString("filename",
							mCurrentDirectory.getPath() + "/" + mAdapter.getItem((int) id).mName);
					Intent mIntent = new Intent();
					mIntent.setClass(FileChoser.this, CodeBrowser.class);
					mIntent.putExtras(bundle);

					startActivity(mIntent);
				}
			}

		};
		toListFile(mCurrentDirectory);
		mListView.setOnItemClickListener(lv2click);
	}

    /**
     * 列文件
     * @param aDirectory 目录
     */
	private void toListFile(File aDirectory) {
		mAdapter.clearItems();
		mAdapter.notifyDataSetChanged();
		mListView.postInvalidate();
		//		Log.v("vodone", "mpath=" + aDirectory.getPath());

		if (!aDirectory.getPath().equals("/")) {
			FileData fd = new FileData();
			fd.mName = "..";
			fd.mType = 1;
			mAdapter.addItem(fd);
		}
		for (File f : aDirectory.listFiles()) {
			if (!f.canRead() || f.isHidden()) {
				continue;
			}
			if (f.isDirectory()) {
				FileData fd = new FileData();
				fd.mName = f.getName();
				fd.mType = 1;
				mAdapter.addItem(fd);
			} else {
				if (checkEnds(f.getName().toLowerCase())) {
					FileData fd = new FileData();
					fd.mName = f.getName();
					fd.mType = 0;
					mAdapter.addItem(fd);
				}
			}
		}
		FileData adData = new FileData();
		adData.mName = "....";
		adData.mType = -1;
		mAdapter.addItem(adData);
		mAdapter.notifyDataSetChanged();
		mListView.postInvalidate();
	}

    /**
     * 检查文件后缀
     * @param checkItsEnd 文件名
     * @return 是否是符合要求的后缀
     */
	private boolean checkEnds(String checkItsEnd) {
		for (String aEnd : FILE_ENDINGS) {
			if (checkItsEnd.endsWith(aEnd)) {
				return true;
			}
		}
		return false;

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		// 按下键盘上返回按钮
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (mCurrentDirectory.getParent() == null) {
				return super.onKeyDown(keyCode, event);
			} else {
				mCurrentDirectory = new File(mCurrentDirectory.getParent());

				toListFile(mCurrentDirectory);

			}
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.exit(0);
	}

}
