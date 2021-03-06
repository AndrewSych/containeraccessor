/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ilb.containeraccessor.core;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import ru.ilb.uriaccessor.URIAccessorFactory;

/**
 *
 * @author slavb
 */
public class ContainerExtractorJsonTest {

    /**
     * Test of extract method, of class ContainerExtractorPdf.
     *
     * @throws java.io.IOException
     * @throws java.net.URISyntaxException
     */
    @Test
    public void testExtract() throws IOException, URISyntaxException {
        System.out.println("extract");
        URI jsonUri = this.getClass().getResource("test.json").toURI();
        URIAccessorFactory f = new URIAccessorFactory();

        Path folder = Files.createTempDirectory("PdfFromJsonToJpegFilesImplTest");
        folder.toFile().deleteOnExit();
        ContainerExtractorJson instance = new ContainerExtractorJson();
        instance.extract(f.getURIAccessor(jsonUri), folder);
        String pageFileName = "test.pdf";
        String andotherPageFileName = "tested.pdf";
        Path pagePath = folder.resolve(pageFileName);
        Path anotherPagePath = folder.resolve(andotherPageFileName);
        assertTrue(Files.exists(pagePath), pageFileName + " not exists");
        assertTrue(Files.exists(anotherPagePath), andotherPageFileName + " not exists");
    }
}
