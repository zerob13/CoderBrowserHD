package in.zerob13.FileExplorer;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by zerob13 on 9/20/13.
 */
public class CodeChooserAdapter extends BaseAdapter {

	CodeScanResult curResult;

	public void setResult(CodeScanResult result) {
		curResult = result;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return curResult.dirLists.size() + curResult.fileLists.size();
	}

	@Override
	public Object getItem(int position) {
		if (position < curResult.dirLists.size()) {
			return curResult.dirLists.get(position);
		} else {
			return curResult.fileLists.get(position - curResult.dirLists.size());
		}
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return null;
	}
}
