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

package com.liferay.vulcan.architect.sample.liferay.portal.internal.rating;

import com.liferay.ratings.kernel.model.RatingsEntry;
import com.liferay.vulcan.architect.sample.liferay.portal.rating.AggregateRating;
import com.liferay.vulcan.architect.sample.liferay.portal.resource.identifier.AggregateRatingIdentifier;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author Alejandro Hernández
 */
public class AggregateRatingImpl implements AggregateRating {

	public AggregateRatingImpl(
		AggregateRatingIdentifier aggregateRatingIdentifier,
		List<RatingsEntry> ratingsEntries) {

		_aggregateRatingIdentifier = aggregateRatingIdentifier;

		_ratingCount = ratingsEntries.size();

		Stream<RatingsEntry> stream = ratingsEntries.stream();

		_ratingValue = stream.mapToDouble(
			RatingsEntry::getScore
		).average(
		).orElse(
			0
		);
	}

	@Override
	public AggregateRatingIdentifier getAggregateRatingIdentifier() {
		return _aggregateRatingIdentifier;
	}

	@Override
	public Integer getRatingCount() {
		return _ratingCount;
	}

	@Override
	public Double getRatingValue() {
		return _ratingValue;
	}

	private final AggregateRatingIdentifier _aggregateRatingIdentifier;
	private final Integer _ratingCount;
	private final Double _ratingValue;

}