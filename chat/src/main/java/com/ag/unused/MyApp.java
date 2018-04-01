package com.ag.unused;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import java.util.logging.Logger;

/**
 * just for training
 */
@WebListener
public class MyApp implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("Servlet context started.");
        Logger.getLogger(MyApp.class.getName()).info("Servlet context started.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
