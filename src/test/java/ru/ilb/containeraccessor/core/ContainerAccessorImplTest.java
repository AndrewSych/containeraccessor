/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ilb.containeraccessor.core;

import ru.ilb.containeraccessor.core.ContainerAccessorImpl;
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
public class ContainerAccessorImplTest {

    public ContainerAccessorImplTest() {
    }

    /**
     * Test of getContentsPath method, of class ContainerAccessorImpl.
     *
     * @throws java.net.URISyntaxException
     * @throws java.io.IOException
     */
    @Test
    public void testGetContentsPath() throws URISyntaxException, IOException {
        System.out.println("getContentsPath");
        URI pdfUri = this.getClass().getResource("test.pdf").toURI();
        ContainerAccessorImpl instance = new ContainerAccessorImpl(pdfUri);

        Path folder = instance.getContentsPath();
        String pageFileName = "page-000.jpg";
        Path pagePath = folder.resolve(pageFileName);
        assertTrue(Files.exists(pagePath), pageFileName + " not exists");
    }

}
