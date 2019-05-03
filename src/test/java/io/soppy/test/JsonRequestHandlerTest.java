package io.soppy.test;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class JsonRequestHandlerTest {

    @Test
    public void test1() throws IOException, InterruptedException {
        var client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
        var request = HttpRequest.newBuilder(URI.create("http://localhost:20789/test-json-post"))
                .POST(HttpRequest.BodyPublishers.ofString("1")).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.printf("返回状态：%d，\n返回体内容： %s\n", response.statusCode(), response.body());
        Assert.assertEquals(200, response.statusCode());
    }




}
