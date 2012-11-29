package in.zerob13.CodeBrowser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
		ap = new ExpBaseAdapter(this);
		lv2.setAdapter(ap);

		initFiles();
		ListView.OnItemClickListener lv2click = new ListView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				int fid = ap.getItemType((int) id);
				String mPath = "";
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
					bundle.putString("filename", mCurrentDirectory.getPath()
							+ "/" + ap.getItem((int) id).name);
					Intent mIntent = new Intent();
					mIntent.setClass(FileChoser.this, CodeBrowser.class);
					mIntent.putExtras(bundle);
					// setResult(RESULT_OK, mIntent);

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
			if(!f.canRead()||f.isHidden())
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
		// 或者下面这种方式
		// android.os.Process.killProcess(android.os.Process.myPid());
	}

	void initFiles() {
		me = this.getApplicationContext();
		String[] file = me.fileList();
		Arrays.sort(file);
		int res = Arrays.binarySearch(file, "prettify.js");
		if (res < 0) {
			// build the file
			try {
				FileOutputStream fout = me.openFileOutput("prettify.js",
						Context.MODE_WORLD_WRITEABLE);
				InputStream in = null;
				in = this.getResources().openRawResource(R.raw.prettifyjs);
				int temp;
				temp = in.read();
				while (temp != -1) {
					fout.write(temp);
					temp = in.read();
				}

				in.close();
				fout.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		res = Arrays.binarySearch(file, "prettify.css");
		if (res < 0) {
			// build the file
			try {
				FileOutputStream fout = me.openFileOutput("prettify.css",
						Context.MODE_WORLD_WRITEABLE);
				InputStream in = null;
				in = this.getResources().openRawResource(R.raw.prettifycss);

				int temp;
				temp = in.read();
				while (temp != -1) {
					fout.write(temp);
					temp = in.read();
				}

				in.close();
				fout.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		res = Arrays.binarySearch(file, "lang-apollo.js");
		if (res < 0) {
			try {
				FileOutputStream fout = me.openFileOutput("lang-apollo.js",
						Context.MODE_WORLD_WRITEABLE);
				InputStream in = null;
				in = this.getResources().openRawResource(R.raw.apollo);
				int temp = in.read();
				while (temp != -1) {
					fout.write(temp);
					temp = in.read();
				}
				in.close();
				fout.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		res = Arrays.binarySearch(file, "lang-clj.js");
		if (res < 0) {
			try {
				FileOutputStream fout = me.openFileOutput("lang-clj.js",
						Context.MODE_WORLD_WRITEABLE);
				InputStream in = null;
				in = this.getResources().openRawResource(R.raw.clj);
				int temp = in.read();
				while (temp != -1) {
					fout.write(temp);
					temp = in.read();
				}
				in.close();
				fout.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		res = Arrays.binarySearch(file, "lang-css.js");
		if (res < 0) {
			try {
				FileOutputStream fout = me.openFileOutput("lang-css.js",
						Context.MODE_WORLD_WRITEABLE);
				InputStream in = null;
				in = this.getResources().openRawResource(R.raw.css);
				int temp = in.read();
				while (temp != -1) {
					fout.write(temp);
					temp = in.read();
				}
				in.close();
				fout.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		res = Arrays.binarySearch(file, "lang-go.js");
		if (res < 0) {
			try {
				FileOutputStream fout = me.openFileOutput("lang-go.js",
						Context.MODE_WORLD_WRITEABLE);
				InputStream in = null;
				in = this.getResources().openRawResource(R.raw.go);
				int temp = in.read();
				while (temp != -1) {
					fout.write(temp);
					temp = in.read();
				}
				in.close();
				fout.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		res = Arrays.binarySearch(file, "lang-hs.js");
		if (res < 0) {
			try {
				FileOutputStream fout = me.openFileOutput("lang-hs.js",
						Context.MODE_WORLD_WRITEABLE);
				InputStream in = null;
				in = this.getResources().openRawResource(R.raw.hs);
				int temp = in.read();
				while (temp != -1) {
					fout.write(temp);
					temp = in.read();
				}
				in.close();
				fout.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		res = Arrays.binarySearch(file, "lang-lisp.js");
		if (res < 0) {
			try {
				FileOutputStream fout = me.openFileOutput("lang-lisp.js",
						Context.MODE_WORLD_WRITEABLE);
				InputStream in = null;
				in = this.getResources().openRawResource(R.raw.lisp);
				int temp = in.read();
				while (temp != -1) {
					fout.write(temp);
					temp = in.read();
				}
				in.close();
				fout.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		res = Arrays.binarySearch(file, "lang-lua.js");
		if (res < 0) {
			try {
				FileOutputStream fout = me.openFileOutput("lang-lua.js",
						Context.MODE_WORLD_WRITEABLE);
				InputStream in = null;
				in = this.getResources().openRawResource(R.raw.lua);
				int temp = in.read();
				while (temp != -1) {
					fout.write(temp);
					temp = in.read();
				}
				in.close();
				fout.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		res = Arrays.binarySearch(file, "lang-ml.js");
		if (res < 0) {
			try {
				FileOutputStream fout = me.openFileOutput("lang-ml.js",
						Context.MODE_WORLD_WRITEABLE);
				InputStream in = null;
				in = this.getResources().openRawResource(R.raw.ml);
				int temp = in.read();
				while (temp != -1) {
					fout.write(temp);
					temp = in.read();
				}
				in.close();
				fout.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		res = Arrays.binarySearch(file, "lang-n.js");
		if (res < 0) {
			try {
				FileOutputStream fout = me.openFileOutput("lang-n.js",
						Context.MODE_WORLD_WRITEABLE);
				InputStream in = null;
				in = this.getResources().openRawResource(R.raw.n);
				int temp = in.read();
				while (temp != -1) {
					fout.write(temp);
					temp = in.read();
				}
				in.close();
				fout.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		res = Arrays.binarySearch(file, "lang-proto.js");
		if (res < 0) {
			try {
				FileOutputStream fout = me.openFileOutput("lang-proto.js",
						Context.MODE_WORLD_WRITEABLE);
				InputStream in = null;
				in = this.getResources().openRawResource(R.raw.proto);
				int temp = in.read();
				while (temp != -1) {
					fout.write(temp);
					temp = in.read();
				}
				in.close();
				fout.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		res = Arrays.binarySearch(file, "lang-sql.js");
		if (res < 0) {
			try {
				FileOutputStream fout = me.openFileOutput("lang-sql.js",
						Context.MODE_WORLD_WRITEABLE);
				InputStream in = null;
				in = this.getResources().openRawResource(R.raw.sql);
				int temp = in.read();
				while (temp != -1) {
					fout.write(temp);
					temp = in.read();
				}
				in.close();
				fout.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		res = Arrays.binarySearch(file, "lang-tex.js");
		if (res < 0) {
			try {
				FileOutputStream fout = me.openFileOutput("lang-tex.js",
						Context.MODE_WORLD_WRITEABLE);
				InputStream in = null;
				in = this.getResources().openRawResource(R.raw.tex);
				int temp = in.read();
				while (temp != -1) {
					fout.write(temp);
					temp = in.read();
				}
				in.close();
				fout.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		res = Arrays.binarySearch(file, "lang-vb.js");
		if (res < 0) {
			try {
				FileOutputStream fout = me.openFileOutput("lang-vb.js",
						Context.MODE_WORLD_WRITEABLE);
				InputStream in = null;
				in = this.getResources().openRawResource(R.raw.vb);
				int temp = in.read();
				while (temp != -1) {
					fout.write(temp);
					temp = in.read();
				}
				in.close();
				fout.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		res = Arrays.binarySearch(file, "lang-vhdl.js");
		if (res < 0) {
			try {
				FileOutputStream fout = me.openFileOutput("lang-vhdl.js",
						Context.MODE_WORLD_WRITEABLE);
				InputStream in = null;
				in = this.getResources().openRawResource(R.raw.vhdl);
				int temp = in.read();
				while (temp != -1) {
					fout.write(temp);
					temp = in.read();
				}
				in.close();
				fout.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		res = Arrays.binarySearch(file, "lang-wiki.js");
		if (res < 0) {
			try {
				FileOutputStream fout = me.openFileOutput("lang-wiki.js",
						Context.MODE_WORLD_WRITEABLE);
				InputStream in = null;
				in = this.getResources().openRawResource(R.raw.wiki);
				int temp = in.read();
				while (temp != -1) {
					fout.write(temp);
					temp = in.read();
				}
				in.close();
				fout.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		res = Arrays.binarySearch(file, "lang-xq.js");
		if (res < 0) {
			try {
				FileOutputStream fout = me.openFileOutput("lang-xq.js",
						Context.MODE_WORLD_WRITEABLE);
				InputStream in = null;
				in = this.getResources().openRawResource(R.raw.xq);
				int temp = in.read();
				while (temp != -1) {
					fout.write(temp);
					temp = in.read();
				}
				in.close();
				fout.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		res = Arrays.binarySearch(file, "lang-yaml.js");
		if (res < 0) {
			try {
				FileOutputStream fout = me.openFileOutput("lang-yaml.js",
						Context.MODE_WORLD_WRITEABLE);
				InputStream in = null;
				in = this.getResources().openRawResource(R.raw.sql);
				int temp = in.read();
				while (temp != -1) {
					fout.write(temp);
					temp = in.read();
				}
				in.close();
				fout.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		res = Arrays.binarySearch(file, "lang-scala.js");
		if (res < 0) {
			try {
				FileOutputStream fout = me.openFileOutput("lang-scala.js",
						Context.MODE_WORLD_WRITEABLE);
				InputStream in = null;
				in = this.getResources().openRawResource(R.raw.scala);
				int temp = in.read();
				while (temp != -1) {
					fout.write(temp);
					temp = in.read();
				}
				in.close();
				fout.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
