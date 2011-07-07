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
package org.staxcel;

import java.io.IOException;
import java.net.URL;

import net.jcip.annotations.ThreadSafe;

/**
 * Logical representation of a SpreadSheet .xlsx file.
 * 
 * @author Gopinath.MR
 * 
 */
@ThreadSafe
public interface SpreadSheet {

	/**
	 * Returns SpreadSheet name.
	 * 
	 * @return
	 */
	String getSpreadSheetName();

	/**
	 * Returns URL path for spreadsheet file generated.
	 * 
	 * @return Returns URL of generated excel file
	 * @throws StaxcelRuntimeException
	 *             If file generation is not yet completed, it will throw
	 *             exception.
	 */
	URL getSpreadSheetURL() throws StaxcelRuntimeException;

	/**
	 * Closes SpreadSheet. This will create the final .xlsx file
	 * 
	 * @throws StaxcelRuntimeException
	 *             If any writers are still open, it will throw exception
	 */
	URL close() throws IOException;

	/**
	 * Creates an excel work sheet and returns reference to it. This method is
	 * thread-safe.
	 * 
	 * @param worksheetName
	 *            name of the worksheet.
	 * @return Reference to logical representation of an excel work sheet
	 * @throws StaxcelRuntimeException
	 *             if another worksheet already created with same name or
	 *             worksheet name provided is greater than max length of
	 *             worksheet name is Excel.
	 */
	Worksheet createWorksheet(String worksheetName, long maxColumns)
			throws StaxcelRuntimeException;
}
