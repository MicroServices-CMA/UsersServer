package usersMicroService.handlers;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import usersMicroService.processes.ClientsProcessing;
import usersMicroService.processes.FileProcessing;
import usersMicroService.processes.ResponseProcessing;

public class UsersServlet extends HttpServlet implements ResponseProcessing, ClientsProcessing {
    private static Logger usersLog = LoggerFactory.getLogger(UsersServlet.class.getSimpleName());

    public UsersServlet() {
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            this.addClient(req, resp);
        } catch (IOException var5) {
            var5.printStackTrace();
        }

        try {
            FileProcessing.save2JsonFile();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            this.searchClient(request, response);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
