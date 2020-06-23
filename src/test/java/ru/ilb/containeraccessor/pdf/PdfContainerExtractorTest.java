/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ilb.containeraccessor.pdf;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author slavb
 */
public class PdfContainerExtractorTest {

    /**
     * Test of extract method, of class PdfContainerExtractor.
     *
     * @throws java.io.IOException
     * @throws java.net.URISyntaxException
     */
    @Test
    public void testExtract() throws IOException, URISyntaxException {
        System.out.println("extract");
        URI pdfUri = this.getClass().getResource("test.pdf").toURI();

        Path folder = Files.createTempDirectory("PdfToJpegFilesImplTest");
        folder.toFile().deleteOnExit();
        PdfContainerExtractor instance = new PdfContainerExtractor();
        instance.extract(pdfUri, folder, "page");
        String pageFileName = "page-000.jpg";
        Path pagePath = folder.resolve(pageFileName);
        File pageFile = pagePath.toFile();
        assertTrue(pageFile.exists(), pageFileName + " not exists");
//        URI expectedPageUri = this.getClass().getResource("page-000.jpg").toURI();
//        File expectedPageFile = Paths.get(expectedPageUri).toFile();
//
        //assertBinaryEquals    }
    }
}
