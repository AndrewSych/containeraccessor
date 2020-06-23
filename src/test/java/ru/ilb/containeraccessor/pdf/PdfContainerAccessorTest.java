/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ilb.containeraccessor.pdf;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ru.ilb.containeraccessor.ContainerItem;

/**
 *
 * @author slavb
 */
public class PdfContainerAccessorTest {

    PdfContainerAccessor instance;

    public PdfContainerAccessorTest() {
    }

    @BeforeEach
    public void setUp() throws URISyntaxException {
        URI pdfUri = this.getClass().getResource("test.pdf").toURI();
        instance = new PdfContainerAccessor(pdfUri);

    }

    @AfterEach
    public void tearDown() throws IOException {
        instance.close();
    }

    /**
     * Test of getContentsPath method, of class PdfContainerAccessor.
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
     * Test of getItems method, of class PdfContainerAccessor.
     */
    @Test
    public void testGetItems() {
        System.out.println("getItems");
        List<ContainerItem> expResult = Arrays.asList(new ContainerItem("page-000.jpg"));
        List<ContainerItem> result = instance.getItems();
        assertEquals(expResult, result);
    }

    /**
     * Test of getItemContents method, of class PdfContainerAccessor.
     */
    @Test
    public void testGetItemContents() {
        System.out.println("getItemContents");
        String name = "page-000.jpg";
        byte[] result = instance.getItemContents(name);
        assertEquals(55505, result.length);
    }

    /**
     * Test of close method, of class PdfContainerAccessor.
     */
    @Test
    public void testClose() throws Exception {
        System.out.println("close");
//        instance.close();
//        assertFalse(instance.getContentsPath().toFile().exists(), "temp folder exists after close");
    }

}
