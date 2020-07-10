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

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import ru.ilb.jfunction.runtime.RuntimeFunction;

public class ContainerExtractorPdf implements ContainerExtractor {


    @Override
    public void extract(URI uri, Path folder)  throws IOException{
        Path pdfPath = Paths.get(uri.getPath());
        if (!Files.exists(pdfPath)) {
            throw new IllegalArgumentException(pdfPath.toString() + " does not exists");
        }
        String prefix = "page";
        String[] command = new String[]{"pdfimages", "-j", pdfPath.toString(), folder.resolve(prefix).toString()};
        RuntimeFunction instance = new RuntimeFunction(command);
        instance.apply(new byte[]{});
    }
}
