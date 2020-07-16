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
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ContextResolver;
import javax.xml.bind.JAXBContext;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ilb.containeraccessor.core.ContainerAccessor;
import ru.ilb.containeraccessor.core.ContainerAccessorFactory;
import ru.ilb.uriaccessor.URIAccessor;
import ru.ilb.uriaccessor.URIAccessorFactory;
import ru.ilb.uriaccessor.URIStorage;
import ru.ilb.common.jaxb.util.JaxbUtil;
import ru.ilb.containeraccessor.model.FileDTO;

public class ContainerResourceIndexJson implements ContainerResource {

    @Autowired
    private ContextResolver<JAXBContext> jaxbContextResolver;

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
    public Response get(String accept) throws IOException {
        if (accept == null) {
            throw new IllegalArgumentException("MediaType is empty");
        }

        List<FileDTO> files = new ArrayList<>();
        Files.list(containerAccessor.getContentsPath()).forEach(x -> {

            FileDTO file = new FileDTO();
            try {
                file.setFilename(x.getFileName().toString());
                file.setLastModifiedDate(Files.getLastModifiedTime(Paths.get(x.toUri())).toMillis());
                file.setSize(Files.size(x));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            files.add(file);
        });
        if(files.isEmpty()){
            throw new IllegalArgumentException("There are no files");
        }
        String json = marshal(files, accept);
        switch (accept) {
            case MediaType.APPLICATION_JSON:
                return Response.ok(json, MediaType.APPLICATION_JSON).build();
            default:
                throw new IllegalArgumentException("error");
        }

    }

    @Override
    public ContainerResource subResource(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String marshal(Object obj, String mediaType) {
        JAXBContext jaxbContext = jaxbContextResolver.getContext(obj.getClass());
        return JaxbUtil.marshal(jaxbContext, obj, mediaType);
    }

}
