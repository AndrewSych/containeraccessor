/*
 * Copyright 2020 andrewsych.
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonObject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import ru.ilb.common.jaxb.util.JaxbUtil;
import ru.ilb.uriaccessor.URIAccessor;
import ru.ilb.uriaccessor.URIAccessorFactory;

/**
 *
 * @author andrewsych
 */
public class ContainerExtractorJson implements ContainerExtractor {

    @Override
    public void extract(URIAccessor uriAccessor, Path folder) throws IOException {
        Path jsonPath = Paths.get(uriAccessor.getUri());
        if (!Files.exists(jsonPath)) {
            throw new IllegalArgumentException(jsonPath.toString() + " does not exists");
        }
        String data = new String(uriAccessor.getContent());
        ru.ilb.containeraccessor.model.Files fls = (ru.ilb.containeraccessor.model.Files) unmarshal(data, ru.ilb.containeraccessor.model.Files.class, "application/json");
        URIAccessorFactory factory = new URIAccessorFactory();
        fls.getFiles().stream().forEach(x -> {
            String path = (x.getPath().isEmpty() || x.getPath() == null) ? x.getFilename() : x.getPath();
            URIAccessor accessor = factory.getURIAccessor(jsonPath.getParent().resolve(path).toUri());
            try (OutputStream fos = new FileOutputStream(folder.resolve(x.getFilename()).toString())) {
                fos.write(accessor.getContent());
                fos.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ContainerExtractorJson.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ContainerExtractorJson.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    private <T> T unmarshal(String data, Class<T> type, String mediaType) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ru.ilb.containeraccessor.model.Files.class);
            return JaxbUtil.unmarshal(jaxbContext, data, type, mediaType);
        } catch (JAXBException ex) {
            Logger.getLogger(ContainerExtractorJson.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
