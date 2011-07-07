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

/**
 * Base Exception class for all StaXcel exceptions.
 * 
 * @author Gopinath.MR
 * 
 */
public class StaxcelException extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 552673359203658873L;

	/**
	 * 
	 */
	public StaxcelException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * {@inheritDoc}
	 */
	public StaxcelException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * {@inheritDoc}
	 */
	public StaxcelException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * {@inheritDoc}
	 */
	public StaxcelException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	// @Override
	// public StackTraceElement[] getStackTrace() {
	// // TODO Auto-generated method stub
	// StackTraceElement[] stackTrace = super.getStackTrace();
	// this.getCause().getStackTrace();
	// }
	//
}
