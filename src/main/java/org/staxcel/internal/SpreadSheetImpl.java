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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.Properties;
import java.util.Vector;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import net.jcip.annotations.ThreadSafe;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.staxcel.SpreadSheet;
import org.staxcel.SpreadSheetConfig;
import org.staxcel.StaxcelConfig;
import org.staxcel.StaxcelException;
import org.staxcel.StaxcelRuntimeException;
import org.staxcel.Worksheet;
import org.staxcel.template.StreamBasedTemplateEngine;
import org.staxcel.template.TokenEnumerableTemplateWriter;

/**
 * This class is logical representation of a .xlsx file during creation.
 * 
 * @author gopinath.mr
 * 
 */
@ThreadSafe
public class SpreadSheetImpl implements SpreadSheet {

	private static final Log logger = LogFactory.getLog(SpreadSheetImpl.class);

	protected String spreadSheetName;

	protected SpreadSheetConfig config;

	protected File workingDir;

	protected URL spreadSheetURL;

	protected List<WorksheetImpl> worksheetList;

	protected TokenEnumerableTemplateWriter appXmlTemplateWriter;

	protected TokenEnumerableTemplateWriter workbookRelsXmlTemplateWriter;

	protected TokenEnumerableTemplateWriter workbookXmlTemplateWriter;

	protected Map<String, Integer> sharedStrings;

	protected Map<String, Integer> resource2IdMap;

	private int defaultBlockSize = 4096;

	/**
	 * Whether to delete intermediate files or not. By default it is true.
	 */
	private boolean shouldDeleteIntermediateFiles = true;

	/**
	 * Constructor.
	 * 
	 * @param config spreadSheetConfig
	 */
	public SpreadSheetImpl(String spreadSheetName,
			SpreadSheetConfig config)
			throws StaxcelRuntimeException {
		this.spreadSheetName = spreadSheetName;
		this.config = config;
		init();
	}

	/**
	 * Initialized SpreadSheet file creation. Creates folder and basic xml files
	 * related to basic excel file.
	 */
	protected void init() throws StaxcelRuntimeException {
		try {
			createBaseDir(config.getProperty(StaxcelConfig.BASE_DIR));
			this.workingDir = createWorkingDir(this.spreadSheetName);

			sharedStrings = new LinkedHashMap<String, Integer>();
			resource2IdMap = new HashMap<String, Integer>();
			HashMap<String, String> context = new HashMap<String, String>();
			getNextResourceId("sharedStrings.xml");

			populateDefaultContext(context);
			addTemplateFiles(context);
			createTemplateWriters(context);
		} catch (Exception e) {
			e.printStackTrace();
			throw new StaxcelRuntimeException(e);
		} finally {
			worksheetList = new Vector<WorksheetImpl>();
		}
	}

	protected void createBaseDir(String folderPath) {
		File file = new File(folderPath);
		file.mkdir();
	}

	protected File createWorkingDir(String spreadSheetName) {
		String workingDirPath = config
				.getProperty(StaxcelConfig.BASE_DIR)
				+ File.separator
				+ getWorkingDirName(spreadSheetName);
		System.out.println("working directory is : " + workingDirPath);
		File file = new File(workingDirPath);
		file.mkdir();
		return file;
	}

	protected String getWorkingDirName(String spreadSheetName) {
		return spreadSheetName;
	}

	protected void addTemplateFiles(HashMap<String, String> context)
			throws IOException {
		this.config.fillInContext(context);
		addContentType(context);
		addRelationshipFile(context);
		addDocumentPropertyFiles(context);
		addStyleFile(context);
		addThemeFile(context);
	}

	protected void createTemplateWriters(HashMap<String, String> context)
			throws IOException {
		createAppXmlTemplateWriter(context);
		createWorkbookRelationshipXmlTemplateWriter(context);
		createWorkbookXmlTemplateWriter(context);
	}

	/**
	 * Loads all system properties in context
	 * 
	 * @param context
	 */
	protected void populateDefaultContext(HashMap<String, String> context) {
		Properties systemProps = System.getProperties();
		for (Entry<Object, Object> entry : systemProps.entrySet()) {
			context.put(entry.getKey().toString(), entry.getValue().toString());
		}
	}

