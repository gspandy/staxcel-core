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

import org.staxcel.internal.SpreadSheetImpl;

/**
 * This class instance is used as entry point by all clients to start generating
 * Excel file.
 * 
 * @author Gopinath.MR
 * 
 */
public class StaxcelFactory {

	/** singleton instance **/
	private static StaxcelFactory theInstance = null;

	/**
	 * Default constructor.
	 */
	private StaxcelFactory() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Returns reference to singleton instance. This method is thread-safe.\
	 * 
	 * @return single instance of StaxcelFactory class.
	 */
	public static StaxcelFactory getInstance() {
		if (theInstance == null) {
			synchronized (StaxcelFactory.class) {
				if (theInstance == null) {
					theInstance = new StaxcelFactory();
				}
			}
		}
		return theInstance;
	}

	/**
	 * Creates Spreadsheet using the configuration information specified.
	 * 
	 * @param spreadSheetName
	 *            name of the spreadsheet file (without .xlsx extension)
	 * @param configTemplate
	 *            configuration used to generate spreadsheet.
	 * @return instance of Spreadsheet which is logical representation of
	 *         SpreadSheet.
	 * @throws StaxcelException
	 *             if any exception occurs
	 */
	public SpreadSheet createSpreadSheet(String spreadSheetName,
			SpreadSheetConfig spreadSheetConfig) throws StaxcelException {
		return new SpreadSheetImpl(spreadSheetName, spreadSheetConfig);
	}
}
