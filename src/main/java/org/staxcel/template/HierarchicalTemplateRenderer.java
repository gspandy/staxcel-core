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
 * This class support Hierarchical template renderer similar to how ClassLoader
 * works in Java. The preference is first given to return value of method
 * <code>render0(String templateFileRelativePath, String tokenName,
 * XMLStreamWriter xmlWriter)</code> . If it cannot understand, then parent is
 * invoked to replace token.
 * 
 * @author Gopinath.MR
 * 
 */
public abstract class HierarchicalTemplateRenderer implements TemplateRenderer {

	/** parent template renderer */
	private TemplateRenderer parentTemplateRenderer;

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            parent renderer to be invoked if render0 implementation cannot
	 *            understand a given token.
	 */
	public HierarchicalTemplateRenderer(TemplateRenderer parent) {
		this.parentTemplateRenderer = parent;
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
		boolean value = render0(templateFileRelativePath, tokenName, xmlWriter);
		if (value == false) {
			value = this.getParentTemplateRenderer().render(
					templateFileRelativePath, tokenName, xmlWriter);
		}
		return value;
	}

	/**
	 * This method should be implemented by derived class to replace the token
	 * value in generated xml.
	 * 
	 * @param templateFileRelativePath
	 *            relative path of template which is used to generate xml
	 * @param tokenName
	 *            token available in template file which need to be replaced by
	 *            writing data to generated xml
	 * @param xmlWriter
	 *            to be used to write content to generated xml
	 * @return returns <code>true</code> if token is expanded. If template
	 *         renderer does not understand token, return as <code>false</code>.
	 * @throws IOException
	 *             if any exception while writing data to xmlWriter
	 */
	public abstract boolean render0(String templateFileRelativePath,
			String tokenName, XMLStreamWriter xmlWriter) throws IOException;

	public TemplateRenderer getParentTemplateRenderer() {
		return parentTemplateRenderer;
	}

	public void setParentTemplateRenderer(
			TemplateRenderer parentTemplateRenderer) {
		this.parentTemplateRenderer = parentTemplateRenderer;
	}
}
