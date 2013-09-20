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

import android.webkit.MimeTypeMap;

/**
 * Created by zerob13 on 9/20/13.
 */
public final class FileUtils {

	private FileUtils() {
	};

	/**
	 * get file's extension
	 * 
	 * @param uri
	 *            file uri
	 * @return extension name
	 */
	public static String getExtension(String uri) {
		if (uri == null) {
			return null;
		}

		int dot = uri.lastIndexOf(".");
		if (dot >= 0) {
			return uri.substring(dot);
		} else {
			return "";
		}
	}

    /**
     * get file mime types
     * @param filename file name
     * @return mimetypes
     */
	public static String getMimeType(String filename) {

		String extension = FileUtils.getExtension(filename);

		if (extension.length() > 0) {
			String webkitMimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
					extension.substring(1));

			if (webkitMimeType != null) {
				return webkitMimeType;
			}
		}

		return "*/*";

	}
}
