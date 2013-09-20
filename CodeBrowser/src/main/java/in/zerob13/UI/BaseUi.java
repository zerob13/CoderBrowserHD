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

import android.app.Activity;
import android.content.res.Configuration;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import in.zerob13.Controller.UiController;

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

	public BaseUi(Activity activity, UiController controller) {
		mActivity = activity;
		mUiController = controller;
		mFrameLayout = (FrameLayout) mActivity.getWindow().getDecorView().findViewById(android.R.id.content);
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

	@Override
	public void setContentLayout(View view) {
		mFrameLayout.removeAllViews();
		mFrameLayout.addView(view, COVER_SCREEN_GRAVITY_CENTER);
	}

	@Override
	public void setContentLayout(int res) {
		mFrameLayout.removeAllViews();
		LayoutInflater.from(mActivity).inflate(res, mFrameLayout);
	}
}