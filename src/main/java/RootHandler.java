import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.util.Date;

public class RootHandler implements HttpHandler {
    private File path;
    public RootHandler(File path) {
        this.path = path;
    }

    public void handle(HttpExchange t) throws IOException {
        System.out.println("Root handler");

    }
}


