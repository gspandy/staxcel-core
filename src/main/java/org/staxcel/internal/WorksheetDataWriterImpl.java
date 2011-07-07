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

import java.io.File;
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
public class WorksheetDataWriterImpl implements WorksheetDataWriter {

	protected WorksheetFragment fragment;

	protected Writer writer;

	/**
	 * Constructor.
	 * 
	 * @param writerNumber
	 * @param parentFolder
	 * @param worksheet
	 */
	public WorksheetDataWriterImpl(int writerNumber, File parentFolder,
			WorksheetImpl worksheet) throws IOException {
		this.fragment = new WorksheetFragmentImpl(this, writerNumber,
				parentFolder, worksheet);
	}

	public Writer getWriter() {
		return writer;
	}

	public void setWriter(Writer writer) {
		this.writer = writer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.staxcel.WorksheetDataWriter#getFragment()
	 */
	@Override
	public WorksheetFragment getFragment() {
		return this.fragment;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.staxcel.WorksheetDataWriter#write(java.lang.Object[])
	 */
	@Override
	public boolean write(Object[] columnValues) throws IOException {
		System.out.println("todo : need to write here..");
		this.writer.write("writing..");
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.staxcel.WorksheetDataWriter#close()
	 */
	@Override
	public boolean close() throws IOException {
		this.writer.flush();
		this.writer.close();
		return false;
	}

}
