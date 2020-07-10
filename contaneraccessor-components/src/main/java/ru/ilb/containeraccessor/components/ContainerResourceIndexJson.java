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

import java.net.URI;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import ru.ilb.containeraccessor.core.ContainerAccessor;
import ru.ilb.containeraccessor.core.ContainerAccessorFactory;
import ru.ilb.uriaccessor.URIAccessor;
import ru.ilb.uriaccessor.URIAccessorFactory;
import ru.ilb.uriaccessor.URIStorage;

public class ContainerResourceIndexJson implements ContainerResource {
private final URI uri;

    private final URIAccessor uriAccessor;

    private final URIStorage uriStorage;

    URIAccessorFactory uriAccessorFactory = new URIAccessorFactory();

    private final ContainerAccessorFactory caf = new ContainerAccessorFactory();

    private final ContainerAccessor containerAccessor;

    public ContainerResourceIndexJson(URI uri, URIStorage uriStorage) {
        this.uri = uri;
        this.uriAccessor = uriAccessorFactory.getURIAccessor(uri);
        this.uriStorage = uriStorage;
        this.containerAccessor = caf.getContainerAccessor(this.uriAccessor);
    }
    @Override
    public Response get(String accept) {
        //containerAccessor.getContentsPath();
        String json = "{}";
        return Response.ok(json).header(HttpHeaders.CONTENT_TYPE, "application/json").build();
    }


    @Override
    public ContainerResource subResource(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
