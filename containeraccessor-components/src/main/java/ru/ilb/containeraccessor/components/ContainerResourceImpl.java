/*
 * Copyright 2020 slavb.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.ilb.containeraccessor.components;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import ru.ilb.containeraccessor.core.ContainerAccessor;
import ru.ilb.containeraccessor.core.ContainerAccessorFactory;
import ru.ilb.uriaccessor.URIAccessor;
import ru.ilb.uriaccessor.URIAccessorFactory;
import ru.ilb.uriaccessor.URIStorage;

public class ContainerResourceImpl implements ContainerResource {

    private final URI uri;

    private final URIAccessor uriAccessor;

    private final URIStorage uriStorage;

    URIAccessorFactory uriAccessorFactory = new URIAccessorFactory();

    private final ContainerAccessorFactory caf = new ContainerAccessorFactory();

    private final ContainerAccessor containerAccessor;

    public ContainerResourceImpl(URI uri, URIStorage uriStorage) {
        this.uri = uri;
        this.uriAccessor = uriAccessorFactory.getURIAccessor(uri);
        this.uriStorage = uriStorage;
        this.containerAccessor = caf.getContainerAccessor(this.uriAccessor);
    }

    @Override
    public Response get(String accept) {
        try {
            return Response.ok(uriAccessor.getContent()).header(HttpHeaders.CONTENT_TYPE, uriAccessor.getContentType()).build();
        } catch (IOException ex) {
            throw new WebApplicationException(ex);
        }
    }

    @Override
    public ContainerResource subResource(String name) {
        try {
            Path path = containerAccessor.getContentsPath().resolve(name);
            if (Files.exists(path)) {
                return new ContainerResourceImpl(path.toUri(), uriStorage);
            } else if ("index".equals(name)) {
                return new ContainerResourceIndexJson(uri, uriStorage);
            } else {
                throw new NotFoundException(name + " not found");
            }
        } catch (IOException ex) {
            throw new WebApplicationException(ex);
        }
    }
}
