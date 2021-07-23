import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Date;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.Headers;


public class DownloadHTTPServer {



    public static void main(String[] args) throws Exception {
        Context ctx;

        if(args.length == 1) {
            ctx = new Context(args[0]);

            ctx.serve();
        } else {
            System.err.println("usage: DownloadHTTPServer <filepath>");
        }
    }
}