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

package in.zerob13.FileExplorer;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.os.Handler;
import android.os.Message;

/**
 * Created by zerob13 on 9/20/13.
 */
public class CodeFileScanner extends Thread {

	private static final int PROGRESS_STEPS = 50;

	private File mCurrentDirectory;
	private Handler mHandler;

	private String mFilterFiletype = "";
	private String mFilterMimetype = "text/*";
	private boolean cancel;

	public CodeFileScanner(String dir, Handler msgHandler) {
		mCurrentDirectory = new File(dir);
		mHandler = msgHandler;
	}

	@Override
	public void run() {
		File[] files = mCurrentDirectory.listFiles();
		int totalCount = 0;
		if (cancel) {
			clearData();
			return;
		}
		if (files == null) {
			totalCount = 0;
		} else {
			totalCount = files.length;
		}
		int progress = 0;
		/** Dir separate for sorting */
		List<File> dirLists = new ArrayList<File>(totalCount);
		/** Files separate for sorting */
		List<File> filesLists = new ArrayList<File>(totalCount);
		// start scan files
		if (files != null) {
			for (File currentFile : files) {
				progress++;
				// skip hidden files
				if (currentFile.isHidden()) {
					continue;
				}
				if (currentFile.isDirectory()) {
					if (currentFile.canWrite()) {
						dirLists.add(currentFile);
					}
				} else {
					String fileName = currentFile.getName();
					String mimetype = FileUtils.getMimeType(fileName);
					String filetype = FileUtils.getExtension(fileName);
					boolean ext_allow = filetype.equalsIgnoreCase(mFilterFiletype) || mFilterFiletype == "";
					boolean mime_allow = mFilterMimetype != null
							&& (mimetype.contentEquals(mFilterMimetype)
									|| mFilterMimetype.contentEquals("*/*") || mFilterFiletype == null);
					if (ext_allow || mime_allow) {
						filesLists.add(currentFile);
					}
				}
			}
		}
		//sort lists
		Collections.sort(dirLists, new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {

				return o1.getName().compareTo(o2.getName());
			}
		});

		Collections.sort(filesLists, new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {

				return o1.getName().compareTo(o2.getName());
			}
		});

        //finish scan
		if (!cancel) {
			CodeScanResult contents = new CodeScanResult();
			contents.dirLists = dirLists;
			contents.fileLists = filesLists;
			contents.mCurDir = mCurrentDirectory.getAbsolutePath();
			Message msg = mHandler.obtainMessage(CodeScanResult.MESSAGE_SCANNER_FINISHED);
			msg.obj = contents;
			msg.sendToTarget();
		}

		clearData();

	}

	private void clearData() {
		// Remove all references
		mHandler = null;
	}

}
