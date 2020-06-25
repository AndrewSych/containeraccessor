/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ilb.containeraccessor;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 *
 * @author slavb
 */
public class ContainerAccessorImplTest {

    ContainerAccessorImpl instance;

    public ContainerAccessorImplTest() {
    }

    @BeforeEach
    public void setUp() throws URISyntaxException {
        URI pdfUri = this.getClass().getResource("test.pdf").toURI();
        instance = new ContainerAccessorImpl(pdfUri, new PdfContainerExtractor());
    }

    @AfterEach
    public void tearDown() throws IOException {
        instance.close();
    }

    /**
     * Test of getContentsPath method, of class ContainerAccessorImpl.
     *
     * @throws java.net.URISyntaxException
     */
    @Test
    public void testGetContentsPath() throws URISyntaxException {
        System.out.println("getContentsPath");

        Path result = instance.getContentsPath();
        assertTrue(result.toString().contains("containerextractor"));
    }

    /**
     * Test of close method, of class ContainerAccessorImpl.
     */
    @Test
    @Disabled
    public void testClose() throws Exception {
        System.out.println("close");
        instance.close();
        assertFalse(instance.getContentsPath().toFile().exists(), "temp folder " + instance.getContentsPath().toString() + " exists after close");
    }

}
