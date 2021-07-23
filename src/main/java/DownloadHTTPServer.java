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

    static private File file;


    public static void main(String[] args) throws Exception {
        if(args.length == 1) {
            file = new File(args[0]);

            HttpServer server = HttpServer.create(new InetSocketAddress(8001), 0);
            server.createContext("/info", new InfoHandler());
            server.createContext("/" + file.getName(), new GetHandler());
            server.setExecutor(null); // creates a default executor
            System.out.println("Serving " + file.getAbsolutePath() + " on port 8001");
            server.start();
        } else {
            System.err.println("usage: DownloadHTTPServer <filepath>");
        }
    }

    static class InfoHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            Date date = new Date();
            System.out.println(date.toString() + " " + t.getRemoteAddress().getHostName() + " info " + file.getAbsolutePath());
            String response = "Use /" + file.getName() + " to download the file";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class GetHandler implements HttpHandler {
        public void handle(HttpExchange t)  {

            // add the required response header for a PDF file
            Headers h = t.getResponseHeaders();
            h.add("Content-Type", "application/octet-stream");

            Date date = new Date();


            try {
                // a PDF (you provide your own!)
                System.out.println(date.toString() + " " + t.getRemoteAddress().getHostName() + " get " + file.getAbsolutePath());
                byte[] bytearray = new byte[(int) file.length()];
                FileInputStream fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);
                bis.read(bytearray, 0, bytearray.length);

                // ok, we are ready to send the response.
                t.sendResponseHeaders(200, file.length());
                OutputStream os = t.getResponseBody();
                os.write(bytearray, 0, bytearray.length);
                os.close();
            } catch(IOException ex) {
                System.out.println(ex);
            }

        }
    }
}