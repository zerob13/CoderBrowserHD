package in.zerob13.UI;

import android.app.Activity;
import android.view.View;

/**
 * Created by zerob13 on 9/20/13.
 */
public interface UiController {
	UI getUi();

	View getCurrentView();

	Activity getActivity();

}
