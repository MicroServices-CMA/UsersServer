package UsersMicroService.Models;


import UsersMicroService.classes.Client;

import java.util.ArrayList;
import java.util.List;

public class Answer
{
    private String status;
    private Client client;

    public Answer(String status, Client people) {
        this.status = status;
        this.client = people;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Client getClients() {
        return client;
    }

    public void setClients(Client client) {
        this.client = client;
    }
}
