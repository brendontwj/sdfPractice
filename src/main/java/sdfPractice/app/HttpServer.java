package sdfPractice.app;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
            System.out.println("Waiting for connection");
            Socket sock = server.accept();
            System.out.println("Connected");
            File directory;
            for(String s : paths) {
                directory = new File(s);
                if(!directory.exists()) {
                    System.out.println("Directory does not exist");
                    System.exit(1);
                } else if (!directory.isDirectory()) {
                    System.out.println("Path is not a directory");
                    System.exit(1);
                } else if (!directory.canRead()) {
                    System.out.println("Path is not readable");
                    System.exit(1);
                } else {
                    ExecutorService executor =  Executors.newFixedThreadPool(3);
                    HttpClientConnection thread = new HttpClientConnection(sock, paths);
                    executor.submit(thread);
                    System.out.println("Task submitted to threadpool.");
                }
            }
        }
    }
}
