import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Context {
    protected File path;
    protected File[] files;
    protected HttpServer server;

    public Context(String spath) throws IOException {
        this.path = new File(spath);

        server = HttpServer.create(new InetSocketAddress(8001), 0);

        if(path.isDirectory()) {
            files = path.listFiles();

            server.createContext("/", new InfoHandler(path));
            server.createContext("/info", new InfoHandler(path));
            for (File file : files) {
                server.createContext("/" + file.getName(), new GetHandler(path));
            }
        } else {
            server.createContext("/", new InfoHandler(path));
            server.createContext("/info", new InfoHandler(path));
            server.createContext("/" + path.getName(), new GetHandler(path));
        }
        server.setExecutor(null); // creates a default executor

    }

    public void serve() {
        System.out.println("Serving " + path.getAbsolutePath() + " on port 8001");
        server.start();
    }
}
