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
 * 
 * @author Gopinath.MR
 * 
 */
public class CommandBasedTemplateRenderer implements TemplateRenderer {

	/**
	 * 
	 */
	public CommandBasedTemplateRenderer() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.staxcel.template.TemplateRenderer#render(java.lang.String,
	 * java.lang.String, javax.xml.stream.XMLStreamWriter)
	 */
	@Override
	public boolean render(String templateFileRelativePath, String tokenName,
			XMLStreamWriter xmlWriter) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

}
