/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ilb.containeraccessor;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import ru.ilb.containeraccessor.ContainerAccessor;
import ru.ilb.containeraccessor.ContainerExtractor;

public class ContainerAccessorImpl implements ContainerAccessor {

    private final URI uri;

    private Path contentsPath;

    private final ContainerExtractor containerExtractor;

    public ContainerAccessorImpl(URI uri, ContainerExtractor containerExtractor) {
        this.uri = uri;
        this.containerExtractor = containerExtractor;
    }

    @Override
    public Path getContentsPath() {
        if (this.contentsPath == null) {
            try {
                this.contentsPath = Files.createTempDirectory("containerextractor");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            containerExtractor.extract(uri, contentsPath, "page");
        }
        return contentsPath;
    }
    /**
     * cleanup
     *
     * @throws java.io.IOException
     */
    @Override
    public void close() throws IOException {
        if (contentsPath != null) {
            delete(contentsPath);
        }
    }

    /**
     * delete directory including files and sub-folders
     *
     * @param path
     * @throws IOException
     */
    private static void delete(Path path) throws IOException {
        Files.walk(path).sorted(Comparator.reverseOrder())
//                .peek(System.out::println)
                .map(Path::toFile)
                .forEach(File::delete);
    }
}
