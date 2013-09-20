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

package in.zerob13.Controller;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import in.zerob13.CodeBrowserHD.R;
import in.zerob13.UI.UI;

/**
 * Created by zerob13 on 9/20/13.
 */
public class Controller implements UiController, ActivityController {

	private Activity mActivity;
	private UI mUi;

	public Controller(Activity activity) {
		mActivity = activity;

	}

	public void setUi(UI ui) {
		mUi = ui;
	}

	@Override
	public UI getUi() {
		return mUi;
	}

	@Override
	public View getCurrentRootView() {
		return null;
	}

	@Override
	public Activity getActivity() {
		return mActivity;
	}

	@Override
	public void setCurrentLayout(View view) {
		mUi.setContentLayout(view);
	}

	@Override
	public void setCurrentLayout(int res) {
		mUi.setContentLayout(res);

	}

	@Override
	public void start(Intent intent) {

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {

	}

	@Override
	public void handleNewIntent(Intent intent) {

	}

	@Override
	public void onResume() {

	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		return false;
	}

	@Override
	public void onOptionsMenuClosed(Menu menu) {

	}

	@Override
	public void onContextMenuClosed(Menu menu) {

	}

	@Override
	public void onPause() {
		mUi.onPause();

	}

	@Override
	public void onDestroy() {
		mUi.onDestroy();

	}

	@Override
	public void onConfgurationChanged(Configuration newConfig) {
		mUi.onConfigurationChanged(newConfig);

	}

	@Override
	public void onLowMemory() {

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		mActivity.getMenuInflater().inflate(R.menu.file, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return false;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		return false;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;
	}

	@Override
	public boolean onKeyLongPress(int keyCode, KeyEvent event) {
		return false;
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		return false;
	}

	@Override
	public void onActionModeStarted(ActionMode mode) {

	}

	@Override
	public void onActionModeFinished(ActionMode mode) {

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {

	}

	@Override
	public boolean onSearchRequested() {
		return false;
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		return false;
	}

	@Override
	public boolean dispatchKeyShortcutEvent(KeyEvent event) {
		return false;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return false;
	}

	@Override
	public boolean dispatchTrackballEvent(MotionEvent ev) {
		return false;
	}

	@Override
	public boolean dispatchGenericMotionEvent(MotionEvent ev) {
		return false;
	}
}
