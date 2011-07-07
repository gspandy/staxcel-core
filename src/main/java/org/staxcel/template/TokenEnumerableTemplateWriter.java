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
import java.util.Enumeration;

import org.staxcel.StaxcelRuntimeException;

/**
 * This class provides pull based templating approach where content from a
 * template file can be written to output and whereever there is a template
 * part, it allows application to replace it with some dynamic content. This
 * class ensures we don't store huge template data in-memory, but still provides
 * capability for clients to add dynamic content. Client application is
 * responsible to pull tokens till <code>nextElement</code> returns
 * <code>null</code>.
 * 
 * @see #nextElement()
 * 
 * @author Gopinath.MR
 * 
 */
public class TokenEnumerableTemplateWriter extends Writer implements
		Enumeration<String> {

	protected BufferedReader templateReader;

	protected Writer outputWriter;

	private String token = "";

	/**
	 * 
	 * @param templateReader
	 * @param outputWriter
	 */
	public TokenEnumerableTemplateWriter(BufferedReader templateReader,
			Writer outputWriter) {
		this.templateReader = templateReader;
		this.outputWriter = outputWriter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.Writer#close()
	 */
	@Override
	public void close() throws IOException {
		outputWriter.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.Writer#flush()
	 */
	@Override
	public void flush() throws IOException {
		outputWriter.flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.Writer#write(char[], int, int)
	 */
	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		outputWriter.write(cbuf, off, len);
	}

	@Override
	public boolean hasMoreElements() {
		if (token.isEmpty()) {
			// not yet read from template file. So apply template
			nextElement();
		}
		return (token != null);
	}

	@Override
	public String nextElement() {
		token = null;
		try {
			while (true) {
				String line = templateReader.readLine();
				if (line == null) {
					templateReader.close();
					break;
				}
				String trimmedLine = line.trim();
				if (trimmedLine.startsWith("{") && trimmedLine.endsWith("}")) {
					token = trimmedLine.substring(1, trimmedLine.length() - 1);
					break;
				} else {
					outputWriter.write(line);
					outputWriter.write("\n");
				}
			}
			return token;
		} catch (IOException ex) {
			throw new StaxcelRuntimeException(ex);
		}
	}

	public Writer getOutputWriter() {
		return outputWriter;
	}

	public String getToken() {
		return token;
	}
}
