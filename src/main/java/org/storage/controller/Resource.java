package org.storage.controller;

import java.io.File;

/**
 * User: frederik.anrys
 * Date: 3/8/13
 * Time: 3:45 PM
 */
public class Resource
{
    String fileName;
    String url;
    boolean isFile;

    public Resource(String fileName, String url, boolean file)
    {
        this.fileName = fileName;
        this.url = url;
        isFile = file;
    }

    public String getName()
    {
        return fileName;
    }

    public String getUrl()
    {
        return url;
    }
    public String getType(){
        return isFile ? "file" : "directory";
    }
}
