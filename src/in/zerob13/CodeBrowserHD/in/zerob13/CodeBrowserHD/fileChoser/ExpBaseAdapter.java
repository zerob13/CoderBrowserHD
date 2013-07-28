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
import in.zerob13.CodeBrowserHD.GlobalConfig;
import in.zerob13.CodeBrowserHD.R;
import net.youmi.android.AdView;

public class ExpBaseAdapter extends BaseAdapter {
	private Context mContext;
	private Vector<fileData> mItems = new Vector<fileData>();
	private AdView mAdView;
	private LinearLayout mLinearLayout;

	public ExpBaseAdapter(Context context) {
		mContext = context;
		mAdView = new AdView((Activity) mContext);
		mLinearLayout = new LinearLayout(mContext);
		mLinearLayout.addView(mAdView, new ViewGroup.LayoutParams(GlobalConfig.my_width,
				(int) (80 * GlobalConfig.my_des)));
	}

	public void addItem(fileData it) {
		mItems.add(it);
	}

	public fileData getItem(int it) {
		return (fileData) mItems.elementAt(it);
	}

	public int getCount() {
		return mItems.size();
	}

	public long getItemId(int arg0) {
		return arg0;
	}

	public int getItemType(int arg0) {
		return getItem(arg0).type;
	}

	public void clearItems() {
		mItems.clear();
	}

	public View getView(int arg0, View arg1, ViewGroup arg2) {
		if (getItem(arg0).name.equals("....") && getItem(arg0).type == -1) {
			return mLinearLayout;
		}
		LayoutInflater inflate = (LayoutInflater) mContext
				.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
		arg1 = inflate.inflate(R.layout.listfile, null);
		TextView fm_text_01 = (TextView) arg1.findViewById(R.id.fm_text_01);
		if (getItem(arg0).type == 1) {
			fm_text_01.setTextColor(Color.WHITE);
		} else {
			fm_text_01.setTextColor(Color.BLUE);
		}
		fm_text_01.setText(getItem(arg0).name + "\n");

		return arg1;
	}

}