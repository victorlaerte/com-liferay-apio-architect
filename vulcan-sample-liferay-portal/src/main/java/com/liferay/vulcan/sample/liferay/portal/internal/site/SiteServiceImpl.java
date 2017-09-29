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

import static com.liferay.portal.kernel.model.GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION;
import static com.liferay.portal.kernel.model.GroupConstants.TYPE_SITE_OPEN;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.vulcan.pagination.PageItems;
import com.liferay.vulcan.pagination.Pagination;
import com.liferay.vulcan.sample.liferay.portal.site.Site;
import com.liferay.vulcan.sample.liferay.portal.site.SiteService;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Victor Oliveira
 */
@Component(immediate = true)
public class SiteServiceImpl implements SiteService {

	@Override
	public Site addSite(
			long userId, long parentGroupId, String className, long classPK,
			long liveGroupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, int type,
			boolean manualMembership, int membershipRestriction,
			String friendlyURL, boolean active, ServiceContext serviceContext)
		throws PortalException {

		Group group = _groupLocalService.addGroup(
			userId, parentGroupId, className, classPK, liveGroupId, nameMap,
			Collections.emptyMap(), TYPE_SITE_OPEN, manualMembership,
			DEFAULT_MEMBERSHIP_RESTRICTION, friendlyURL, true, active,
			serviceContext);

		return new SiteImpl(group);
	}

	@Override
	public void deleteSite(long siteId) throws PortalException {
		_groupLocalService.deleteGroup(siteId);
	}

	@Override
	public PageItems<Site> getPageItems(Pagination pagination, long companyId) {
		List<Group> groups = _groupLocalService.getGroups(companyId, 0, true);
		int count = _groupLocalService.getGroupsCount(companyId, 0, true);

		List<Group> pageGroups = ListUtil.subList(
			groups, pagination.getStartPosition(), pagination.getEndPosition());

		Stream<Group> stream = pageGroups.stream();

		List<Site> sites = stream.map(
			SiteImpl::new
		).collect(
			Collectors.toList()
		);

		return new PageItems<>(sites, count);
	}

	@Override
	public Site getSite(long siteId) throws PortalException {
		return new SiteImpl(_groupLocalService.getGroup(siteId));
	}

	@Override
	public Site updateSite(
			long siteId, long parentGroupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, int type,
			boolean manualMembership, int membershipRestriction,
			String friendlyURL, boolean inheritContent, boolean active,
			ServiceContext serviceContext)
		throws PortalException {

		Group group = _groupLocalService.updateGroup(
			siteId, parentGroupId, nameMap, descriptionMap, type,
			manualMembership, membershipRestriction, friendlyURL,
			inheritContent, active, serviceContext);

		return new SiteImpl(group);
	}

	@Reference
	private GroupLocalService _groupLocalService;

}