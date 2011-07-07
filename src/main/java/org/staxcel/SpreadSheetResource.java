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

/**
 * Every xml file within Spreadsheet which is identified with resourceId. Any
 * class which represents one of the xml files in .xlsx should implement this
 * interface.
 * 
 * @author Gopinath.MR
 * 
 * @see Worksheet
 */
public interface SpreadSheetResource {

	/**
	 * Set resource id.
	 * 
	 * @param resourceId
	 *            resource Id
	 */
	void setResourceId(int resourceId);

	/**
	 * Returns resource id
	 * 
	 * @return
	 */
	int getResourceId();
}
