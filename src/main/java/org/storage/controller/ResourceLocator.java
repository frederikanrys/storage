package org.storage.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * User: frederik.anrys
 * Date: 1/25/13
 * Time: 9:46 PM
 */

@Controller
public class ResourceLocator extends HttpServlet
{

    @javax.annotation.Resource(name = "mountPoints")
    private Hashtable<String, String> mountPoints;

    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public String getData(ModelMap model)
    {
        ArrayList<Resource> resources = new ArrayList<Resource>();

        for (String mountPoint : mountPoints.keySet())
        {
            resources.add(new Resource(mountPoint, getMountPointUrl(mountPoint), false));
        }

        model.put("parent", new Resource("mountpoints", getRootUrl(), false));
        model.put("resources", resources);
        return "complex";
    }



    @RequestMapping(value = "/data/{mountpoint}", method = RequestMethod.GET)
    public String getData(@PathVariable String mountpoint, @RequestParam(value = "path", required = false) String path,

                          ModelMap model, HttpServletResponse response) throws IOException
    {
        // path
        path = (path != null) ? path : "";

        // mountpoint
        String docBase = mountPoints.get(mountpoint);
        if (docBase == null) throw  new ResourceNotFoundException("mountPoint");

        // requested file or directory
        File root = new File(docBase, path);

        // prevent breaking out of path
        if (!root.getCanonicalPath().startsWith(new File(docBase).getCanonicalPath()))
        {
            root = new File(docBase);
            path = "";
        }

        if (!root.exists()) throw  new ResourceNotFoundException(root.toString());

        // parent
        final String parent = new File(path).getParent();


        if (parent != null)
        {
            model.put("parent", new Resource(parent, getUrl(mountpoint, parent), false));
        }
        else{ // mountpoints
            model.put("parent", new Resource("mountpoints", getRootUrl(), false));
        }

        // directory
        if (root.isDirectory())
        {
            ArrayList<Resource> resources = new ArrayList<Resource>();

            final File[] files = root.listFiles();
            if (files != null){
                for (final File fileEntry : files)
                {
                    resources.add(new Resource(fileEntry.getName(), getUrl(mountpoint, path + "/" + fileEntry.getName()), fileEntry.isFile()));
                }
            }
            model.put("resources", resources);

            return "complex";
        } else{ // file

                try {


                    response.setHeader("Content-Disposition","inline; filename=\"" + root.getName() +"\"");
                    response.setContentType(getContentType(root.getName()));
                    response.setContentLength((int) root.length());
                    // get your file as InputStream
                    InputStream is = new FileInputStream(root);
                    // copy it to response's OutputStream
                    IOUtils.copy(is, response.getOutputStream());
                    response.flushBuffer();
                } catch (IOException ex) {
//                    log.info("Error writing file to output stream. Filename was '" + fileName + "'");
                    throw new RuntimeException("IOError writing file to output stream");
                }
                return null;
        }
    }

    public static String getContentType(String filename)
    {
        String g = URLConnection.guessContentTypeFromName(filename);
        if( g == null)
        {
            g = MimetypesFileTypeMap.getDefaultFileTypeMap().getContentType(filename);
        }
        return g;
    }


    private String getRootUrl(){
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/data")
                .build()
                .encode()
                .toUriString();
    }

    private String getMountPointUrl(String mountPoint)
    {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/data/{mountpoint}")
                .buildAndExpand(mountPoint)
                .encode()
                .toUriString();
    }

    private String getUrl(String mountpoint, String path)
    {
        path = (path != null) ? path.replace("\\","/").replace("//", "/") : "";

        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/data/{mountpoint}").replaceQueryParam("path", "{path}")
                .build()
                .expand(mountpoint, path)
                .encode()
                .toUriString();
    }







}
