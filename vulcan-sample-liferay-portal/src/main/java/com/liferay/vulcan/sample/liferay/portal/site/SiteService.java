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

package com.liferay.vulcan.sample.liferay.portal.site;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.vulcan.pagination.PageItems;
import com.liferay.vulcan.pagination.Pagination;

import java.util.Locale;
import java.util.Map;

/**
 * @author Victor Oliveira
 */
public interface SiteService {

	public Site addSite(
			long userId, long parentGroupId, java.lang.String className,
			long classPK, long liveGroupId,
			Map<Locale, java.lang.String> nameMap,
			Map<Locale, java.lang.String> descriptionMap, int type,
			boolean manualMembership, int membershipRestriction,
			java.lang.String friendlyURL, boolean active,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException;

	public void deleteSite(long siteId) throws PortalException;

	public PageItems<Site> getPageItems(Pagination pagination, long companyId);

	public Site getSite(long id) throws PortalException;

	public Site updateSite(
			long siteId, long parentGroupId,
			Map<Locale, java.lang.String> nameMap,
			Map<Locale, java.lang.String> descriptionMap, int type,
			boolean manualMembership, int membershipRestriction,
			java.lang.String friendlyURL, boolean inheritContent,
			boolean active,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException;

}