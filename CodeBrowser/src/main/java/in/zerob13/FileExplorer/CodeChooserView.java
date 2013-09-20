package in.zerob13.FileExplorer;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by zerob13 on 9/20/13.
 */
public class CodeChooserView extends ListView {

	private CodeScanResult result;
	private Handler resultHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case CodeScanResult.MESSAGE_SCANNER_FINISHED:
					result = (CodeScanResult) msg.obj;
					break;
				default:
					break;
			}
		}
	};

	public CodeChooserView(Context context) {
		this(context, null);
	}

	public CodeChooserView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CodeChooserView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

}
