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

package com.liferay.vulcan.architect.endpoint;

import com.liferay.vulcan.architect.pagination.Page;
import com.liferay.vulcan.architect.pagination.SingleModel;
import com.liferay.vulcan.architect.result.Try;

import java.io.InputStream;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Declares the endpoint from which all of your APIs originate.
 *
 * <p>
 * There should only be one RootEndpoint in the application.
 * </p>
 *
 * @author Alejandro Hernández
 * @author Carlos Sierra Andrés
 * @author Jorge Ferrer
 */
public interface RootEndpoint {

	/**
	 * Adds a new {@link SingleModel} by performing a POST request to the given
	 * name's resource or an exception if an error occurred.
	 *
	 * @param  name the name of the desired resource, extracted from the URL.
	 * @return the created single model, or an exception if there was an error.
	 */
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/p/{name}")
	@POST
	public <T> Try<SingleModel<T>> addCollectionItem(
		@PathParam("name") String name, Map<String, Object> body);

	/**
	 * Adds a new {@link SingleModel} by performing a POST request to the given
	 * name's resource or an exception if an error occurred.
	 *
	 * @param  name the name of the parent resource, extracted from the URL.
	 * @param  id the ID of the resource.
	 * @param  nestedName the name of the desired resource, extracted from the
	 *         URL.
	 * @return the created single model, or an exception if there was an error.
	 */
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/p/{name}/{id}/{nestedName}")
	@POST
	public <T> Try<SingleModel<T>> addNestedCollectionItem(
		@PathParam("name") String name, @PathParam("id") String id,
		@PathParam("nestedName") String nestedName, Map<String, Object> body);

	/**
	 * Deletes a collection item for a given name or an exception if an error
	 * occurred.
	 *
	 * @param  name the name of the resource to be deleted, extracted from the
	 *         URL.
	 * @param  id the ID of the resource.
	 * @return the response for the operation.
	 */
	@DELETE
	@Path("/p/{name}/{id}")
	public Response deleteCollectionItem(
		@PathParam("name") String name, @PathParam("id") String id);

	/**
	 * Returns the {@link InputStream} for a given resource identifier or an
	 * exception if an error occurred.
	 *
	 * @param  name the name the resource, extracted from the URL.
	 * @param  id the ID of the resource.
	 * @param  binaryId the ID to the binary resource.
	 * @return the input stream of the binary file, or an exception it there was
	 *         an error.
	 */
	@GET
	@Path("/b/{name}/{id}/{binaryId}")
	public Try<InputStream> getCollectionItemInputStreamTry(
		@PathParam("name") String name, @PathParam("id") String id,
		@PathParam("binaryId") String binaryId);

	/**
	 * Returns the {@link SingleModel} for a given name or an exception if an
	 * error occurred.
	 *
	 * @param  name the name of the desired resource, extracted from the URL.
	 * @param  id the ID of the resource.
	 * @return the single model of a resource with this name, or an exception it
	 *         there was an error.
	 */
	@GET
	@Path("/p/{name}/{id}")
	public <T> Try<SingleModel<T>> getCollectionItemSingleModelTry(
		@PathParam("name") String name, @PathParam("id") String id);

	/**
	 * Returns the collection {@link Page} for a resource with the provided name
	 * or an exception if an error occurred.
	 *
	 * @param  name the name of the desired resource, extracted from the URL.
	 * @return the collection page of a resource with this name, or an exception
	 *         if there was an error.
	 */
	@GET
	@Path("/p/{name}")
	public <T> Try<Page<T>> getCollectionPageTry(
		@PathParam("name") String name);

	/**
	 * Returns a nested collection {@link Page} for a given set of
	 * name-id-nestedName or an exception if an error occurred.
	 *
	 * @param  name the name of the parent resource, extracted from the URL.
	 * @param  id the ID of the resource.
	 * @param  nestedName the name of the nested resource.
	 * @return the collection page of a resource with this combination of
	 *         name-id-nestedName, or an exception if there was an error.
	 */
	@GET
	@Path("/p/{name}/{id}/{nestedName}")
	public <T> Try<Page<T>> getNestedCollectionPageTry(
		@PathParam("name") String name, @PathParam("id") String id,
		@PathParam("nestedName") String nestedName);

	/**
	 * Updates a collection item for a given name or an exception if an error
	 * occurred.
	 *
	 * @param  name the name of the resource to be updated, extracted from the
	 *         URL.
	 * @param  id the ID of the resource.
	 * @return the updated single model, or an exception if there was an error.
	 */
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/p/{name}/{id}")
	@PUT
	public <T> Try<SingleModel<T>> updateCollectionItem(
		@PathParam("name") String name, @PathParam("id") String id,
		Map<String, Object> body);

}