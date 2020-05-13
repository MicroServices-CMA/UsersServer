package usersMicroService.handlers;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import usersMicroService.Main;

import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;

public class HeartbeatServletTest {

    @Before
    public void setUp()
    {
        Main.runServer(7000, "/");
    }

    @Test
    public void doGet() throws Exception
    {
        String url = "http://localhost:7000/heart";
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);
        org.junit.Assert.assertEquals(HttpServletResponse.SC_OK, response.getStatusLine().getStatusCode());
    }

    @After
    public void tearDown()
    {
        Main.stopServer();
    }

}