package io.soppy.test;

import io.netty.handler.codec.http.FullHttpRequest;
import io.soppy.handler.Method;
import io.soppy.handler.RequestHandler;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * 测试Method注解
 *
 * @see Method
 */
public class MethodTest {

    @Test
    public void testMethodGet() throws IOException, InterruptedException {
        var client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
        var request = HttpRequest.newBuilder(URI.create("http://localhost:20789/test"))
                .GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.printf("返回状态：%d，\n返回体内容： %s\n", response.statusCode(), response.body());
        Assert.assertEquals(200, response.statusCode());
    }

    @Test
    public void testMethodPost() throws IOException, InterruptedException {
        var client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
        var request = HttpRequest.newBuilder(URI.create("http://localhost:20789/testpost"))
                .POST(HttpRequest.BodyPublishers.ofString("1")).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.printf("返回状态：%d，\n返回体内容： %s\n", response.statusCode(), response.body());
        Assert.assertEquals(200, response.statusCode());
    }

    @Method(Method.Type.GET)
    static class GetHandler implements RequestHandler {

        @Override
        public Object handle(FullHttpRequest fullHttpRequest) {
            return "hello get";
        }
    }
    @Method(Method.Type.POST)
    static class PostHandler implements RequestHandler {

        @Override
        public Object handle(FullHttpRequest fullHttpRequest) {
            return "hello post";
        }
    }
}
