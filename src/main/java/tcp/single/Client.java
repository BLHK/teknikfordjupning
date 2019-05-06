package tcp.single;

import java.io.*;
import java.net.Socket;

public class Client {

    private Socket socket;
    private int port;

    /**
     * Skapar en stream för att kunna kommunicera med servern.
     * Endast en klient kan koppla upp sig åt gången.
     * Försöker två klienter kommunicera med servern samtidigt får den sista vänta tills
     * den andra klienten stängt av sin stream och socket.
     */
    public Client(){
        port = 8888;
    }

    public void run(){
        int port = 8888;

        try {
            System.out.println("Trying to connect to server on port " + port);
            socket = new Socket("localhost", port);

            System.out.println("Just connected to server " + socket.getRemoteSocketAddress());
            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            dataOutputStream.writeUTF("Hello from + " + socket.getLocalSocketAddress());
            InputStream inputStream = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);

            while(!dataInputStream.readUTF().equals("done")){
                System.out.println("Server says: " + dataInputStream.readUTF());
            }
            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
        }catch (IOException e){
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
    Client c = new Client();
    c.run();
    }
}
