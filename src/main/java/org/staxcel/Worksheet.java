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

import net.jcip.annotations.ThreadSafe;

/**
 * Represents logical unit of an excel worksheet. An instance of class
 * implementing this interface should be thread-safe so that client applications
 * can invoke its methods from multiple threads.
 * 
 * @author Gopinath.MR
 * 
 */
@ThreadSafe
public interface Worksheet extends SpreadSheetResource {

	/**
	 * Returns worksheet name.
	 * 
	 * @return worksheet name
	 */
	String getName();

	/**
	 * Returns reference to writer which can be used to write data to worksheet.
	 * 
	 * @return reference to writer which can be used to write rows to this
	 *         worksheet .
	 * @throws StaxcelRuntimeException
	 *             if worksheet is already closed.
	 */
	WorksheetDataWriter getWriter() throws IOException;

	/**
	 * Closes the worksheet.
	 * 
	 * @return <code> true</code> if success else returns <code>false</code>
	 * @throws StaxcelRuntimeException
	 *             if underneath WorksheetDataWriter instances are still open.
	 * 
	 * @see WorksheetDataWriter
	 */
	boolean close() throws StaxcelRuntimeException;
}