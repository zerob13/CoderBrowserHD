package in.zerob13.CodeBrowserHD.View;

import in.zerob13.CodeBrowserHD.GlobalConfig;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class ZeroWebView extends WebView {

	public ZeroWebView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public ZeroWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public ZeroWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	@Deprecated
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		setMeasuredDimension(GlobalConfig.my_width, GlobalConfig.my_height);
	}
}
