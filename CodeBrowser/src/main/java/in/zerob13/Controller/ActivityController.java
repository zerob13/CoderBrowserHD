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

/**
 * Created by zerob13 on 9/20/13.
 */
public interface ActivityController {

	void start(Intent intent);

	void onSaveInstanceState(Bundle outState);

	void handleNewIntent(Intent intent);

	void onResume();

	boolean onMenuOpened(int featureId, Menu menu);

	void onOptionsMenuClosed(Menu menu);

	void onContextMenuClosed(Menu menu);

	void onPause();

	void onDestroy();

	void onConfgurationChanged(Configuration newConfig);

	void onLowMemory();

	boolean onCreateOptionsMenu(Menu menu);

	boolean onPrepareOptionsMenu(Menu menu);

	boolean onOptionsItemSelected(MenuItem item);

	void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo);

	boolean onContextItemSelected(MenuItem item);

	boolean onKeyDown(int keyCode, KeyEvent event);

	boolean onKeyLongPress(int keyCode, KeyEvent event);

	boolean onKeyUp(int keyCode, KeyEvent event);

	void onActionModeStarted(ActionMode mode);

	void onActionModeFinished(ActionMode mode);

	void onActivityResult(int requestCode, int resultCode, Intent intent);

	boolean onSearchRequested();

	boolean dispatchKeyEvent(KeyEvent event);

	boolean dispatchKeyShortcutEvent(KeyEvent event);

	boolean dispatchTouchEvent(MotionEvent ev);

	boolean dispatchTrackballEvent(MotionEvent ev);

	boolean dispatchGenericMotionEvent(MotionEvent ev);
}
