package usersMicroService;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import usersMicroService.handlers.HeartbeatServlet;
import usersMicroService.handlers.UsersServlet;
import usersMicroService.processings.FileProcessing;
import usersMicroService.utils.Common;
import usersMicroService.utils.PropertyManager;

import java.io.FileWriter;

public class Main {

    private static Logger log = LoggerFactory.getLogger(Main.class.getSimpleName());

    private static Server server;

    private static FileWriter file;

    public static void main(String[] args) throws Exception {
        PropertyManager.load();
        Common.configure();
        FileProcessing.loadFromJsonFile();
        runServer();
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FileProcessing.save2JsonFile();
                } catch (Exception e) {
                    log.error("Error: ", e);
                }
                stopServer();
            }
        }, "Stop Jetty Hook"));
    }

    public static void runServer(int port, String contextStr) {
        server = new Server(port);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath(contextStr);
        server.setHandler(context);

        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);

        handler.addServletWithMapping(UsersServlet.class, "/userData");
        handler.addServletWithMapping(HeartbeatServlet.class, "/heart");
        try {
            server.start();
            log.info("Server has started at port: " + port);
            System.out.println("Server has started at port: " + port);
        } catch (Throwable t) {
            log.error("Error while starting server.", t);
        }
    }

    private static void runServer() {
        int port = PropertyManager.getPropertyAsInteger("server.port", 7000);
        String contextStr = PropertyManager.getPropertyAsString("server.context", "/");

        runServer(port, contextStr);
    }

    public static void stopServer() {
        try {
            if (server.isRunning()) {
                server.stop();
            }
        } catch (Exception e) {
            log.error("Error while stopping server", e);
        }
    }

    public static Logger getLog() {
        return log;
    }

    public static void setLog(Logger log) {
        Main.log = log;
    }
}

