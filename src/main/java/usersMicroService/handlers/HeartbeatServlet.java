package usersMicroService.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import usersMicroService.models.Answer;
import usersMicroService.processors.ResponseProcessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * This servlet is used to receive requests from external sources trying to identify the server state.
 *
 * @author Ханк
 * @version 1.0
 */
public class HeartbeatServlet extends HttpServlet implements ResponseProcessor {

    private static Logger logHeartbeatServlet = LoggerFactory.getLogger(HeartbeatServlet.class.getSimpleName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            setRespParam(resp, HttpServletResponse.SC_OK, new Answer("OK", null, null));
        } catch (Exception e) {
            logHeartbeatServlet.error("Error. " + e.getMessage());
        }
    }
}
