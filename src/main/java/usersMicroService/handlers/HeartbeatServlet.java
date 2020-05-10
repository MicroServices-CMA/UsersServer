package usersMicroService.handlers;

import usersMicroService.models.Answer;
import usersMicroService.utils.Common;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import usersMicroService.models.Answer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HeartbeatServlet extends HttpServlet
{
    private static Logger logHeartbeatServlet = LoggerFactory.getLogger(HeartbeatServlet.class.getSimpleName());
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_OK);
            Answer answer = new Answer("OK", null, null);
            resp.getWriter().println(Common.getPrettyGson().toJson(answer));
        }catch (Exception e)
        {
            logHeartbeatServlet.error("Error. " + e.getMessage());
        }
    }
}
