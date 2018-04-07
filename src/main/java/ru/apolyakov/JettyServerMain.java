package ru.apolyakov;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.apolyakov.servlets.AllRequestsServlet;
import ru.apolyakov.servlets.MirrorServlet;

import java.util.logging.Logger;

/**
 * Entry point for starting jetty server
 * @author apolyakov
 * @since 07.04.2018
 */
public class JettyServerMain {
    public static void main(String[] args) throws Exception {
        // инициализация сервлетов
        // ! важно: это все только для Jetty, для Tomcat метод main не нужен
        AllRequestsServlet allRequestsServlet = new AllRequestsServlet();
        MirrorServlet mirrorServlet = new MirrorServlet();
        // контекст, содержащий карту соответствий <сервлет-url>
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(allRequestsServlet), "/*");
        context.addServlet(new ServletHolder(mirrorServlet), "/mirror");
        // старт сервера и указание карты доступныъ для него url-запросов
        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
        Logger.getGlobal().info("Server started");
        //System.out.println("Server started");
        server.join();
    }
}
