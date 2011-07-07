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

import java.util.HashMap;

import org.staxcel.template.TemplateRenderer;

/**
 * Base interface containing the methods to get configuration required for
 * spreadsheet generation.
 * 
 * @author Gopinath.MR
 * 
 */
public interface SpreadSheetConfig {

	/**
	 * returns property value.
	 * 
	 * @param propertyName
	 * @return returns property value if exists else returns <code>null</code>.
	 */
	String getProperty(String propertyName);

	/**
	 * Fill any application related context to be overriden.
	 * 
	 * @param context
	 */
	void fillInContext(HashMap<String, String> context);

	/**
	 * Returns template renderer to be used to replace any tokens.
	 * 
	 * @return template renderer if configured else return <code>null</code>.
	 */
	TemplateRenderer getTemplateRenderer();
}