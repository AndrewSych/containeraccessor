/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ilb.containeraccessor;

import java.net.URI;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author slavb
 */
@Named
public class ContainerAccessorFactory {

    private final ContainerExtractorFactory containerExtractorFactory;

    @Inject
    public ContainerAccessorFactory(ContainerExtractorFactory containerExtractorFactory) {
        this.containerExtractorFactory = containerExtractorFactory;
    }

    public ContainerAccessor getContainerAccessor(URI uri, String mediaType) {
         switch (mediaType) {
            case "application/pdf":
                return new ContainerAccessorImpl(uri, containerExtractorFactory.getContainerExtractor(mediaType));
            default:
                throw new IllegalArgumentException("unsupported mediatype " + mediaType);
         }
    }
}
