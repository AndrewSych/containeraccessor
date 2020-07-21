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
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;
import ru.ilb.containeraccessor.core.ContainerAccessor;
import ru.ilb.containeraccessor.core.ContainerAccessorFactory;
import ru.ilb.uriaccessor.URIAccessor;
import ru.ilb.uriaccessor.URIAccessorFactory;
import ru.ilb.uriaccessor.URIStorage;
import ru.ilb.containeraccessor.model.FileDTO;

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
    public Response get(String accept) throws IOException {
        if (accept != null && !accept.equals(MediaType.APPLICATION_JSON)) {
            throw new WebApplicationException("MediaType is wrong");
        }

        List<FileDTO> files = new ArrayList<>();
        Files.list(containerAccessor.getContentsPath()).forEach(x -> {
            FileDTO file = new FileDTO();
            file.setFilename(x.getFileName().toString());
            LocalDateTime date = Instant.ofEpochSecond(x.toFile().lastModified() / 1000).atZone(ZoneId.systemDefault()).toLocalDateTime();
            file.setLastModified(date);
            file.setSize(x.toFile().length());
            files.add(file);
        });
        String json = new JSONObject(new ru.ilb.containeraccessor.model.Files(files)).toString();
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
        
    }

    @Override
    public ContainerResource subResource(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
