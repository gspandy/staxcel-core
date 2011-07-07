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

import java.io.IOException;

import net.jcip.annotations.NotThreadSafe;

/**
 * This class instance is used to write data to an excel file. A particular
 * writer can write only fragment of an excel worksheet.
 * 
 * @author Gopinath.MR
 * 
 */
@NotThreadSafe
public interface WorksheetDataWriter {

	/**
	 * Returns reference to fragment of worksheet written using this writer.
	 * 
	 * @return fragment of worksheet created by this writer.
	 */
	WorksheetFragment getFragment();

	/**
	 * Writes all data in single row.
	 * 
	 * @param columnValues
	 *            array of column values to be written.
	 * @return returns <code>true</code> if success else returns
	 *         <code>false</code>.
	 * @throws StaxcelRuntimeException
	 *             if any exception occurs
	 */
	boolean write(Object[] columnValues) throws IOException;

	/**
	 * Closes writer.
	 * 
	 * @return returns <code>true</code> if success else returns
	 *         <code>false</code>
	 * @throws StaxcelRuntimeException
	 *             if closing fails.
	 */
	boolean close() throws IOException;
}