/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ilb.containeraccessor.core;

import java.net.URI;
import javax.inject.Named;
import ru.ilb.uriaccessor.URIAccessor;

/**
 *
 * @author slavb
 */
@Named
public class ContainerAccessorFactory {

    public ContainerAccessor getContainerAccessor(URI uri) {
        return new ContainerAccessorImpl(uri);
    }

     public ContainerAccessor getContainerAccessor(URIAccessor uriAccessor) {
        return new ContainerAccessorImpl(uriAccessor);
    }
}
