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

import java.io.IOException;

import net.jcip.annotations.ThreadSafe;

import org.staxcel.StaxcelRuntimeException;
import org.staxcel.Worksheet;
import org.staxcel.WorksheetDataWriter;

/**
 * @author Gopinath.MR
 * 
 */
@ThreadSafe
public class WorksheetImpl implements Worksheet {

	protected String name;

	protected SpreadSheetImpl spreadSheet;

	protected int worksheetWriterCounter = 0;

	protected long maxColumns;

	private int resourceId;

	private int worksheetNumber;

	/**
	 * 
	 * @param worksheetName
	 */
	public WorksheetImpl(SpreadSheetImpl spreadSheet, String worksheetName,
			int worksheetNumber, long maxColumns) {
		this.spreadSheet = spreadSheet;
		this.name = worksheetName;
		this.worksheetNumber = worksheetNumber;
		this.maxColumns = maxColumns;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.staxcel.Worksheet#getWriter()
	 */
	@Override
	public WorksheetDataWriter getWriter() throws IOException {
		return new WorksheetDataWriterImpl(++worksheetWriterCounter,
				this.spreadSheet.getWorkingDir(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.staxcel.Worksheet#close()
	 */
	@Override
	public boolean close() throws StaxcelRuntimeException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getResourceId() {
		return this.resourceId;
	}

	public int getWorksheetNumber() {
		return worksheetNumber;
	}

	public void setWorksheetNumber(int worksheetNumber) {
		this.worksheetNumber = worksheetNumber;
	}
}
