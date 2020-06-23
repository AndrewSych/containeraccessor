/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ilb.containeraccessor;

import ru.ilb.containeraccessor.pdf.PdfContainerExtractor;
import java.net.URI;
import javax.inject.Named;

/**
 *
 * @author slavb
 */
@Named
public class ContainerExtractorFactory {

    public ContainerExtractor getContainerExtractor(String mediaType) {
         switch (mediaType) {
            case "application/pdf":
                return new PdfContainerExtractor();
            default:
                throw new IllegalArgumentException("unsupported mediatype " + mediaType);
         }
    }
}
