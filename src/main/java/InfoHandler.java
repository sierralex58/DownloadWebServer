import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

public class InfoHandler implements HttpHandler {
    private File path;
    public InfoHandler(File path) {
        this.path = path;
    }

    public void handle(HttpExchange t) throws IOException {
        StringBuilder response = new StringBuilder();
        Date date = new Date();
        File[] files = path.listFiles();

        System.out.println(date.toString() + " " + t.getRemoteAddress().getHostName() + " info " + path.getAbsolutePath());

        response.append("<h1>IT-LAB Download Server</h1><br<br>");

        if(path.isDirectory()) {
            for (File file : files) {
                if (file.isFile()) {
                    response.append("<a href=http://" + t.getLocalAddress().getHostName() + ":8001/" + file.getName() + ">" + file.getName() + "</a><br>");

                }
            }
        } else {
            response.append("<a href=http://" + t.getLocalAddress().getHostName() + ":8001/" + path.getName() + ">" + path.getName() + "</a><br>");
        }

        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.toString().getBytes());
        os.close();
    }
}