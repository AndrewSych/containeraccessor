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
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.Comparator;
import ru.ilb.jfunction.resources.URIToTempPathFunction;
import ru.ilb.jfunction.resources.URIToLocalURIFunction;

public class ContainerAccessorImpl implements ContainerAccessor {

    private final URIToTempPathFunction uriToTempPathFunction = URIToTempPathFunction.INSTANCE;
    private final URIToLocalURIFunction uriToLocalUriFunction = URIToLocalURIFunction.INSTANCE;

    private final URI uri;

    private URI localUri;
    private Path contentsPath;

    private final ContainerExtractor containerExtractor;

    public ContainerAccessorImpl(URI uri, ContainerExtractor containerExtractor) {
        this.uri = uri;
        this.containerExtractor = containerExtractor;
    }

    @Override
    public Path getContentsPath() throws IOException {
        if (this.contentsPath == null) {
            // localize uri
            this.localUri = uriToLocalUriFunction.apply(uri);
            this.contentsPath = uriToTempPathFunction.apply(uri);
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
                containerExtractor.extract(localUri, contentsPath);
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
