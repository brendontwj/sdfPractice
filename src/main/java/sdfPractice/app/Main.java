package sdfPractice.app;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // initialize default settings if no arguments passed
        List<String> docPath = new LinkedList<String>();
        docPath.add("./static");
        String portNum = "8080";

        // getting arguments if they are passed
        if(args.length > 0) {
            for(int i = 0; i < args.length; i++) {
                if(args[i].contains("--port")) {
                    portNum = args[i+1];
                } else if (args[i].contains("--docRoot")) {
                    String directories = args[i+1];
                    String[] splitPaths = directories.split(":");
                    for(String s: splitPaths) {
                        docPath.add(s);
                    }
                }
            }
        }

        // start server
        System.out.println("Starting server at port: " + portNum);
        HttpServer server = new HttpServer(portNum, (LinkedList<String>) docPath);
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
