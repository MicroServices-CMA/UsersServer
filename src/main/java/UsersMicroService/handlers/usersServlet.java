package UsersMicroService.handlers;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.io.IOUtils;
import UsersMicroService.Main;
import UsersMicroService.Models.Answer;
import UsersMicroService.Models.Clients;
import UsersMicroService.classes.Client;
import UsersMicroService.utils.Common;
; // TODO: create log file for GET / POST requests and delete this import

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


/**
 * @See https://www.tutorialspoint.com/servlets/servlets-form-data.htm for examples
 */
public class usersServlet extends HttpServlet {
    private static Logger usersLog = LoggerFactory.getLogger(usersServlet.class.getSimpleName());

    /**
     * The POST method is used when you create an HTML form, and request method=POST as part of the tag.
     * The POST method allows the client to send form data to the server in the request body section of the request.
     * This packages the information in exactly the same way as GET method, but instead of sending it as a text string
     * after a ? (question mark) in the URL it sends it as a separate message.
     * This message comes to the backend program in the form of the standard input.
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req != null) {
            try {
                String reqStr = IOUtils.toString(req.getInputStream(), StandardCharsets.UTF_8);
                if (StringUtils.isBlank(reqStr)) {
                    resp.setContentType("application/json");
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().println(Common.getPrettyGson().toJson(new Answer("BAD", null)));
                    return;
                }
                Client c = Common.getPrettyGson().fromJson(reqStr, Client.class);
                Clients.getPeopleTable().put(c.getClientId(), c);
                resp.setContentType("application/json");
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                resp.getWriter().println(Common.getPrettyGson().toJson(
                        new Answer("OK", Clients.getPeopleTable().get(c.getClientId()))));
            } catch (Exception e) {
                Main.getLog().error("PROCESSING ERROR.");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().println("");
            }
        } else {
            Main.getLog().error("Empty request.");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("EMPTY_REQUEST");
        }
    }


    /**
     * The GET method sends the encoded user information appended to the page request.
     * The page and the encoded information are separated by the ? (question mark)
     * The GET method is most commonly (and is the default method) used by browsers to retrieve information from servers.
     * Never use the GET method if you have password or other sensitive information to pass to the server.
     * The GET method has size limitation: only 1024 characters can be used in a request string.
     * Servlet handles this type of requests using doGet() method.
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id;
        id = request.getParameter("clientId");
        if(id == null){
            Main.getLog().error("Error on clientId, value not provided.");
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println(Common.getPrettyGson().toJson(new Answer("ERROR", null)));
            return;
        }
        try {
            if (Clients.getPeopleTable().containsKey(id)) {
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_FOUND);
                response.getWriter().println(Common.getPrettyGson().toJson(new Answer("OK",
                        Clients.getPeopleTable().get(id))));
                Main.getLog().error("Object found. Provided id: " + id);
            } else {
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().println(Common.getPrettyGson().toJson(new Answer("NOT_FOUND", null)));
                Main.getLog().error("Object not found. Provided id: " + id);
            }
        }catch (Exception e){
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println(Common.getPrettyGson().toJson(new Answer("ERROR", null)));
            Main.getLog().error("Error. " + e.getMessage());
        }
    }

    public static Logger getUsersLog() {
        return usersLog;
    }

    public static void setUsersLog(Logger usersLog) {
        usersServlet.usersLog = usersLog;
    }

    // TODO: 07.05.2020 Also consider overriding methods doHead, doPut, doDelete, doTrace, doOptions
    // TODO: 07.05.2020 read чистый код
}
