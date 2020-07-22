/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ilb.containeraccessor.core;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.Comparator;
import ru.ilb.uriaccessor.URIAccessor;
import ru.ilb.uriaccessor.URIAccessorFactory;

public class ContainerAccessorImpl implements ContainerAccessor {

    URIAccessorFactory uriAccessorFactory = new URIAccessorFactory();
    ContainerExtractorFactory containerExtractorFactory = new ContainerExtractorFactory();

    private final URIAccessor uriAccessor;

    private URI localUri;
    private Path contentsPath;

    private ContainerExtractor containerExtractor;


    public ContainerAccessorImpl(URI uri) {
        this.uriAccessor = uriAccessorFactory.getURIAccessor(uri);
    }
    public ContainerAccessorImpl(URIAccessor uriAccessor) {
        this.uriAccessor = uriAccessor;
    }

    @Override
    public Path getContentsPath() throws IOException {
        if (this.contentsPath == null) {
            // localize uri
            this.localUri = uriAccessor.getLocalUri();
            this.contentsPath = uriAccessor.getStorage().resolve("contents");
            this.containerExtractor = containerExtractorFactory.getContainerExtractor(uriAccessor.getContentType());
            boolean contentsExists = Files.exists(contentsPath);
            if (contentsExists) {
                FileTime contentsLastMod = Files.getLastModifiedTime(contentsPath);
                FileTime fileLastMod = Files.getLastModifiedTime(Paths.get(localUri));
                // unpaked contents older than file, cleanup
                if (contentsLastMod.compareTo(fileLastMod) < 0) {
                    delete(contentsPath);
                    contentsExists = false;
                }
            }
            if (!contentsExists) {
                Files.createDirectories(contentsPath);
                containerExtractor.extract(uriAccessor, contentsPath);
            }
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
