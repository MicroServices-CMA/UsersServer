package usersMicroService.models;

import usersMicroService.classes.Client;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Clients implements Serializable {
    private static Map<String, Client> peopleTable = new HashMap<>();

    private Clients(){

    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public static Map<String, Client> getPeopleTable() {
        return peopleTable;
    }

    public static void setPeopleTable(Map<String, Client> peopleTable) {
        Clients.peopleTable = peopleTable;
    }
}
