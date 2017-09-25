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

package com.liferay.vulcan.architect.jaxrs.json.internal.filter;

import com.liferay.vulcan.architect.error.VulcanDeveloperError.MustHaveExceptionConverter;
import com.liferay.vulcan.architect.jaxrs.json.internal.writer.WriterHelper;
import com.liferay.vulcan.architect.logger.VulcanLogger;
import com.liferay.vulcan.architect.message.json.ErrorMessageMapper;
import com.liferay.vulcan.architect.result.APIError;
import com.liferay.vulcan.architect.result.Try;
import com.liferay.vulcan.architect.wiring.osgi.manager.ErrorMessageMapperManager;
import com.liferay.vulcan.architect.wiring.osgi.manager.ExceptionConverterManager;

import java.io.IOException;

import java.util.Optional;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * This filter is responsible of filtering {@link Try.Failure} entities,
 * converting them to its corresponding {@link APIError} and writing the error
 * to the response.
 *
 * @author Alejandro Hernández
 * @review
 */
@Component(
	immediate = true, property = "liferay.vulcan.container.response.filter=true"
)
public class FailureFilter implements ContainerResponseFilter {

	@Override
	public void filter(
			ContainerRequestContext containerRequestContext,
			ContainerResponseContext containerResponseContext)
		throws IOException {

		Object entity = containerResponseContext.getEntity();

		if (entity instanceof Try.Failure) {
			Try.Failure failure = (Try.Failure)entity;

			Exception exception = failure.getException();

			Optional<APIError> optional = _exceptionConverterManager.convert(
				exception);

			APIError apiError = optional.orElseThrow(
				() -> new MustHaveExceptionConverter(exception.getClass()));

			_vulcanLogger.error(apiError);

			ErrorMessageMapper errorMessageMapper =
				_errorMessageMapperManager.getErrorMessageMapper(
					apiError, _httpHeaders);

			String result = WriterHelper.writeError(
				errorMessageMapper, apiError, _httpHeaders);

			MultivaluedMap<String, Object> headers =
				containerResponseContext.getHeaders();

			headers.remove("Content-Type");
			headers.add("Content-Type", errorMessageMapper.getMediaType());

			containerResponseContext.setEntity(result);
			containerResponseContext.setStatus(apiError.getStatusCode());
		}
	}

	@Reference
	private ErrorMessageMapperManager _errorMessageMapperManager;

	@Reference
	private ExceptionConverterManager _exceptionConverterManager;

	@Context
	private HttpHeaders _httpHeaders;

	@Reference
	private VulcanLogger _vulcanLogger;

}