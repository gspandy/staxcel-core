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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import net.jcip.annotations.NotThreadSafe;

import org.staxcel.WorksheetDataWriter;
import org.staxcel.WorksheetFragment;

/**
 * @author Gopinath.MR
 * 
 */
@NotThreadSafe
public class WorksheetFragmentImpl implements WorksheetFragment {

	protected WorksheetDataWriterImpl worksheetDataWriter;

	protected int writerNumber;

	protected File parentFolder;

	protected WorksheetImpl worksheet;

	private static final String WORKSHEET_FRAGMENT_FILE_PREFIX = ".fragment.";

	/**
	 * 
	 * @param worksheetDataWriter
	 * @param writerNumber
	 * @param parentFolder
	 * @param worksheet
	 * @throws IOException
	 */
	public WorksheetFragmentImpl(WorksheetDataWriterImpl worksheetDataWriter,
			int writerNumber, File parentFolder, WorksheetImpl worksheet)
			throws IOException {
		this.worksheetDataWriter = worksheetDataWriter;
		this.writerNumber = writerNumber;
		this.parentFolder = parentFolder;
		this.worksheet = worksheet;
		createWriter();
	}

	protected void createWriter() throws IOException {
		File fragmentFile = new File(this.parentFolder,
				this.worksheet.getName() + WORKSHEET_FRAGMENT_FILE_PREFIX
						+ Integer.toString(writerNumber));
		Writer writer = new BufferedWriter(new FileWriter(fragmentFile));
		this.worksheetDataWriter.setWriter(writer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.staxcel.WorksheetFragment#getWriter()
	 */
	@Override
	public WorksheetDataWriter getWriter() {
		return this.worksheetDataWriter;
	}
}
