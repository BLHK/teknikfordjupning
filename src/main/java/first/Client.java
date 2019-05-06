package first;

import java.io.*;
import java.net.Socket;

/**
 * Detta var mitt första försök på att skriva ihop en TCP klient.
*/
public class Client {
    public static void main(String[] args) {
        int port = 8888;

        try {
            System.out.println("Trying to connect to server on port " + port);
            //Sätter upp en socket för att kunna kommunicera med.
            Socket socket = new Socket("localhost", port);

            System.out.println("Just connected to server " + socket.getRemoteSocketAddress());

            //Sätter upp en stream för utdata.
            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            //Hämtar socket adressen som klienten har och skickar den till servern.
            dataOutputStream.writeUTF("Hello from + " + socket.getLocalSocketAddress());
            InputStream inputStream = socket.getInputStream();
            //Väntar på svar ifrån servern (används ej).
            DataInputStream dataInputStream = new DataInputStream(inputStream);

            //Ifall servern skickar ut "done" så stängs klientens socket och streams.
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

}
