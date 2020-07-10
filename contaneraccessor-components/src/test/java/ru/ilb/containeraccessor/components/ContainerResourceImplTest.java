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

import java.util.Arrays;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

/**
 *
 * @author slavb
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContainerResourceImplTest {

    private ContainersResource resource;

    @LocalServerPort
    private Integer randomPort;

    public ContainerResourceImplTest() {
    }

    private ContainersResource getContainersResource() {
        if (resource == null) {
            String port = randomPort.toString();
            String resourceUri = "http://localhost:" + port + "/web";
            System.out.println("resourceUri=" + resourceUri);
            resource = JAXRSClientFactory.create(resourceUri, ContainersResource.class);
        }
        return resource;

    }

    /**
     * Test of get method, of class ContainerResourceImpl.
     */
    @Test
    public void testGet() {
        ContainerResource res = getContainersResource().subResource("test");
        assertEquals("test", res.get("text/xml").readEntity(String.class));
    }

    /**
     * Test of subResource method, of class ContainerResourceImpl.
     */
    @Test
    public void testSubResource() {
    }

}
