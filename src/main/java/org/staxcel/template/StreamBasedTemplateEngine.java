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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;

/**
 * Simple Stream based template engine which does find/replace before writing
 * data to writer.
 * 
 * @author gopinath.mr
 * 
 */
public class StreamBasedTemplateEngine {

	/**
	 * 
	 */
	public StreamBasedTemplateEngine() {
		// TODO Auto-generated constructor stub
	}

	public static void applyTemplate(BufferedReader templateReader,
			Writer targetWriter, HashMap<String, String> context)
			throws IOException {
		// TODO-Future : need to use regex later
		String line = null;
		while ((line = templateReader.readLine()) != null) {
			if (context != null) {
				for (String key : context.keySet()) {
					String value = context.get(key);
					line = line.replaceAll("\\{" + key + "\\}", value);
				}
			}
			targetWriter.write(line);
			targetWriter.write("\n");
		}

	}

}
