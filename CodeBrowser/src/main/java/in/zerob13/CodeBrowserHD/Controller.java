package in.zerob13.CodeBrowserHD;

import android.app.Activity;
import android.view.View;

import in.zerob13.UI.UI;
import in.zerob13.UI.UiController;

/**
 * Created by zerob13 on 9/20/13.
 */
public class Controller implements UiController{

    private Activity mActivity;
    private UI mUi;

    public Controller(Activity activity) {
        mActivity = activity;

    }

    void setUi(UI ui) {
        mUi = ui;
    }

    @Override
    public UI getUi() {
        return mUi;
    }

    @Override
    public View getCurrentView() {
        return null;
    }

    @Override
    public Activity getActivity() {
        return mActivity;
    }
}
