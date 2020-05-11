package usersMicroService.processes;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import usersMicroService.Main;
import usersMicroService.classes.Client;
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

public interface ClientsProcessing extends ResponseProcessing {

    default void searchClient(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            String surname = request.getParameter("surname");
            String name = request.getParameter("name");
            String passportSerial = request.getParameter("passportSerial");
            String email = request.getParameter("email");
            String country = request.getParameter("country");
            if (name != null && surname != null && passportSerial != null && email != null && country != null) {
                search(response, surname, name, passportSerial, email, country);
            } else {
                setRespParameters(response, HttpServletResponse.SC_BAD_REQUEST, new Answer("BAD_USER_REQUEST",
                        "At least one search parameter missing. Please check the parameters", null));
                Main.getLog().error("Error: No name, surname, passportSerial or email provided");
            }
        } catch (Exception e) {
            setRespParameters(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    new Answer("INTERNAL_USERS_SERVER_ERROR", "An internal Error occurred within " +
                            "UsersMicroService", null));
            Main.getLog().error("Error: " + e.getMessage());
        }
    }

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

    default void search(HttpServletResponse response, String surname, String name, String passportSerial, String email,
                        String country) throws IOException {
        List<Client> clients = searchBySurname(surname);
        clients.removeIf(K -> !K.getPassport().getName().equals(name));
        clients.removeIf(K -> !K.getPassport().getSerialNumber().equals(passportSerial));
        clients.removeIf(K -> !K.getEmail().equals(email));
        clients.removeIf(K -> !K.getPassport().getCountry().getName().equals(country));
        if (clients.size() != 0) {
            setRespParameters(response, HttpServletResponse.SC_OK, new Answer("OK",
                    "The request was successful", clients.get(0)));
            Main.getLog().error("Client found and delivered!");
        } else {
            setRespParameters(response, HttpServletResponse.SC_NOT_FOUND, new Answer("CLIENT_NOT_FOUND",
                    "Client not found", null));
            Main.getLog().error("Client not found. Provided parameters:\n" +
                    "surname: " + surname + "\n" +
                    "name: " + name + "\n" +
                    "passportSerial: " + passportSerial + "\n" +
                    "email: " + email + "\n" +
                    "country: " + country);
        }
    }

    default void addClient(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req != null) {
            try {
                String reqStr = IOUtils.toString(req.getInputStream(), StandardCharsets.UTF_8);
                if (StringUtils.isBlank(reqStr)) {
                    setRespParameters(resp, HttpServletResponse.SC_BAD_REQUEST, new Answer("BAD_USER_REQUEST",
                            "Empty request body", null));
                    System.out.println("Empty request body!");
                    Main.getLog().error("Empty request body!");
                }
                else {
                    Client c = Common.getPrettyGson().fromJson(reqStr, Client.class);
                    Clients.getPeopleTable().put(c.getClientId(), c);
                    setRespParameters(resp, HttpServletResponse.SC_OK, new Answer("OK",
                            "The request was successful", Clients.getPeopleTable().get(c.getClientId())));
                    System.out.println("Client added!");
                }
            } catch (Exception e) {
                setRespParameters(resp, HttpServletResponse.SC_BAD_REQUEST,
                        new Answer("INTERNAL_USERS_SERVER_ERROR", "An internal Error occurred on " +
                                "server UsersMicroService", null));
                Main.getLog().error("PROCESSING ERROR.");
            }
        } else {
            setRespParameters(resp, HttpServletResponse.SC_BAD_REQUEST, new Answer("EMPTY_USER_REQUEST",
                    "Empty request", null));
            Main.getLog().error("Empty request.");
        }
    }
}
