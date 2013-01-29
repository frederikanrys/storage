package org.storage;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class SleepServlet extends HttpServlet
{


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text");

/*
        UrlConnectionHelper connection = new UrlConnectionHelper();
        String json = connection.getUrl("http://digipolis-dsd-test.dev.amplexor.com:8080/alfresco/service/slingshot/doclib/doclist/all/node/alfresco/company/home/Data%20Dictionary",
                "admin", "admin", null);
*/


        Runtime.getRuntime().exec("Rundll32.exe powrprof.dll,SetSuspendState Sleep");


        PrintWriter out = response.getWriter();
        out.print("Putting server to sleep");

    }
}