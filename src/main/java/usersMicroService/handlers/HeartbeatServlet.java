package usersMicroService.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import usersMicroService.models.Answer;
import usersMicroService.processes.ResponseProcessing;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HeartbeatServlet extends HttpServlet implements ResponseProcessing {

    private static Logger logHeartbeatServlet = LoggerFactory.getLogger(HeartbeatServlet.class.getSimpleName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            setRespParameters(resp, HttpServletResponse.SC_OK, new Answer("OK", null, null));
        } catch (Exception e) {
            logHeartbeatServlet.error("Error. " + e.getMessage());
        }
    }
}
