/*******************************************************************************
 * Copyright 2011 StaXcel
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package org.staxcel.internal;

import java.io.File;

/**
 * @author gopinath.mr
 * 
 */
public interface OpenXMLFormatConstants {

	public static final String CONTENT_TYPE_FILE_NAME = "[Content_Types].xml";

	public static final String RELS_FILE_NAME = "_rels" + File.separator
			+ ".rels";

	public static final String DOCS_CORE_FILE_NAME = "docProps"
			+ File.separator + "core.xml";

	public static final String STYLES_FILE_NAME = "xl" + File.separator
			+ "styles.xml";

	public static final String THEMES_FILE_NAME = "xl" + File.separator
			+ "theme" + File.separator + "theme1.xml";

	public static final String DOCS_APP_FILE_NAME = "docProps" + File.separator
			+ "app.xml";

	public static final String WORKBOOK_RELS_FILE_NAME = "xl" + File.separator
			+ "_rels" + File.separator + "workbook.xml.rels";

	public static final String WORKBOOK_FILE_NAME = "xl" + File.separator
			+ "workbook.xml";

	public static final String SHARED_STRING_FILE_NAME = "xl" + File.separator
			+ "sharedStrings.xml";
}
