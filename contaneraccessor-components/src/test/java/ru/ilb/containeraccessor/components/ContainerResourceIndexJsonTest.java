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
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import ru.ilb.jfunction.resources.URLToStringFunction;

/**
 *
 * @author slavb
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContainerResourceIndexJsonTest {

    private ContainersResource resource;

    @LocalServerPort
    private Integer randomPort;

    public ContainerResourceIndexJsonTest() {
    }

    private String getServiceBaseUri() {
        return "http://localhost:" + randomPort.toString() + "/web";
    }

    /**
     * Test of get method, of class ContainerResourceImpl.
     *
     * @throws java.net.URISyntaxException
     * @throws java.net.MalformedURLException
     */
    @Test
    public void testGet() throws URISyntaxException, MalformedURLException, IOException {
        URI pdfUri = this.getClass().getResource("test.pdf").toURI();
        URL input = new URL(getServiceBaseUri() + "/containers?uri=" + pdfUri.toString() + "&path=index.json");
        String apply = URLToStringFunction.INSTANCE.apply(input);
        assertEquals("{}", apply);
    }

}
