package org.storage.controller;

import javax.activation.MimetypesFileTypeMap;
import java.net.URLConnection;

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

    public String getContentType()
    {
        String g = URLConnection.guessContentTypeFromName(fileName);
        if( g == null)
        {
            g = MimetypesFileTypeMap.getDefaultFileTypeMap().getContentType(fileName);
        }
        return g;
    }

    /*
 * Get the extension of a file.
 */
    public  String getExtension() {
        String ext = null;
        String s = getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
}
