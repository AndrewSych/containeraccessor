/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ilb.containeraccessor.pdf;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import ru.ilb.containeraccessor.ContainerAccessor;
import ru.ilb.containeraccessor.ContainerExtractor;
import ru.ilb.containeraccessor.ContainerItem;

public class PdfContainerAccessor implements ContainerAccessor {

    private final URI uri;

    private Path contentsPath;

    private final ContainerExtractor containerExtractor = new PdfContainerExtractor();

    public PdfContainerAccessor(URI uri) {
        this.uri = uri;
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

    @Override
    public List<ContainerItem> getItems() {
        try {
            return Files.walk(this.getContentsPath())
                    .filter(Files::isRegularFile).sorted()
                    .map(p -> new ContainerItem(p.getFileName().toString()))
                    .collect(Collectors.toList());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public byte[] getItemContents(String name) {
        try {
            return Files.readAllBytes(getContentsPath().resolve(name));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
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
                .peek(System.out::println)
                .map(Path::toFile)
                .forEach(File::delete);
    }
}
