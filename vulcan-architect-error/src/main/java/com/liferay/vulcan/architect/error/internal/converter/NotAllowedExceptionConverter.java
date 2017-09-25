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

import static javax.ws.rs.core.Response.Status.METHOD_NOT_ALLOWED;

import com.liferay.vulcan.architect.converter.ExceptionConverter;
import com.liferay.vulcan.architect.result.APIError;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;

/**
 * Converts a {@link NotAllowedException} into its {@link APIError}
 * representation.
 *
 * @author Alejandro Hernández
 * @review
 */
@Component(immediate = true)
public class NotAllowedExceptionConverter
	extends WebApplicationExceptionConverter
	implements ExceptionConverter<NotAllowedException> {

	@Override
	public APIError convert(NotAllowedException exception) {
		return super.convert(exception);
	}

	@Override
	protected Response.StatusType getStatusType() {
		return METHOD_NOT_ALLOWED;
	}

	@Override
	protected String getTitle() {
		return "HTTP method not supported";
	}

	@Override
	protected String getType() {
		return "not-allowed";
	}

}