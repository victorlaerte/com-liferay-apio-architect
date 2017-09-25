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

package com.liferay.vulcan.architect.sample.liferay.portal.rating;

import aQute.bnd.annotation.ProviderType;

import com.liferay.vulcan.architect.sample.liferay.portal.resource.identifier.AggregateRatingIdentifier;

/**
 * Instances of this service can be used to get the related {@link
 * AggregateRating} for a certain combination of className/classPK.
 *
 * @author Alejandro Hernández
 * @review
 */
@ProviderType
public interface AggregateRatingService {

	/**
	 * Returns the {@link AggregateRating} for a className/classPK combination.
	 *
	 * @param  aggregateRatingIdentifier the rating identifier.
	 * @return the aggregate rating.
	 * @review
	 */
	public AggregateRating getAggregateRating(
		AggregateRatingIdentifier aggregateRatingIdentifier);

}