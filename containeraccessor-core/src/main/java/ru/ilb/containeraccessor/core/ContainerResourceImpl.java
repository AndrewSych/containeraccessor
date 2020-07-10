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
package ru.ilb.containeraccessor.core;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.ws.rs.core.Response;

public class ContainerResourceImpl implements ContainerResource {

    private final URI uri;

    private final ContainerAccessorFactory caf = new ContainerAccessorFactory();

    private ContainerAccessor containerAccessor;

    public ContainerResourceImpl(URI uri) {
        this.uri = uri;
    }

    @Override
    public Response get(String accept) {
        //FIXME hardcode mimeType
//        containerAccessor = caf.getContainerAccessor(uri, "application/pdf");
//        try {
//            Path path = containerAccessor.getContentsPath().resolve(name);
//            byte[] contents = Files.readAllBytes(path);
//            return Response.ok(contents).header("Co", uri).build();
//        } catch (IOException ex) {
//            throw new RuntimeException(ex);
//        }
        return null;
    }

    @Override
    public ContainerResource subResource() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
