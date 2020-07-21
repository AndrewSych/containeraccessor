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

        Path folder = Files.createTempDirectory("PdfFromJsonToJpegFilesImplTest");
        folder.toFile().deleteOnExit();
        ContainerExtractorJson instance = new ContainerExtractorJson();
        instance.extract(jsonUri, folder);
        String pageFileName = "test_page-000.jpg";
        Path pagePath = folder.resolve(pageFileName);
        assertTrue(Files.exists(pagePath), pageFileName + " not exists");
    }
}
