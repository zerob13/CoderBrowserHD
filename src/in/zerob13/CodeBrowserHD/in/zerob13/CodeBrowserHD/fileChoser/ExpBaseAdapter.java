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

import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import in.zerob13.CodeBrowserHD.in.zerob13.CodeBrowserHD.main.GlobalConfig;
import in.zerob13.CodeBrowserHD.R;
import net.youmi.android.AdView;

public class ExpBaseAdapter extends BaseAdapter {
	private Context mContext;
	private Vector<FileData> mItems = new Vector<FileData>();
	private AdView mAdView;
	private LinearLayout mLinearLayout;

	public ExpBaseAdapter(Context context) {
		mContext = context;
		mAdView = new AdView((Activity) mContext);
		mLinearLayout = new LinearLayout(mContext);
		mLinearLayout.addView(mAdView, new ViewGroup.LayoutParams(GlobalConfig.sWidth,
				(int) (80 * GlobalConfig.sDes)));
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
		LayoutInflater inflate = (LayoutInflater) mContext
				.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
		arg1 = inflate.inflate(R.layout.listfile, null);
		TextView fmText01 = (TextView) arg1.findViewById(R.id.fm_text_01);
		if (getItem(arg0).mType == 1) {
			fmText01.setTextColor(Color.WHITE);
		} else {
			fmText01.setTextColor(Color.BLUE);
		}
		fmText01.setText(getItem(arg0).mName + "\n");

		return arg1;
	}

}