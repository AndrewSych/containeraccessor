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
import javax.inject.Named;
import javax.ws.rs.core.Response;
import ru.ilb.uriaccessor.URIStorage;
import ru.ilb.uriaccessor.URIStorageFactory;

@Named
public class ContainersResourceImpl implements ContainersResource {

    URIStorageFactory uriStorageFactory = new URIStorageFactory();
    private final URIStorage uriStorage = uriStorageFactory.getURIStorage();

    @Override
    public ContainerResource subResource(String uriCode) {
        return new ContainerResourceImpl(uriStorage.getUri(uriCode));
    }

    @Override
    public Response getUri(String uri) {
        //redirec to subresource based on uriCode
        return Response.seeOther(URI.create("containers/" + uriStorage.registerUri(URI.create(uri)))).build();
    }

}
