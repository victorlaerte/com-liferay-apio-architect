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

package com.liferay.vulcan.architect.function;

import java.util.Objects;
import java.util.function.Function;

/**
 * Represents a function that accepts six arguments and produces a result. This
 * is the six-arity specialization of {@link Function}.
 *
 * <p>This is a <a
 * href="http://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html">functional
 * interface</a> whose
 * functional method is {@link
 * #apply(Object, Object, Object, Object, Object, Object)}.
 *
 * @author Alejandro Hernández
 * @author Jorge Ferrer
 * @see    Function
 * @review
 */
@FunctionalInterface
public interface HexaFunction<A, B, C, D, E, F, R> {

	/**
	 * Returns a composed function that first applies this function to its
	 * input, and then applies the {@code afterFunction} function to the result.
	 * If evaluation of either function throws an exception, it is relayed to
	 * the caller of the composed function.
	 *
	 * @param  afterFunction the function to apply after this function is
	 *         applied
	 * @return a composed function that first applies this function and then
	 *         applies the {@code after} function
	 * @review
	 */
	public default <V> HexaFunction<A, B, C, D, E, F, V> andThen(
		Function<? super R, ? extends V> afterFunction) {

		Objects.requireNonNull(afterFunction);

		return (A a, B b, C c, D d, E e, F f) -> afterFunction.apply(
			apply(a, b, c, d, e, f));
	}

	/**
	 * Applies this function to the given arguments.
	 *
	 * @param  a the first function argument
	 * @param  b the second function argument
	 * @param  c the third function argument
	 * @param  d the fourth function argument
	 * @param  e the fifth function argument
	 * @param  f the sixth function argument
	 * @return the function result
	 * @review
	 */
	public R apply(A a, B b, C c, D d, E e, F f);

}