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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.ResourceBundle;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.staxcel.internal.SpreadSheetConfigImpl;
import org.staxcel.template.StreamBasedTemplateEngine;
import org.staxcel.template.TokenEnumerableTemplateWriter;

/**
 * @author gopinath.mr
 * 
 */
public class SimpleSpreadSheetWriteTest {

	private SpreadSheetConfig spreadsheetConfig = null;
	private String outputDir;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		ResourceBundle resourceBundle = ResourceBundle
				.getBundle("staxcel-config");
		spreadsheetConfig = new SpreadSheetConfigImpl(resourceBundle);
		outputDir = System.getProperty("output.dir");
		if (outputDir == null) {
			outputDir = "c:/temp";
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSingleThreadWrite() throws Exception {

		int totalColumns = 5;
		int totalRows = 20;

		SpreadSheet spreadSheet = StaxcelFactory.getInstance()
				.createSpreadSheet("test", this.spreadsheetConfig);
		Worksheet worksheet = spreadSheet.createWorksheet("worksheet1",
				totalColumns);
		WorksheetDataWriter writer = worksheet.getWriter();

		String[] columnNames = new String[totalColumns];
		for (int colIndex = 0; colIndex < totalColumns; ++colIndex) {
			columnNames[colIndex] = "column-" + (colIndex + 1);
		}
		writer.write(columnNames);
		String[] columnValues = new String[totalColumns];

		for (int rowIndex = 0; rowIndex < totalRows; ++rowIndex) {

			for (int colIndex = 0; colIndex < totalColumns; ++colIndex) {
				columnValues[colIndex] = "columnValue-" + (rowIndex + 1) + "-"
						+ (colIndex + 1);
			}
			writer.write(columnValues);
		}
		writer.close();
		spreadSheet.close();
		Assert.assertEquals(1, 1);
		// fail("Not yet implemented");
	}

	@Test
	public void testMe() throws Exception {
		HashMap<String, String> context = new HashMap<String, String>();
		context.put("name", "gopi");
		BufferedReader templateReader = new BufferedReader(new FileReader(
				"c:/work/test/excel/xl/styles.xml"));
		Writer targetWriter = new OutputStreamWriter(System.out);
		try {
			StreamBasedTemplateEngine.applyTemplate(templateReader,
					targetWriter, context);
		} finally {
			targetWriter.flush();
			templateReader.close();
		}
	}

	@Test
	public void testStreamBasedDynamicTemplateWriter() throws Exception {
		BufferedReader templateReader = new BufferedReader(new FileReader(
				"s:/template/docProps/app.xml"));
		Writer outputWriter = new FileWriter(this.outputDir + File.separator
				+ "templateExpanded-app.xml");
		TokenEnumerableTemplateWriter templateWriter = new TokenEnumerableTemplateWriter(
				templateReader, outputWriter);
		String token = "";
		while ((token = templateWriter.nextElement()) != null) {
			if (token.equals("worksheets.count")) {
				outputWriter.write(Integer.toString(3));
			} else if (token.equals("worksheets.vector")) {
				outputWriter.write("worksheet1\n");
				outputWriter.write("worksheet2\n");
				outputWriter.write("worksheet3\n");
			}
			System.out.println("token : " + token);
		}
		templateWriter.getOutputWriter().flush();
		templateWriter.getOutputWriter().close();
		Assert.assertEquals(1, 1);
	}

}
