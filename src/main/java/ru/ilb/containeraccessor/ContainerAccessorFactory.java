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

    public ContainerAccessor getContainerAccessor(URI uri, String mediaType) {
        return new ContainerAccessorImpl(uri);
    }
}
