/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ilb.containeraccessor;

import java.io.Closeable;
import java.nio.file.Path;
import java.util.List;

/**
 *
 * @author slavb
 */
public interface ContainerAccessor extends Closeable{

    /**
     * List items in container
     * @return
     */
    List<ContainerItem> getItems();

    /**
     * get item contents
     * @param name
     * @return
     */
    byte[] getItemContents(String name);

    /**
     * get path to extracted contents
     * @return
     */
    public Path getContentsPath();
}
