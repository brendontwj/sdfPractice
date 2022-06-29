package sdfPractice.app;

import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> docPath = new LinkedList<String>();
        docPath.add("./static");
        String portNum = "8080";

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

        HttpServer server = new HttpServer(portNum, docPath);
    }
}