	/**
	 * creates content type file /[Content_Types].xml
	 * 
	 * @param context
	 * @throws IOException
	 */
	protected void addContentType(HashMap<String, String> context)
			throws IOException {
		createFromTemplate(OpenXMLFormatConstants.CONTENT_TYPE_FILE_NAME,
				context);
	}

	/**
	 * creates Relaship file /_rels/.rels
	 * 
	 * @param context
	 * @throws IOException
	 */
	protected void addRelationshipFile(HashMap<String, String> context)
			throws IOException {
		createFromTemplate(OpenXMLFormatConstants.RELS_FILE_NAME, context);
	}

	/**
	 * Creates files under /docProps folder
	 * 
	 * @param context
	 * @throws IOException
	 */
	protected void addDocumentPropertyFiles(HashMap<String, String> context)
			throws IOException {
		createCoreXml(context);
	}

	/**
	 * Creates docProps/core.xml
	 * 
	 * @param context
	 * @throws IOException
	 */
	protected void createCoreXml(HashMap<String, String> context)
			throws IOException {
		createFromTemplate(OpenXMLFormatConstants.DOCS_CORE_FILE_NAME, context);
	}

	/**
	 * Creates /xl/styles.xml file.
	 * 
	 * @param context
	 * @throws IOException
	 */
	protected void addStyleFile(HashMap<String, String> context)
			throws IOException {
		createFromTemplate(OpenXMLFormatConstants.STYLES_FILE_NAME, context);
		getNextResourceId(OpenXMLFormatConstants.STYLES_FILE_NAME);
	}

	/**
	 * Get next resource Id assigned to resource and return assigned resourceId
	 * 
	 * @param resourceName
	 *            name of resource (usually relative path of xml file) which
	 *            need to be tracked as resource
	 * @return resourceId assigned
	 */
	protected int getNextResourceId(String resourceName) {
		int resourceId = resource2IdMap.size() + 1;
		resource2IdMap.put(resourceName, Integer.valueOf(resourceId));
		return resourceId;
	}

	/**
	 * Creates /xl/theme\theme1.xml file.
	 * 
	 * @param context
	 * @throws IOException
	 */
	protected void addThemeFile(HashMap<String, String> context)
			throws IOException {
		createFromTemplate(OpenXMLFormatConstants.THEMES_FILE_NAME, context);
		getNextResourceId(OpenXMLFormatConstants.STYLES_FILE_NAME);
	}

	protected void createAppXmlTemplateWriter(HashMap<String, String> context)
			throws IOException {
		appXmlTemplateWriter = createTemplateWriter(context,
				OpenXMLFormatConstants.DOCS_APP_FILE_NAME);
	}

	protected void createWorkbookRelationshipXmlTemplateWriter(
			HashMap<String, String> context) throws IOException {
		workbookRelsXmlTemplateWriter = createTemplateWriter(context,
				OpenXMLFormatConstants.WORKBOOK_RELS_FILE_NAME);
	}

	protected void createWorkbookXmlTemplateWriter(
			HashMap<String, String> context) throws IOException {
		workbookXmlTemplateWriter = createTemplateWriter(context,
				OpenXMLFormatConstants.WORKBOOK_FILE_NAME);
	}

	protected void createFromTemplate(String path,
			HashMap<String, String> context) throws IOException {

		BufferedWriter bufWriter = createWriter(path);
		BufferedReader templateReader = createTemplateFileReader(path);
		StreamBasedTemplateEngine.applyTemplate(templateReader, bufWriter,
				context);
		bufWriter.flush();
		bufWriter.close();
	}

	protected TokenEnumerableTemplateWriter createTemplateWriter(
			HashMap<String, String> context, String path) throws IOException {

		BufferedWriter bufWriter = createWriter(path);
		BufferedReader templateReader = createTemplateFileReader(path);

		TokenEnumerableTemplateWriter templateWriter = new TokenEnumerableTemplateWriter(
				templateReader, bufWriter);
		if (templateWriter.hasMoreElements() == false) {
			templateWriter.getOutputWriter().flush();
			templateWriter.getOutputWriter().close();
		}
		return templateWriter;
	}

	protected BufferedReader createTemplateFileReader(
			String templateFileRelativePath) throws IOException {
		String templateDirPath = this.config
				.getProperty(StaxcelConfig.TEMPLATE_DIR);
		String templateFilePath = templateDirPath + File.separator
				+ templateFileRelativePath;
		return new BufferedReader(new FileReader(templateFilePath));
	}

