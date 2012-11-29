package in.zerob13.CodeBrowser;

import java.util.Vector;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class fileData {
	public String name;
	public int type;
}

class ExpBaseAdapter extends BaseAdapter {
	private Context mContext;
	private Vector<fileData> mItems = new Vector<fileData>();

	// private LinearLayout layout,layout_more;

	public ExpBaseAdapter(Context context) {
		mContext = context;
	}

	public void addItem(fileData it) {
		mItems.add(it);
	}

	public fileData getItem(int it) {
		return (fileData) mItems.elementAt(it);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return mItems.size();
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	public int getItemType(int arg0) {
		// TODO Auto-generated method stub
		return getItem(arg0).type;
	}

	public void clearItems() {
		mItems.clear();
	}

	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		// LinearLayout layout=null ;
		LayoutInflater inflate = (LayoutInflater) mContext
				.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
		arg1 = (LinearLayout) inflate.inflate(R.layout.listfile, null);
		TextView fm_text_01 = (TextView) arg1.findViewById(R.id.fm_text_01);
		if (getItem(arg0).type == 1) {
			fm_text_01.setTextColor(Color.WHITE);
		} else {
			fm_text_01.setTextColor(Color.BLUE);
		}
		fm_text_01.setText(getItem(arg0).name + "\n");

		// Log.v("vodone",""+arg0+" name="+getItem(arg0).name);

		/*
		 * if (getItem(arg0).equals(".")){
		 * 
		 * }
		 */

		return arg1;
	}

}
