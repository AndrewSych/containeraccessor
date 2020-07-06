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
package ru.ilb.containeraccessor;

import java.net.URI;
import java.nio.file.Path;
import javax.ws.rs.core.Response;


public class ContainerResourceImpl implements ContainerResource {


    private final Path path;

    public ContainerResourceImpl(Path path) {
        this.path = path;
    }


    @Override
    public Response containerDownload(String name, String accept) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
