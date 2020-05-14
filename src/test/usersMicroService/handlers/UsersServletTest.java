package usersMicroService.handlers;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import usersMicroService.Main;
import usersMicroService.processors.FileProcessing;

import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;

public class UsersServletTest {

    @Before
    public void setUp()
    {
        try {
            FileProcessing.loadFromJsonFile();
        } catch (Exception e) {
            org.junit.Assert.fail();
        }
        Main.runServer(7000, "/");
    }

    @Test
    public void doGetFound() throws Exception
    {
        String url = "http://localhost:7000/userData?surname=Sergei&name=Lavrov&passportSerial=094528&email=Lavrov@mail.ru&country=Russia";
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);
        org.junit.Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatusLine().getStatusCode());
    }

    @Test
    public void doGetNoFound() throws Exception
    {
        String url = "http://localhost:7000/userData?surname=Sergei&name=Lavrovvv&passportSerial=094528&email=Lavrov@mail.ru&country=Russia";
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);
        org.junit.Assert.assertEquals(HttpServletResponse.SC_NOT_FOUND, response.getStatusLine().getStatusCode());
    }

    @Test
    public void doGetBadRequest() throws Exception
    {
        String url = "http://localhost:7000/userData?sssurname=Sergei&name=Lavrov&passportSerial=094dfgd77528&email=Lavrov@mail.ru&country=Russia";
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);
        org.junit.Assert.assertEquals(HttpServletResponse.SC_BAD_REQUEST, response.getStatusLine().getStatusCode());
    }

    @Test
    public void doGetPageError() throws Exception
    {
        String url = "http://localhost:7000/userddData?sssurname=Sergei&name=Lavrov&passportSerial=094dfgd77528&email=Lavrov@mail.ru&country=Russia";
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);
        org.junit.Assert.assertEquals(HttpServletResponse.SC_NOT_FOUND, response.getStatusLine().getStatusCode());
    }

    @After
    public void tearDown()
    {
        Main.stopServer();
    }
}