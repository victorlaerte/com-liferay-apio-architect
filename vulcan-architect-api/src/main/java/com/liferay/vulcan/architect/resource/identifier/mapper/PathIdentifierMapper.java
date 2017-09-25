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

package com.liferay.vulcan.architect.resource.identifier.mapper;

import com.liferay.vulcan.architect.resource.identifier.Identifier;
import com.liferay.vulcan.architect.uri.Path;

/**
 * Instances of this interface will be used to convert from a {@link Path} to
 * its correspondent {@link Identifier} and vice versa.
 *
 * <p>
 * {@link PathIdentifierMapper}s should only be created for {@link Identifier}s
 * used as a single model's identifier.
 * </p>
 *
 * <p>
 * The class of the identifier can then be provided as a parameter in {@link
 * com.liferay.vulcan.architect.resource.builder.RoutesBuilder}{@code
 * #addCollectionPageItemGetter} methods.
 * </p>
 *
 * @author Alejandro Hernández
 */
public interface PathIdentifierMapper<T extends Identifier> {

	/**
	 * Converts a {@code Path} to its correspondent {@code Identifier}
	 *
	 * @param  path the path of the resource.
	 * @return the correspondent {@code Identifier}.
	 */
	public T map(Path path);

	/**
	 * Converts an {@code Identifier} to its correspondent {@code Path}
	 *
	 * @param  t the identifier.
	 * @param  modelClass the class of the model identified by the identifier.
	 * @return the correspondent {@code Path}.
	 */
	public <U> Path map(T t, Class<U> modelClass);

}