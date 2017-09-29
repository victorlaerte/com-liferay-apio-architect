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

package com.liferay.vulcan.sample.liferay.portal.internal.resource;

import static com.liferay.portal.kernel.model.GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION;
import static com.liferay.portal.kernel.model.GroupConstants.TYPE_SITE_OPEN;

import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.vulcan.liferay.portal.context.CurrentUser;
import com.liferay.vulcan.pagination.PageItems;
import com.liferay.vulcan.pagination.Pagination;
import com.liferay.vulcan.resource.CollectionResource;
import com.liferay.vulcan.resource.Representor;
import com.liferay.vulcan.resource.Routes;
import com.liferay.vulcan.resource.builder.RepresentorBuilder;
import com.liferay.vulcan.resource.builder.RoutesBuilder;
import com.liferay.vulcan.resource.identifier.LongIdentifier;
import com.liferay.vulcan.resource.identifier.RootIdentifier;
import com.liferay.vulcan.result.Try;
import com.liferay.vulcan.sample.liferay.portal.site.Site;
import com.liferay.vulcan.sample.liferay.portal.site.SiteService;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Victor Oliveira
 */
@Component(immediate = true)
public class SiteCollectionResource
	implements CollectionResource<Site, LongIdentifier> {

	@Override
	public Representor<Site, LongIdentifier> buildRepresentor(
		RepresentorBuilder<Site, LongIdentifier> representorBuilder) {

		return representorBuilder.identifier(
			Site::getSiteLongIdentifier
		).addField(
			"name", Site::getName
		).addField(
			"description", Site::getDescription
		).addType(
			"WebSite"
		).build();
	}

	@Override
	public String getName() {
		return "sites";
	}

	@Override
	public Routes<Site> routes(
		RoutesBuilder<Site, LongIdentifier> routesBuilder) {

		return routesBuilder.addCollectionPageGetter(
			this::_getPageItems, RootIdentifier.class, Company.class
		).addCollectionPageItemCreator(
			this::_addSite, RootIdentifier.class, CurrentUser.class
		).addCollectionPageItemGetter(
			this::_getSite
		).addCollectionPageItemRemover(
			this::_deleteSite
		).addCollectionPageItemUpdater(
			this::_updateSite
		).build();
	}

	private Site _addSite(
		RootIdentifier rootIdentifier, Map<String, Object> body,
		CurrentUser currentUser) {

		String name = (String)body.get("name");

		if (Validator.isNull(name)) {
			throw new BadRequestException("Invalid body");
		}

		Try<Site> siteTry = Try.fromFallible(
			() -> _siteService.addSite(
				currentUser.getUserId(), 0, Group.class.getName(), 0, 0,
				Collections.singletonMap(Locale.US, name),
				Collections.emptyMap(), TYPE_SITE_OPEN, false,
				DEFAULT_MEMBERSHIP_RESTRICTION, null, true, null));

		return siteTry.getUnchecked();
	}

	private void _deleteSite(LongIdentifier siteLongIdentifier) {
		try {
			_siteService.deleteSite(siteLongIdentifier.getId());
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private PageItems<Site> _getPageItems(
		Pagination pagination, RootIdentifier rootIdentifier, Company company) {

		return _siteService.getPageItems(pagination, company.getCompanyId());
	}

	private Site _getSite(LongIdentifier siteLongIdentifier) {
		try {
			return _siteService.getSite(siteLongIdentifier.getId());
		}
		catch (NoSuchGroupException nsge) {
			throw new NotFoundException(
				"Unable to get group " + siteLongIdentifier.getId(), nsge);
		}
		catch (PortalException pe) {
			throw new ServerErrorException(500, pe);
		}
	}

	private Site _updateSite(
		LongIdentifier groupLongIdentifier, Map<String, Object> body) {

		String name = (String)body.get("name");

		if (Validator.isNull(name)) {
			throw new BadRequestException("Invalid body");
		}

		Try<Site> siteTry = Try.fromFallible(
			() -> _siteService.updateSite(
				groupLongIdentifier.getId(), 0,
				Collections.singletonMap(Locale.US, name),
				Collections.emptyMap(), TYPE_SITE_OPEN, false,
				DEFAULT_MEMBERSHIP_RESTRICTION, null, true, true, null));

		return siteTry.getUnchecked();
	}

	@Reference
	private SiteService _siteService;

}