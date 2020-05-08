package UsersMicroService.Models;

import java.util.Date;

public class Request
{
    Integer clientId;
    String surname;
    String name;
    Date birthDate;


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}

