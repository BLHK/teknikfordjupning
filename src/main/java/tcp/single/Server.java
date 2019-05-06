package tcp.single;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public class Server extends Thread {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private DataOutputStream out;
    private DataInputStream in;

    /**
     * Sätter upp en server för att utföra tcp kommunikation.
     * Denna server kan endast kommunicera med en klient åt gången.
     */
    public Server(){
        try {
            serverSocket = new ServerSocket(8888);
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    @Override
    public void run() {
        try{
        System.out.println("Waiting for client on port: " + serverSocket.getLocalPort());
        clientSocket = serverSocket.accept();
        in = new DataInputStream(clientSocket.getInputStream());
        out = new DataOutputStream(clientSocket.getOutputStream());

            while (true){
                for(int i = 0; i < 5; i++){
                    out.writeUTF(LocalDateTime.now().toString());
                    sleep(1000);
                }
                out.writeUTF("done");
                in.close();
                out.close();
                clientSocket.close();
                break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Thread t = new Server();
        t.run();
    }
}
