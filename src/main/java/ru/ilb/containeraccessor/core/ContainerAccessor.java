/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ilb.containeraccessor.core;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Path;

/**
 *
 * @author slavb
 */
public interface ContainerAccessor extends Closeable {

    /**
     * get path to file contents
     * @return
     * @throws java.io.IOException
     */
    public Path getContentsPath() throws IOException;
}
