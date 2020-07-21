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
import java.io.IOException;
import java.net.URI;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZoneOffset;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import ru.ilb.common.jaxb.util.JaxbUtil;
import ru.ilb.jfunction.runtime.RuntimeFunction;

/**
 *
 * @author andrewsych
 */
public class ContainerExtractorJson implements ContainerExtractor {

    @Override
    public void extract(URI uri, Path folder) throws IOException {
        Path jsonPath = Paths.get(uri.getPath());
        if (!Files.exists(jsonPath)) {
            throw new IllegalArgumentException(jsonPath.toString() + " does not exists");
        }

        String data = readFile(jsonPath.toString());
        ru.ilb.containeraccessor.model.Files fls = (ru.ilb.containeraccessor.model.Files) unmarshal(data, ru.ilb.containeraccessor.model.Files.class, "application/json");

        fls.getFiles().stream().forEach(x -> {
            String path = (x.getPath().isEmpty() || x.getPath() == null) ? x.getFilename() : x.getPath();
            String prefix = x.getFilename().contains(".") ? x.getFilename().split("\\.")[0] + "_page" : x.getFilename() + "_page";
            String[] command = new String[]{"pdfimages", "-j", jsonPath.getParent().resolve(path).toString(), folder.resolve(prefix).toString()};
            RuntimeFunction instance = new RuntimeFunction(command);
            instance.apply(new byte[]{});
            
//            try {
//                Files.list(folder).forEach(z -> {
//                    z.toFile().setLastModified(x.getLastModified().toEpochSecond(ZoneOffset.UTC));
//                });
//            } catch (IOException ex) {
//                Logger.getLogger(ContainerExtractorJson.class.getName()).log(Level.SEVERE, null, ex);
//            }

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

    private static String readFile(String path) throws IOException {
        try (FileInputStream stream = new FileInputStream(new File(path))) {
            FileChannel fc = stream.getChannel();
            MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
            return Charset.defaultCharset().decode(bb).toString();
        }
    }

}
