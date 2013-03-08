package org.storage.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServlet;
import java.io.IOException;


@Controller
public class SleepServlet extends HttpServlet
{

    Logger logger = LoggerFactory.getLogger(SleepServlet.class);

    @RequestMapping(value = "/sleep")
    public String sleep() throws IOException
    {
        logger.debug("Going to sleep");

      //  Runtime.getRuntime().exec("Rundll32.exe powrprof.dll,SetSuspendState Sleep");

        return "sleep";
    }
}