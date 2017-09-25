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

package com.liferay.vulcan.architect.pagination;

/**
 * Provides a wrapper over a model for skipping problems related to the Java
 * generics system.
 *
 * @author Alejandro Hernández
 * @review
 */
public class SingleModel<T> {

	public SingleModel(T model, Class<T> modelClass) {
		_model = model;
		_modelClass = modelClass;
	}

	/**
	 * Returns the model.
	 *
	 * @return the model.
	 * @review
	 */
	public T getModel() {
		return _model;
	}

	/**
	 * Returns the model class.
	 *
	 * @return the model class.
	 * @review
	 */
	public Class<T> getModelClass() {
		return _modelClass;
	}

	private final T _model;
	private final Class<T> _modelClass;

}