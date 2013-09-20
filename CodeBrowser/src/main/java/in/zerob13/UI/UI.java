/*
 * Copyright (C) 2013 Lingfeng Yang (http://www.zerob13.in)
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package in.zerob13.UI;

import android.content.res.Configuration;
import android.view.KeyEvent;

/**
 * Created by zerob13 on 9/20/13.
 */
public interface UI {

	public void onPause();

	public void onResume();

	public void onDestroy();

	public void onConfigurationChanged(Configuration config);

	public boolean onBackKey();

	public boolean onMenuKey();

	boolean dispatchKey(int code, KeyEvent event);

}
