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

package com.liferay.vulcan.sample.liferay.portal.internal.site;

import com.liferay.portal.kernel.model.Group;
import com.liferay.vulcan.resource.identifier.LongIdentifier;
import com.liferay.vulcan.sample.liferay.portal.site.Site;

/**
 * @author Victor Oliveira
 */
public class SiteImpl implements Site {

	public SiteImpl(Group group) {
		_group = group;
	}

	@Override
	public String getDescription() {
		return _group.getDescription();
	}

	@Override
	public String getName() {
		return _group.getName();
	}

	@Override
	public LongIdentifier getSiteLongIdentifier() {
		return _group::getGroupId;
	}

	private final Group _group;

}