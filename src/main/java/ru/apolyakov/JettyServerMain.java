package ru.apolyakov;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.apolyakov.accounts.AccountService;
import ru.apolyakov.accounts.UserProfile;
import ru.apolyakov.servlets.*;

import java.util.logging.Logger;

/**
 * Entry point for starting jetty server
 * @author apolyakov
 * @since 07.04.2018
 */
public class JettyServerMain {
    // ! важно: это все только для Jetty, для Tomcat метод main не нужен
    public static void main(String[] args) throws Exception {
        // инициализация сервисов и наполнение данными (in-memory db)
        AccountService accountService = new AccountService();
        accountService.addNewUser(new UserProfile("admin"));
        accountService.addNewUser(new UserProfile("test"));

        // инициализация сервлетов
        // контекст, содержащий карту соответствий <сервлет-url>
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new AllRequestsServlet()), "/*");
        context.addServlet(new ServletHolder(new MirrorServlet()), "/mirror");
        context.addServlet(new ServletHolder(new UsersServlet(accountService)), "/api/v1/users");
        context.addServlet(new ServletHolder(new SessionsServlet(accountService)), "/api/v1/sessions");
        context.addServlet(new ServletHolder(new SignUpServlet(accountService)), "/signup");
        context.addServlet(new ServletHolder(new SignInServlet(accountService)), "/signin");

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setResourceBase("templates");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});


        // старт сервера и указание карты доступныъ для него url-запросов
        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
        Logger.getGlobal().info("Server started");
        //System.out.println("Server started");
        server.join();
    }
}
