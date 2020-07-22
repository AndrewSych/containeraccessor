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
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import ru.ilb.uriaccessor.URIAccessor;
import ru.ilb.uriaccessor.URIAccessorFactory;

/**
 *
 * @author slavb
 */
public class ContainerExtractorPdfTest {

    /**
     * Test of extract method, of class ContainerExtractorPdf.
     *
     * @throws java.io.IOException
     * @throws java.net.URISyntaxException
     */
    @Test
    public void testExtract() throws IOException, URISyntaxException {
        System.out.println("extract");
        URI pdfUri = this.getClass().getResource("test.pdf").toURI();
        URIAccessorFactory f = new URIAccessorFactory();
        
        Path folder = Files.createTempDirectory("PdfToJpegFilesImplTest");
        folder.toFile().deleteOnExit();
        ContainerExtractorPdf instance = new ContainerExtractorPdf();
        instance.extract(f.getURIAccessor(pdfUri), folder);
        String pageFileName = "page-000.jpg";
        Path pagePath = folder.resolve(pageFileName);
        assertTrue(Files.exists(pagePath), pageFileName + " not exists");
        long pdfFileTime = Paths.get(pdfUri).toFile().lastModified();
        long pageFileTime = pagePath.toFile().lastModified();
        assertEquals(pdfFileTime, pageFileTime);

//        URI expectedPageUri = this.getClass().getResource("page-000.jpg").toURI();
//        File expectedPageFile = Paths.get(expectedPageUri).toFile();
//
        //assertBinaryEquals    }
    }
}
