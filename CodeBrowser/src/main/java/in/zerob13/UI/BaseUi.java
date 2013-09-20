package in.zerob13.UI;

import android.app.Activity;
import android.content.res.Configuration;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import in.zerob13.CodeBrowserHD.R;

/**
 * Created by zerob13 on 9/20/13.
 */
public class BaseUi implements UI {

	private static final String LOGTAG = "BaseUi";

	protected static final FrameLayout.LayoutParams COVER_SCREEN_PARAMS = new FrameLayout.LayoutParams(
			ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

	protected static final FrameLayout.LayoutParams COVER_SCREEN_GRAVITY_CENTER = new FrameLayout.LayoutParams(
			ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER);

    Activity mActivity;
    UiController mUiController;

    protected FrameLayout mFrameLayout;
    protected View mContentView;

    public BaseUi(Activity activity, UiController controller) {
        mActivity = activity;
        mUiController=controller;
        mFrameLayout = (FrameLayout) mActivity.getWindow()
                .getDecorView().findViewById(android.R.id.content);
        mContentView = new TextView(mActivity);
        ((TextView)mContentView).setText(R.string.hello_world);
        mFrameLayout.addView(mContentView,COVER_SCREEN_GRAVITY_CENTER);
    }



	@Override
	public void onPause() {

	}

	@Override
	public void onResume() {

	}

	@Override
	public void onDestroy() {

	}

	@Override
	public void onConfigurationChanged(Configuration config) {

	}

	@Override
	public boolean onBackKey() {
		return false;
	}

	@Override
	public boolean onMenuKey() {
		return false;
	}

	@Override
	public boolean dispatchKey(int code, KeyEvent event) {
		return false;
	}
}
