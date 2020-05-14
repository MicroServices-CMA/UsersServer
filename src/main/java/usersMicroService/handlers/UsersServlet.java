package usersMicroService.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import usersMicroService.processors.ClientsProcessor;
import usersMicroService.processors.FileProcessor;
import usersMicroService.processors.ResponseProcessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This is the servlet used to receive, transform and send back different requests and answers.
 *
 * @author Ханк
 * @version 1.4
 */
public class UsersServlet extends HttpServlet implements ResponseProcessor, ClientsProcessor {
    public static Logger usersLog = LoggerFactory.getLogger(UsersServlet.class.getSimpleName());

    public UsersServlet() {
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            addClient(req, resp);
            FileProcessor.save2JsonFile();
        } catch (Exception ex) {
            usersLog.error("Error in doPost method execution. Msg: ",ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            searchClient(request, response);
        } catch (IOException ex) {
            usersLog.error("Error in doGet method execution. Msg: ",ex);
        }

    }
}