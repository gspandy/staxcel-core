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
package org.staxcel.template;

import java.io.IOException;

import javax.xml.stream.XMLStreamWriter;

/**
 * This interface should be implemented by class which can expand given token into equivalent xml fragment data.
 * The implementation class should register with staxcel framework and use the tokens in template files with syntax {@tokenName@}.
 * 
 * @author Gopinath.MR
 * 
 */
public interface TemplateRenderer {
	
	/**
	 * This method should write content equivalent to specified tokenName if it
	 * understands token and return <code>true</code> else return
	 * <code>false</code>.
	 * 
	 * @param templateFileRelativePath
	 *            The relative path of template file name
	 * @param tokenName
	 *            token name which is available in template file as
	 *            {@tokenName@}
	 * @param xmlWriter
	 *            writer pointing to generated xml file.
	 * @return returns <code>true</code> if token is expanded. If template
	 *         renderer does not understand token, return as <code>false</code>.
	 * @throws IOException
	 *             if any exception while writing data to xmlWriter
	 */
	boolean render(String templateFileRelativePath, String tokenName,
			XMLStreamWriter xmlWriter) throws IOException;
}
