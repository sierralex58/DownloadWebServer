import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Context {

    public Context(Server server)  {
        File[] files = DownloadHTTPServer.path.listFiles();

        if(DownloadHTTPServer.path.isDirectory()) {

            server.getServer().createContext("/", new InfoHandler(DownloadHTTPServer.path));
            server.getServer().createContext("/info", new InfoHandler(DownloadHTTPServer.path));
            for (File file : files) {
                server.getServer().createContext("/" + file.getName(), new GetHandler(DownloadHTTPServer.path));
            }
        } else {
            server.getServer().createContext("/", new InfoHandler(DownloadHTTPServer.path));
            server.getServer().createContext("/info", new InfoHandler(DownloadHTTPServer.path));
            server.getServer().createContext("/" + DownloadHTTPServer.path.getName(), new GetHandler(DownloadHTTPServer.path));
        }

    }


}
