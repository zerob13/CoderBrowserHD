package in.zerob13.CodeBrowserHD;

import java.io.File;

import net.youmi.android.AdManager;
import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class FileChoser extends Activity {
	private File mCurrentDirectory = new File("/sdcard/");
	ExpBaseAdapter ap;
	ListView lv2;
	Context me;

	//	String fileEndings[] = { "cpp", "cc", "h", "hpp", "cxx", "hxx", "c", "c++",
	//			"cs", "c#", "c-sharp", "csharp", "css", "jav", "java", "php",
	//			"php4", "py", "python", "sh", "ksh", "csh", "shell", "rc", "init",
	//			"4gl", "proc", "sql", "bas", "frm", "cls", "vbs", "ctl", "vb",
	//			"vb.net", "asp", "jsp", "aspx", "htt", "htx", "phtml", "wml",
	//			"rss", "xhtml", "shtml", "dhtml", "dtd", "html", "htm", "xhtml",
	//			"xml", "xsd", "xsl", "xslt", "config", "js", "jscript",
	//			"javascript" };

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setTitle(R.string.text_selectfile);
		setContentView(R.layout.frm_openfile);
		lv2 = (ListView) findViewById(R.id.of_lv);
		lv2.setCacheColorHint(0x00000000);
		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		GlobalConfig.my_width = dm.widthPixels;
		GlobalConfig.my_height = dm.heightPixels;
		GlobalConfig.my_des = dm.density;
		ap = new ExpBaseAdapter(this);
		lv2.setAdapter(ap);
		AdManager.init(getApplicationContext(),"9820e7674f3eb1a7", "d59234552b2ee037", 30, false);
		ListView.OnItemClickListener lv2click = new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView parent, View view, int position, long id) {

				int fid = ap.getItemType((int) id);
				String mPath = "";
				if(fid == -1){
					return;
				}
				if (fid == 1) {
					String s1 = ap.getItem((int) id).name;
					
					if (s1.equals("..")) {
						mPath = mCurrentDirectory.getParent();
					} else {
						mPath = mCurrentDirectory.getPath() + "/" + s1 + "/";
					}
					mCurrentDirectory = new File(mPath);
					ListFile(mCurrentDirectory);
				} else {
					Bundle bundle = new Bundle();
					bundle.putString("filename", mCurrentDirectory.getPath() + "/"
							+ ap.getItem((int) id).name);
					Intent mIntent = new Intent();
					mIntent.setClass(FileChoser.this, CodeBrowser.class);
					mIntent.putExtras(bundle);

					startActivity(mIntent);
				}
			}

		};
		ListFile(mCurrentDirectory);
		lv2.setOnItemClickListener(lv2click);
	}

	private void ListFile(File aDirectory) {
		ap.clearItems();
		ap.notifyDataSetChanged();
		lv2.postInvalidate();
		Log.v("vodone", "mpath=" + aDirectory.getPath());

		if (!aDirectory.getPath().equals("/")) {
			fileData fd = new fileData();
			fd.name = "..";
			fd.type = 1;
			ap.addItem(fd);
		}
		for (File f : aDirectory.listFiles()) {
			if (!f.canRead() || f.isHidden())
				continue;
			if (f.isDirectory()) {
				fileData fd = new fileData();
				fd.name = f.getName();
				fd.type = 1;
				ap.addItem(fd);
			} else {
				//if (checkEnds(f.getName().toLowerCase())) {
				fileData fd = new fileData();
				fd.name = f.getName();
				fd.type = 0;
				ap.addItem(fd);
				//}
			}
		}
		fileData adData= new fileData();
		adData.name="....";
		adData.type=-1;
		ap.addItem(adData);
		ap.notifyDataSetChanged();
		lv2.postInvalidate();
	}

	//	private boolean checkEnds(String checkItsEnd) {
	//		for (String aEnd : fileEndings) {
	//			if (checkItsEnd.endsWith(aEnd))
	//				return true;
	//		}
	//		return false;
	//
	//	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		// 按下键盘上返回按钮
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (mCurrentDirectory.getParent() == null) {
				return super.onKeyDown(keyCode, event);
			} else {
				mCurrentDirectory = new File(mCurrentDirectory.getParent());

				ListFile(mCurrentDirectory);

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
