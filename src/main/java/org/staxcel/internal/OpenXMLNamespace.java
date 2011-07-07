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

/**
 * Contains constants for all xml namespace URIs required to generate Excel
 * file.
 * 
 * @author Gopinath.MR
 * 
 */
public interface OpenXMLNamespace {

	public static final String NS_PURL_DCMITYPE = "http://purl.org/dc/dcmitype/";
	public static final String NS_PURL_ELM_11 = "http://purl.org/dc/elements/1.1/";
	public static final String NS_PURL_DC_TERMS = "http://purl.org/dc/terms/";

	public static final String NS_MSO_SPREADSHEETML_2009_AC = "http://schemas.microsoft.com/office/spreadsheetml/2009/9/ac";
	public static final String NS_MSO_SPREADSHEETML_2009_MAIN = "http://schemas.microsoft.com/office/spreadsheetml/2009/9/main";

	public static final String NS_OPENXML_DRAWINGML_2006_MAIN = "http://schemas.openxmlformats.org/drawingml/2006/main";
	public static final String NS_OPENXML_COMPATIBILITY_2006 = "http://schemas.openxmlformats.org/markup-compatibility/2006";

	public static final String NS_OPENXML_OFFICE_DOC_2006_DOC_PROPS_VTYPES = "http://schemas.openxmlformats.org/officeDocument/2006/docPropsVTypes";
	public static final String NS_OPENXML_OFFICE_DOC_2006_EXT_PROPS = "http://schemas.openxmlformats.org/officeDocument/2006/extended-properties";

	public static final String NS_OPENXML_OFFICE_DOC_2006_REL = "http://schemas.openxmlformats.org/officeDocument/2006/relationships";
	public static final String NS_OPENXML_OFFICE_DOC_2006_REL_EXT_PROPS = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/extended-properties";
	public static final String NS_OPENXML_OFFICE_DOC_2006_REL_OFFICE_DOC = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument";
	public static final String NS_OPENXML_OFFICE_DOC_2006_REL_SHARED_STRINGS = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/sharedStrings";
	public static final String NS_OPENXML_OFFICE_DOC_2006_REL_STYLES = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/styles";
	public static final String NS_OPENXML_OFFICE_DOC_2006_REL_THEME = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/theme";
	public static final String NS_OPENXML_OFFICE_DOC_2006_REL_WORKSHEET = "http://schemas.openxmlformats.org/officeDocument/2006/relationships/worksheet";

	public static final String NS_OPENXML_PKG_2006_CONTENT_TYPE = "http://schemas.openxmlformats.org/package/2006/content-types";
	public static final String NS_OPENXML_PKG_2006_MD_CORE_PROPS = "http://schemas.openxmlformats.org/package/2006/metadata/core-properties";
	public static final String NS_OPENXML_PKG_2006_REL_MD_CORE_PROPS = "http://schemas.openxmlformats.org/package/2006/relationships/metadata/core-properties";

	public static final String NS_OPENXML_SPREADSHEETML_2006_MAIN = "http://schemas.openxmlformats.org/spreadsheetml/2006/main";
	public static final String NS_W3_2001_XML_SCHEMA_INST = "http://www.w3.org/2001/XMLSchema-instance";
}
