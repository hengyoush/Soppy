package io.soppy.test;

import io.soppy.Bootstrap;
import io.soppy.router.RouterManager;
import io.soppy.test.MethodTest.GetHandler;
import io.soppy.test.MethodTest.PostHandler;
import org.junit.Test;

public class ServerStart {
    @Test
    public void initServer1() {
        int port = 20789;
        RouterManager.getInstance().register("/test", new GetHandler());
        RouterManager.getInstance().register("/testpost", new PostHandler());
        new Bootstrap(RouterManager.getInstance()).start(port);
    }

    @Test
    public void initServer2() {
        int port = 20789;
        RouterManager.getInstance().register("/test-json-post", new PostJsonHandler());
        new Bootstrap(RouterManager.getInstance()).start(port);
    }
}
