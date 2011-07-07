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

import java.util.HashMap;
import java.util.ResourceBundle;

import org.staxcel.SpreadSheetConfig;
import org.staxcel.template.TemplateRenderer;

/**
 * @author Gopinath.MR
 * 
 */
public class SpreadSheetConfigImpl implements SpreadSheetConfig {

	protected ResourceBundle resourceBundler = null;

	protected TemplateRenderer templateRenderer = null;

	/**
	 * 
	 */
	public SpreadSheetConfigImpl(ResourceBundle resourceBundle) {
		this.resourceBundler = resourceBundle;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.staxcel.SpreadSheetConfigTemplate#getProperty(java.lang.String)
	 */
	@Override
	public String getProperty(String propertyName) {
		return this.resourceBundler.getString(propertyName);
	}

	@Override
	public void fillInContext(HashMap<String, String> context) {

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TemplateRenderer getTemplateRenderer() {
		return templateRenderer;
	}

	public void setTemplateRenderer(TemplateRenderer templateRenderer) {
		this.templateRenderer = templateRenderer;
	}
}
