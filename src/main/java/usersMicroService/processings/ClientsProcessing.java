package usersMicroService.processings;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import usersMicroService.structures.Client;
import usersMicroService.models.Answer;
import usersMicroService.models.Clients;
import usersMicroService.utils.Common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static usersMicroService.handlers.UsersServlet.usersLog;


/**
 * This interface contains different predefined methods used to perform <code>Client</code> adding and searching.
 *
 * @author Hank
 * @version 2.1
 */
public interface ClientsProcessing extends ResponseProcessing {

    /**
     * initiate the client search and proceed into writing the data into a JSON object and write it in <code>response</code>.
     *
     * @param request the request to be analysed;
     * @param response the response object that'll be sent back;
     * @return void
     */
    default void searchClient(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (request != null) {
            try {
                String surname = request.getParameter("surname");
                String name = request.getParameter("name");
                String passportSerial = request.getParameter("passportSerial");
                String email = request.getParameter("email");
                String country = request.getParameter("country");
                if (name != null && surname != null && passportSerial != null && email != null && country != null) {
                    search(response, surname, name, passportSerial, email, country);
                } else {
                    setRespParam(response, HttpServletResponse.SC_BAD_REQUEST, new Answer("BAD_CLIENT_REQUEST",
                            "At least one search parameter missing. Please check the parameters", null));
                    usersLog.error("Error: No name, surname, passportSerial or email provided");
                }
            } catch (Exception e) {
                setRespParam(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, new Answer("INTERNAL_CLIENTS_SERVER_ERROR",
                        "An internal Error occurred within UsersMicroService", null));
                usersLog.error("Error: " + e.getMessage());
            }
        } else {
            usersLog.error("Empty request.");
            setRespParam(response, HttpServletResponse.SC_BAD_REQUEST, new Answer("EMPTY_CLIENT_REQUEST",
                    "The request provided was empty", null));
        }
    }

    /**
     * proceed into searching a client by surname
     *
     * @param surname The surname of the Client that's being searched.
     * @return List<Client>
     */
    default List<Client> searchBySurname(String surname) {

        List<Client> clients = new ArrayList<>();
        for (Map.Entry<String, Client> entry : Clients.getPeopleTable().entrySet()) {
            Client V = entry.getValue();
            if (surname.equals(V.getPassport().getSurname())) {
                clients.add(V);
            }
        }
        return clients;
    }


    /**
     * Evolve into the real process of searching for a client using many filters;
     *
     * @param response a link to the response object;
     * @param surname the Client surname;
     * @param name ths client's name;
     * @param passportSerial the client's passport serial number;
     * @param email the client email;
     * @param country the client country
     * @return void
     */
    default void search(HttpServletResponse response, String surname, String name, String passportSerial, String email,
                        String country) throws IOException {
        List<Client> clients = searchBySurname(surname);
        clients.removeIf(K -> !K.getPassport().getName().equals(name));
        clients.removeIf(K -> !K.getPassport().getSerialNumber().equals(passportSerial));
        clients.removeIf(K -> !K.getEmail().equals(email));
        clients.removeIf(K -> !K.getPassport().getCountry().getName().equals(country));
        if (clients.size() != 0) {
            setRespParam(response, HttpServletResponse.SC_OK, new Answer("OK", "The request was successful",
                    clients.get(0)));
            usersLog.error("Client found and delivered!");
        } else {
            setRespParam(response, HttpServletResponse.SC_NOT_FOUND, new Answer("CLIENT_NOT_FOUND",
                    "Client not found", null));
            usersLog.error("Client not found. Provided parameters:\n" +
                    "surname: " + surname + "\n" +
                    "name: " + name + "\n" +
                    "passportSerial: " + passportSerial + "\n" +
                    "email: " + email + "\n" +
                    "country: " + country);
        }
    }

    /**
     * Add a client into the database.
     *
     * @param req the HttpServletRequest object;
     * @param resp the HttpServletResponse object;
     * @return void
     */
    default void addClient(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req != null) {
            try {
                String reqStr = IOUtils.toString(req.getInputStream(), StandardCharsets.UTF_8);
                if (StringUtils.isBlank(reqStr)) {
                    setRespParam(resp, HttpServletResponse.SC_BAD_REQUEST, new Answer("BAD_CLIENT_REQUEST",
                            "Empty request body", null));
                    System.out.println("Empty request body!");
                    usersLog.error("Empty request body!");
                }
                else {
                    Client c = Common.getPrettyGson().fromJson(reqStr, Client.class);
                    Clients.getPeopleTable().put(c.getClientId(), c);
                    setRespParam(resp, HttpServletResponse.SC_OK, new Answer("OK",
                            "The request was successful", Clients.getPeopleTable().get(c.getClientId())));
                    System.out.println("Client added!");
                }
            } catch (Exception e) {
                setRespParam(resp, HttpServletResponse.SC_BAD_REQUEST, new Answer("INTERNAL_CLIENTS_SERVER_ERROR",
                        "An internal Error occurred on server UsersMicroService", null));
                usersLog.error("PROCESSING ERROR.");
            }
        } else {
            setRespParam(resp, HttpServletResponse.SC_BAD_REQUEST, new Answer("EMPTY_CLIENT_REQUEST",
                    "Empty request", null));
            usersLog.error("Empty request.");
        }
    }
}
