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

package com.liferay.vulcan.architect.wiring.osgi.manager;

import static org.osgi.service.component.annotations.ReferenceCardinality.MULTIPLE;
import static org.osgi.service.component.annotations.ReferencePolicy.DYNAMIC;
import static org.osgi.service.component.annotations.ReferencePolicyOption.GREEDY;

import com.liferay.vulcan.architect.converter.ExceptionConverter;
import com.liferay.vulcan.architect.result.APIError;

import java.util.Optional;

import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides methods to convert exceptions to generic {@link APIError}
 * representations.
 *
 * @author Alejandro Hernández
 * @review
 */
@Component(immediate = true, service = ExceptionConverterManager.class)
public class ExceptionConverterManager extends BaseManager<ExceptionConverter> {

	/**
	 * Converts an exception to its generic {@code APIError} representation. If
	 * no {@link ExceptionConverter} can be found for the actual exception
	 * class, it tries to use the superclass {@link ExceptionConverter}.
	 *
	 * <p>
	 * If non {@link ExceptionConverter} can be found a generic converter
	 * ({@code ExceptionConverter<Exception>} will be used.
	 * </p>
	 *
	 * @param  exception the exception to be converted.
	 * @return the corresponding error, if a valid {@link ExceptionConverter} is
	 *         present; <code>Optional#empty()</code> otherwise.
	 * @review
	 */
	public <T extends Exception> Optional<APIError> convert(T exception) {
		return _convert(exception, (Class<T>)exception.getClass());
	}

	@Reference(cardinality = MULTIPLE, policy = DYNAMIC, policyOption = GREEDY)
	protected void setServiceReference(
		ServiceReference<ExceptionConverter> serviceReference) {

		addService(serviceReference, ExceptionConverter.class);
	}

	@SuppressWarnings("unused")
	protected void unsetServiceReference(
		ServiceReference<ExceptionConverter> serviceReference) {

		removeService(serviceReference, ExceptionConverter.class);
	}

	private <T extends Exception> Optional<APIError> _convert(
		T exception, Class<T> exceptionClass) {

		Optional<ExceptionConverter> optional = getServiceOptional(
			exceptionClass);

		if (!optional.isPresent()) {
			Optional<Class<?>> classOptional = Optional.ofNullable(
				exceptionClass.getSuperclass());

			return classOptional.filter(
				Exception.class::isAssignableFrom
			).flatMap(
				clazz -> _convert(exception, (Class<T>)clazz)
			);
		}

		return optional.map(
			exceptionConverter -> (ExceptionConverter<T>)exceptionConverter
		).map(
			exceptionConverter -> exceptionConverter.convert(exception)
		);
	}

}