	protected BufferedWriter createWriter(String relativePath)
			throws IOException {
		File destFile = new File(this.workingDir, relativePath);
		destFile.getParentFile().mkdirs();
		return new BufferedWriter(new FileWriter(destFile));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.staxcel.SpreadSheet#getSpreadSheetName()
	 */
	@Override
	public String getSpreadSheetName() {
		return this.spreadSheetName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.staxcel.SpreadSheet#getSpreadSheetURL()
	 */
	@Override
	public URL getSpreadSheetURL() throws StaxcelRuntimeException {
		return this.spreadSheetURL;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.staxcel.SpreadSheet#close()
	 */
	@Override
	public URL close() throws IOException {
		System.out.println("SpreadSheetImpl.Close invoked");
		addSharedStringsFile();
		flushPartialTemplateFiles();
		createXlsxFile();
		return spreadSheetURL;
	}

	/**
	 * 
	 * @throws IOException
	 */
	private void flushPartialTemplateFiles() throws IOException {
		XMLOutputFactory xmlOutFactory = XMLOutputFactory.newInstance();
		flushAppXmlFile(xmlOutFactory);
		flushWorkbookRelsXmlFile(xmlOutFactory);
		flushWorkbookXmlFile(xmlOutFactory);
	}

	/**
	 * Write remaining part of ./docProps/app.xml file
	 * 
	 * @param xmlOutFactory
	 * @throws IOException
	 */
	protected void flushAppXmlFile(XMLOutputFactory xmlOutFactory)
			throws IOException {
		flushDynamicTemplateWriter(this.appXmlTemplateWriter, xmlOutFactory);
	}

	/**
	 * Write remaining part of ./xl/_rels/workbook.xml.rels file
	 * 
	 * @param xmlOutFactory
	 * @throws IOException
	 */
	protected void flushWorkbookRelsXmlFile(XMLOutputFactory xmlOutFactory)
			throws IOException {
		flushDynamicTemplateWriter(this.workbookRelsXmlTemplateWriter,
				xmlOutFactory);
	}

	/**
	 * Write remaining part of ./xl/workbook.xml file
	 * 
	 * @param xmlOutFactory
	 * @throws IOException
	 */
	protected void flushWorkbookXmlFile(XMLOutputFactory xmlOutFactory)
			throws IOException {
		flushDynamicTemplateWriter(this.workbookXmlTemplateWriter,
				xmlOutFactory);
	}

	protected void flushDynamicTemplateWriter(
			TokenEnumerableTemplateWriter templateWriter,
			XMLOutputFactory xmlOutFactory) throws IOException {
		String token = templateWriter.getToken();
		while (token != null) {
			XMLStreamWriter xmlWriter = null;
			try {
				if (token.equals("worksheets.count")) {
					templateWriter.getOutputWriter().write(
							String.valueOf(this.worksheetList.size()));
				} else if (token.equals("worksheets.vector")) {

					xmlWriter = xmlOutFactory
							.createXMLStreamWriter(appXmlTemplateWriter
									.getOutputWriter());
					xmlWriter.writeStartElement("vt:vector");
					xmlWriter.writeAttribute("size",
							String.valueOf(this.worksheetList.size()));
					xmlWriter.writeAttribute("baseType", "lpstr");

					for (int index = 0; index < this.worksheetList.size(); ++index) {
						xmlWriter.writeStartElement("vt:lpstr");
						xmlWriter.writeCharacters(this.worksheetList.get(index)
								.getName());
						xmlWriter.writeEndElement();
					}

					xmlWriter.writeEndElement();

				} else if (token.equals("worksheet.relationships")) {
					xmlWriter = xmlOutFactory
							.createXMLStreamWriter(templateWriter
									.getOutputWriter());
					for (int index = 0; index < worksheetList.size(); ++index) {
						WorksheetImpl worksheet = worksheetList.get(index);
						xmlWriter.writeStartElement("Relationship");
						xmlWriter.writeAttribute("Id",
								"rId" + worksheet.getResourceId());
						xmlWriter
								.writeAttribute(
										"Type",
										OpenXMLNamespace.NS_OPENXML_OFFICE_DOC_2006_REL_WORKSHEET);
						xmlWriter.writeAttribute("Target", "worksheets/sheet"
								+ worksheet.getWorksheetNumber() + ".xml");
						xmlWriter.writeEndElement();
					}
				} else if (token.equals("workbook.sheets")) {
					xmlWriter = xmlOutFactory
							.createXMLStreamWriter(templateWriter
									.getOutputWriter());
					for (int index = 0; index < worksheetList.size(); ++index) {
						WorksheetImpl worksheet = worksheetList.get(index);
						xmlWriter.writeStartElement("sheet");
						xmlWriter.writeAttribute("name",
								"Sheet" + worksheet.getWorksheetNumber());
						xmlWriter.writeAttribute("sheetId",
								String.valueOf(worksheet.getWorksheetNumber()));
						xmlWriter.writeAttribute("r:id",
								"rId" + worksheet.getResourceId());
						xmlWriter.writeEndElement();
					}
				} else {
					// unknown. Invoke a protected method which can be
					// overridden by derived classes
					// to add new template variables and interpret them
					// accordingly.
					expandToken(token, templateWriter);
				}
			} catch (XMLStreamException ex) {
				throw new StaxcelException("Error when replacing " + token
						+ " from template file", ex);
			} finally {
				if (xmlWriter != null) {
					try {
						xmlWriter.flush();
						xmlWriter.close();
					} catch (XMLStreamException ex2) {
						logger.error("Error when closing xmlstream", ex2);
					}
				}
			}
			token = templateWriter.nextElement();
		}
		templateWriter.getOutputWriter().flush();
		templateWriter.getOutputWriter().close();
	}

	/**
	 * This method can be overridden by derived class to add new macro variables
	 * to template and interpet them here accordingly.
	 * 
	 * @param token
	 *            token to be expanded by writing content using template writer
	 * @param templateWriter
	 *            writer pointing to xml file to be updated with token expanded
	 *            value.
	 * @throws IOException
	 *             if any exception occurs
	 * 
	 */
	protected void expandToken(String token,
			TokenEnumerableTemplateWriter templateWriter) throws IOException {
		// does nothing. Derived class can override.
	}

	/**
	 * Write all shared strings to ./xl/SharedStrings.xml file
	 * 
	 * @throws IOException
	 */
	protected void addSharedStringsFile() throws IOException {
		try {
			BufferedWriter writer = createWriter(OpenXMLFormatConstants.SHARED_STRING_FILE_NAME);
			XMLStreamWriter xmlWriter = XMLOutputFactory.newInstance()
					.createXMLStreamWriter(writer);
			xmlWriter.writeStartDocument("UTF-8", "1.0");
			xmlWriter.writeStartElement("sst");
			xmlWriter.writeAttribute("xmlns",
					OpenXMLNamespace.NS_OPENXML_SPREADSHEETML_2006_MAIN);
			xmlWriter.writeAttribute("count",
					String.valueOf(this.sharedStrings.size()));
			xmlWriter.writeAttribute("uniqueCount",
					String.valueOf(this.sharedStrings.size()));

			for (Entry<String, Integer> entry : this.sharedStrings.entrySet()) {
				xmlWriter.writeStartElement("si");
				xmlWriter.writeStartElement("t");
				xmlWriter.writeCharacters(entry.getKey());
				xmlWriter.writeEndElement();
				xmlWriter.writeEndElement();
			}
			xmlWriter.writeEndElement();
			xmlWriter.writeEndDocument();
			xmlWriter.close();
			writer.flush();
			writer.close();
		} catch (XMLStreamException ex) {
			logger.error("XML error when creating SharedStrings.xml file", ex);
			throw new StaxcelException(
					"XML error when creating SharedStrings.xml file", ex);
		}
	}

	/**
	 * Zips all content, creates xlsx file and deletes intermediate files
	 */
	protected void createXlsxFile() throws IOException {
		ArrayList<File> filesList = new ArrayList<File>();
		listAllFilesRecursively(getWorkingDir(), filesList);
		File xlsxFolder = getFolderForGeneratedSpreadsheet();

		// FIXME : REMOVE ME after testing.
		File zipFile = new File(xlsxFolder, getSpreadSheetName() + ".zip");
		zipContent(zipFile, getWorkingDir(), filesList);

		File xlsxFile = new File(xlsxFolder, getSpreadSheetName() + ".xlsx");
		zipContent(xlsxFile, getWorkingDir(), filesList);

		if (this.shouldDeleteIntermediateFiles) {
			// delete all intermediate files.
			getWorkingDir().delete();
		}
	}

	/**
	 * Returns block size (in bytes) to be used to copy data from .xml files to
	 * create zip file. By default this returns 4096 if not set explicitly.
	 * 
	 * @return block size.
	 */
	public int getDefaultBlockSize() {
		return defaultBlockSize;
	}

	public void setDefaultBlockSize(int defaultBlockSize) {
		this.defaultBlockSize = defaultBlockSize;
	}

	/**
	 * Zip all source files into target zip file
	 * 
	 * @param targetFile
	 * @param baseFolder
	 * @param sourceFilesList
	 * @throws IOException
	 */
	private void zipContent(File targetFile, File baseFolder,
			ArrayList<File> sourceFilesList) throws IOException {
		BufferedOutputStream fOut = new BufferedOutputStream(
				new FileOutputStream(targetFile));
		ZipOutputStream zipOut = new ZipOutputStream(fOut);
		String baseFolderPath = baseFolder.getAbsolutePath();
		int blockSize = getDefaultBlockSize();
		byte[] byteArray = new byte[blockSize];

		for (int index = 0; index < sourceFilesList.size(); ++index) {
			File entryFile = sourceFilesList.get(index);
			String name = entryFile.getAbsolutePath().substring(
					baseFolderPath.length());
			System.out.println("name is : " + name);
			ZipEntry zipEntry = new ZipEntry(name);
			zipOut.putNextEntry(zipEntry);
			long inputFileSize = entryFile.length();
			InputStream fIn = new BufferedInputStream(new FileInputStream(
					entryFile));

			int length = 0;
			while (inputFileSize > 0) {
				length = inputFileSize >= blockSize ? blockSize
						: (int) inputFileSize;
				inputFileSize = inputFileSize - length;
				fIn.read(byteArray, 0, length);
				zipOut.write(byteArray, 0, length);
			}
			fIn.close();
		}

		zipOut.flush();
		zipOut.close();
		fOut.flush();
		fOut.close();
	}

	protected File getFolderForGeneratedSpreadsheet() {
		return getWorkingDir().getParentFile();
	}

	/**
	 * Does depth-first travel of folder to get list of all files in the folder
	 * and its sub-folders recursively
	 * 
	 * @param folder
	 *            folder whose content need to be scanned for files only
	 * @param filesList
	 *            list to which all files found (at any level) are appended
	 *            using depth-first traversal of file system
	 */
	private void listAllFilesRecursively(File folder, ArrayList<File> filesList) {
		File[] content = folder.listFiles();
		for (int index = 0; index < content.length; ++index) {
			if (content[index].isDirectory()) {
				// make recursive call (depth first traversal)
				listAllFilesRecursively(content[index], filesList);
			} else {
				filesList.add(content[index]);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.staxcel.SpreadSheet#createWorksheet(java.lang.String)
	 */
	@Override
	public Worksheet createWorksheet(String worksheetName, long maxColumns)
			throws StaxcelRuntimeException {
		int worksheetNumber = worksheetList.size() + 1;
		WorksheetImpl worksheet = new WorksheetImpl(this, worksheetName,
				worksheetNumber, maxColumns);
		this.worksheetList.add(worksheet);
		String resourceName = "xl" + File.separator + "worksheets"
				+ File.separator + "sheet" + String.valueOf(worksheetNumber)
				+ ".xml";

		int worksheetResourceId = getNextResourceId(resourceName);
		worksheet.setResourceId(worksheetResourceId);
		return worksheet;
	}

	public File getWorkingDir() {
		return workingDir;
	}

	/**
	 * Adds given string to commonnly used string literals in Excel file.
	 * 
	 * @param sharedString
	 */
	public void addSharedString(String sharedString) {
		synchronized (sharedStrings) {
			int count = this.sharedStrings.size();
			this.sharedStrings.put(sharedString, ++count);
		}
	}

	/**
	 * Add multiple common strings.
	 * 
	 * @param commonStrings
	 */
	public void addSharedStrings(Iterable<String> commonStrings) {
		synchronized (sharedStrings) {
			int count = this.sharedStrings.size();
			for (String commonString : commonStrings) {
				this.sharedStrings.put(commonString, ++count);
			}
		}
	}
}
