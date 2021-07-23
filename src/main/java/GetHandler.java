import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.openjsse.sun.security.rsa.RSAUtil;

import java.io.*;
import java.util.Date;

public class GetHandler implements HttpHandler {
    private File path;
    public GetHandler(File path) {
        this.path = path;
    }

    public void handle(HttpExchange t) {

        // add the required response header for a PDF file
        Headers h = t.getResponseHeaders();
        h.add("Content-Type", "application/octet-stream");

        Date date = new Date();
        File file;

        if (path.isDirectory()) {
            file = new File(path.getAbsolutePath() + t.getHttpContext().getPath());
        } else {
            file = path;
        }

        try {
            // a PDF (you provide your own!)
            System.out.println(date.toString() + " " + t.getRemoteAddress().getHostName() + " get " + t.getHttpContext().getPath());
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
