/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.vulcan.architect.error.internal.converter;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

import com.liferay.vulcan.architect.converter.ExceptionConverter;
import com.liferay.vulcan.architect.result.APIError;

import org.osgi.service.component.annotations.Component;

/**
 * Converts any exception to its {@link APIError} representation.
 *
 * @author Alejandro Hernández
 * @review
 */
@Component(immediate = true)
public class GenericExceptionConverter
	implements ExceptionConverter<Exception> {

	@Override
	public APIError convert(Exception exception) {
		return new APIErrorImpl(
			exception, "General server error", "server-error",
			INTERNAL_SERVER_ERROR.getStatusCode());
	}

}