package udp.regular;

import java.io.Console;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {

    private DatagramSocket socket;
    private InetAddress address;

    private byte[] buf;

    //Initialisera socket och adress i konstruktorn.
    public Client(){
        try{
            socket = new DatagramSocket();
            address = InetAddress.getByName("localhost");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void startConnection(){
        try{
            //Gör om texten så att den kan skickas i ett DatagramPacket - Skicka det sedan.
            buf = "This will be sent to the server".getBytes();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 8888);
            socket.send(packet);

            //Gör ett nytt paket för det väntade svaret ifrån servern.
            packet = new DatagramPacket(buf, buf.length);
            //Ta emot paketet, gör om det till en sträng och skriv ut det i konsollen.
            socket.receive(packet);
            String received = new String(packet.getData(), 0, packet.getLength());
            System.out.println("The received response was: \n" + received);

        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    //Skapa ny instans av Client klassen för att slippa static main strul.
    public static void main(String[] args) {
        Client c = new Client();
        c.startConnection();
    }
}
