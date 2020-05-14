package usersMicroService.models;

import org.junit.Test;
import usersMicroService.classes.Client;
import usersMicroService.classes.Country;
import usersMicroService.classes.Passport;
import usersMicroService.classes.PhysicalAddress;
import usersMicroService.enumerators.GenderEnum;
import usersMicroService.enumerators.PassportCategoryEnum;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class AnswerTest {

    @Test
    public void setStatus() throws ParseException {
        Country country = new Country("RU");
        PhysicalAddress address = new PhysicalAddress("Linin street", "Bryansk", "Bryansk obl", "12-1");
        Date birthDate = new SimpleDateFormat( "dd.MM.yyyy" ).parse( "1.12.1990" );
        Date date1 = new SimpleDateFormat( "dd.MM.yyyy" ).parse( "12.12.2020" );
        Date date2 = new SimpleDateFormat( "dd.MM.yyyy" ).parse( "12.12.2029" );
        Passport passport = new Passport("Ivanov", "Ivan", birthDate,  GenderEnum.MALE, country, "111111", PassportCategoryEnum.OFFICIAL, date1, date2, null, null);
        Client client = new Client(passport, "666-666", "ivan@mail.ru", address);
        Answer answer = new Answer("OK", "The request was succesful", client);
        answer.setStatus("MOLOKO");
        org.junit.Assert.assertEquals("MOLOKO", answer.getStatus());
    }

    @Test
    public void setDetails() throws ParseException {
        Country country = new Country("RU");
        PhysicalAddress address = new PhysicalAddress("Linin street", "Bryansk", "Bryansk obl", "12-1");
        Date birthDate = new SimpleDateFormat( "dd.MM.yyyy" ).parse( "1.12.1990" );
        Date date1 = new SimpleDateFormat( "dd.MM.yyyy" ).parse( "12.12.2020" );
        Date date2 = new SimpleDateFormat( "dd.MM.yyyy" ).parse( "12.12.2029" );
        Passport passport = new Passport("Ivanov", "Ivan", birthDate,  GenderEnum.MALE, country, "111111", PassportCategoryEnum.OFFICIAL, date1, date2, null, null);
        Client client = new Client(passport, "666-666", "ivan@mail.ru", address);
        Answer answer = new Answer("OK", "The request was succesful", client);
        answer.setDetails("The request was not succesful");
        org.junit.Assert.assertEquals("The request was not succesful", answer.getDetails());
    }

    @Test
    public void setClient() throws ParseException {
        Country country = new Country("RU");
        PhysicalAddress address = new PhysicalAddress("Linin street", "Bryansk", "Bryansk obl", "12-1");
        Date birthDate = new SimpleDateFormat( "dd.MM.yyyy" ).parse( "1.12.1990" );
        Date date1 = new SimpleDateFormat( "dd.MM.yyyy" ).parse( "12.12.2020" );
        Date date2 = new SimpleDateFormat( "dd.MM.yyyy" ).parse( "12.12.2029" );
        Passport passport = new Passport("Ivanov", "Ivan", birthDate,  GenderEnum.MALE, country, "111111", PassportCategoryEnum.OFFICIAL, date1, date2, null, null);
        Client client = new Client(passport, "666-666", "ivan@mail.ru", address);
        Answer answer = new Answer("OK", "The request was succesful", null);
        answer.setClient(client);
        org.junit.Assert.assertEquals(client, answer.getClient());
    }


}