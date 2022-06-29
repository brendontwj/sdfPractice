package sdfPractice.app;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class HttpClientConnection implements Runnable {
    private Socket sock;
    private String path;

    public HttpClientConnection(Socket sock, String path) {
        this.sock = sock;
        this.path = path;
    }

    @Override
    public void run() {
        try {
            OutputStream os = sock.getOutputStream();
            InputStream is = sock.getInputStream(); 
            InputStreamReader isr = new InputStreamReader(dis);

            String input;
            while(input = dis.read)
        } catch (IOException e) {
            e.printStackTrace();
        }
               
    }

    
}
