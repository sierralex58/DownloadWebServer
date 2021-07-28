import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
    protected HttpServer server;

    public Server() throws IOException {
        server = HttpServer.create(new InetSocketAddress(8001), 0);
        server.setExecutor(null); // creates a default executor
    }

    public void serve() {
        System.out.println("Serving " + DownloadHTTPServer.path.getAbsolutePath() + " on port 8001");
        server.start();
    }

    public HttpServer getServer() {
        return server;
    }

}
