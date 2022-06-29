package sdfPractice.app;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class HttpServer {
    private int port;
    private LinkedList<String> paths;

    public HttpServer(String port, LinkedList<String> docPath) {
        this.port = Integer.parseInt(port);
        this.paths = docPath;
    }
    
    public void start() throws IOException {
        ServerSocket server = new ServerSocket(port);

        while(true) {
            Socket sock = server.accept();
            File directory;
            for(String s : paths) {
                directory = new File(s);
                for(File webpage : directory.listFiles()) {
                    if(webpage.getName().equals("index.html")) {

                    }
                }
            }
        }

    }
}
