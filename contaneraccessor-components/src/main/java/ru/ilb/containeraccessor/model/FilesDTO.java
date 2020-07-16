/*
 * Copyright 2020 andrewsych.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ru.ilb.containeraccessor.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andrewsych
 */
@XmlRootElement(name = "files")
@XmlAccessorType (XmlAccessType.FIELD)
public class FilesDTO implements Serializable{
    @XmlElement(name = "employee")
    private List<FileDTO> filesDTO = null;
    
    public FilesDTO(){
        
    }
    
    public FilesDTO(List<FileDTO> listFiles){
        this.filesDTO = listFiles;
    }

    /**
     * @return the filesDTO
     */
    public List<FileDTO> getFilesDTO() {
        return filesDTO;
    }

    /**
     * @param filesDTO the filesDTO to set
     */
    public void setFilesDTO(List<FileDTO> filesDTO) {
        this.filesDTO = filesDTO;
    }

}
