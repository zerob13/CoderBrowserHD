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
import java.util.List;

/**
 * Created by zerob13 on 9/20/13.
 */
public class CodeScanResult {
	public static final int MESSAGE_SCANNER_FINISHED = 200; // List of contents is ready, obj = result

	public String mCurDir;
	public List<File> dirLists;
	public List<File> fileLists;
}
