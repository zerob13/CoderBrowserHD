/*
 * Filename:    ExpBaseAdapter.java
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

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Vector;

import in.zerob13.CodeBrowserHD.in.zerob13.CodeBrowserHD.main.GlobalConfig;

/**
 * 文件listView的Adapter
 */
public class ExpBaseAdapter extends BaseAdapter {
	private Context mContext;
	private Vector<FileData> mItems = new Vector<FileData>();
	private LinearLayout mLinearLayout;

	public ExpBaseAdapter(Context context) {
		mContext = context;
		mLinearLayout = new LinearLayout(mContext);
	}

	public void addItem(FileData it) {
		mItems.add(it);
	}

	public FileData getItem(int it) {
		return (FileData) mItems.elementAt(it);
	}

	public int getCount() {
		return mItems.size();
	}

	public long getItemId(int arg0) {
		return arg0;
	}

	public int getItemType(int arg0) {
		return getItem(arg0).mType;
	}

	public void clearItems() {
		mItems.clear();
	}

	public View getView(int arg0, View arg1, ViewGroup arg2) {
		if (getItem(arg0).mName.equals("....") && getItem(arg0).mType == -1) {
			return mLinearLayout;
		}
		TextView fmText01 = new TextView(mContext);
		if (getItem(arg0).mType == 1) {
			fmText01.setTextColor(0xfff0f0f0);
		} else {
			fmText01.setTextColor(Color.RED);
		}
		fmText01.setText(getItem(arg0).mName + "\n");
        ListView.LayoutParams layoutParams =  new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT,
                (int) (50 * GlobalConfig.sDes));
		fmText01.setLayoutParams(layoutParams);
        fmText01.setTextSize(19);
        fmText01.setGravity(Gravity.CENTER_VERTICAL);

		return fmText01;
	}

}