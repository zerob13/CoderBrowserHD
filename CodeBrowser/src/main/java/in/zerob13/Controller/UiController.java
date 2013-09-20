package in.zerob13.Controller;

import android.app.Activity;
import android.view.View;

import in.zerob13.UI.UI;

/**
 * Created by zerob13 on 9/20/13.
 */
public interface UiController {

	UI getUi();

	View getCurrentRootView();

	Activity getActivity();

    void setCurrentLayout(View view);

    void setCurrentLayout(int res);

}
