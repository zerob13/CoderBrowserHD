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

package in.zerob13.CodeBrowserHD;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.TextView;

import in.zerob13.Controller.Controller;
import in.zerob13.UI.BaseUi;
import in.zerob13.UI.UI;

public class CodeActivity extends Activity {

	private Controller mController = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mController = createController();
		mController.start(getIntent());

	}

	private Controller createController() {
		Controller controller = new Controller(this);
		UI ui = null;
		ui = new BaseUi(this, controller);
		controller.setUi(ui);
		controller.setCurrentLayout(R.layout.activity_file);
		return controller;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return mController.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onPause() {
		mController.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mController.onResume();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		mController.onDestroy();
		super.onDestroy();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mController.onConfgurationChanged(newConfig);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}
}
