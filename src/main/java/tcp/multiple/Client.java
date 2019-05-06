package tcp.multiple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    /**
     * Skapar upp en klient för kommunikation över tcp stream.
     */

    public void startConnection() throws IOException {
        clientSocket = new Socket("127.0.0.1", 8888);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        while(true){
            System.out.println(in.readLine());
        }
    }

    public static void main(String[] args) throws IOException{
        Client c = new Client();
        c.startConnection();
    }
}
