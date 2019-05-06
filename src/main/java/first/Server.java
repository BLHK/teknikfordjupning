package first;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

/**
 * Detta var mitt första försök på att skriva ihop en TCP server.
 * Denna server kan bara ta emot en klient.
 */
public class Server extends Thread{

    private ServerSocket serverSocket;

    //Socket initialiseras i konstruktorn.
    public Server() throws IOException {
        serverSocket = new ServerSocket(8888);
    }

    public void run() {
        while (true){
            try{
                System.out.println("Waiting for client on port: " + serverSocket.getLocalPort());
                //Acceptera klient när serverSocket får en request för kontakt.
                Socket socket = serverSocket.accept();
                //Sätt upp streams för kommunikation.
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

                //Skriv ut datum till klienten tills någon av parterna stänger av sin applikation.
                while(true){
                    dataOutputStream.writeUTF(LocalDateTime.now().toString());
                    sleep(1000);
                }

            }catch (IOException e){
                System.out.println(e);
            }catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }

    //Skapa tråd för att undvika static strul med main.
    public static void main(String[] args) {
        try {
            Thread t = new Server();
            t.start();
        }catch (IOException e){
            System.out.println(e);
        }
    }

}
