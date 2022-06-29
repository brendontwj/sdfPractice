package sdfPractice.app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class HttpClientConnection implements Runnable {
    private Socket sock;
    private LinkedList<String> paths;

    public HttpClientConnection(Socket sock, LinkedList<String> paths) {
        this.sock = sock;
        this.paths = paths;
    }

    @Override
    public void run() {
        try {
            // initializing IO
            OutputStream os = sock.getOutputStream();
            InputStream is = sock.getInputStream(); 
            InputStreamReader isr = new InputStreamReader(is);
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedReader br = new BufferedReader(isr);
            BufferedWriter bw = new BufferedWriter(osw);

            // reading http request
            String input;
            input = br.readLine();
            System.out.println(input);

            String[] splitInput = input.split(" ");
            if(splitInput[1].equals("/")) {
                splitInput[1] = "/index.html";
            }
            // parsing client input
            if(!splitInput[0].equals("GET")) {
                System.out.println("Not get method");
                bw.write("HTTP/1.1/ 405 Method Not Allowed\r\n");
                bw.write("\r\n");
                bw.write(splitInput[0] + " not supported\r\n");
                bw.flush();
            } else {
                // look thru folders sent in paths for requested content
                boolean found = false;
                for(String s : paths) {
                    File directory = new File(s);
                    for(File webpage : directory.listFiles()) {
                        if(("/"+webpage.getName()).equals(splitInput[1]) && splitInput[1].contains(".png") ) {
                            found = true;
                            System.out.println("PNG request");
                            InputStream fis = new FileInputStream(webpage);
                            InputStreamReader fisr = new InputStreamReader(fis);
                            BufferedReader fbr = new BufferedReader(fisr);
                            String lines = fbr.lines().collect(Collectors.joining("\n"));
                            bw.write("HTTP/1.1 200 OK\r\n");
                            bw.write("Content-Type: image/png\r\n");
                            bw.write("\r\n");
                            bw.write(lines);
                            bw.flush();
                            fbr.close();
                        } else if (("/"+webpage.getName()).equals(splitInput[1])) {
                            found = true;
                            System.out.println("Non-PNG request");
                            InputStream fis = new FileInputStream(webpage);
                            InputStreamReader fisr = new InputStreamReader(fis);
                            BufferedReader fbr = new BufferedReader(fisr);
                            String lines = fbr.lines().collect(Collectors.joining("\n"));
                            bw.write("HTTP/1.1 200 OK\r\n");
                            bw.write("\r\n");
                            bw.write(lines);
                            bw.flush();
                            fbr.close();
                        }
                    }
                    if(!found) {
                        System.out.println("Resource does not exist");
                        bw.write("HTTP/1.1 404 Not Found\r\n");
                        bw.write("\r\n");
                        bw.write(splitInput[1] + " not found\r\n");
                        bw.flush();
                    }
                }
                sock.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
