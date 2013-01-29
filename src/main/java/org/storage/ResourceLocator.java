package org.storage;

import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FileFileFilter;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * User: frederik.anrys
 * Date: 1/25/13
 * Time: 9:46 PM
 */
public class ResourceLocator  extends HttpServlet
{

    // make sure the docbase is also mapped to the context in tomcat/server.xml
    // <host ...>
    //      <Context path=<context> docBase=<docBase> />
    // </host>
    File context = new File("/d");
    File docBase =new File("d:\\");


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {

        resp.setContentType("text/html");

        String path = req.getParameter("path") != null? req.getParameter("path") : "" ;




        File folder = new File(docBase, path);

        // prevent breaking out of path
        if (!folder.getCanonicalPath().startsWith(docBase.getCanonicalPath())){
            folder = docBase;
            path = "";
        }



        PrintWriter out = resp.getWriter();
        String title = "Reading Three Request Parameters";
        out.println("<BODY>\n" +
                "<H1 ALIGN=CENTER>" + title + "</H1>\n" +
                "<UL>\n");

        String tmp = new File(path).getParent() != null ? new File(path).getParent() : "";
        out.println("  <li><a href=\""
                + this.getServletConfig().getServletName() + "?path=" + tmp + "\">..</a></li>" + "\n");
        for (final File fileEntry : folder.listFiles((FileFilter) DirectoryFileFilter.DIRECTORY)) {

                out.println("  <li><a href=\""
                        + this.getServletConfig().getServletName() + "?path=" +new File(path, fileEntry.getName()).getPath() + "\">(dir) " + fileEntry.getName() + "</a></li>" + "\n");
        }

        for (final File fileEntry : folder.listFiles((FileFilter) FileFileFilter.FILE)) {
                out.println("  <li> <a href=\""
                        + new File(new File(context, path), fileEntry.getName()).getPath() + "\">" + fileEntry.getName() + "</a></li>" + "\n");
        }


                out.println("</UL>\n" +
                "</BODY></HTML>");


    }



}
