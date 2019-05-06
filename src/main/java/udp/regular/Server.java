package udp.regular;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server extends Thread {

    private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[256];

    //Konstruktor för att skapa upp socket, och bool för att kunna stänga av servern från klientsidan.
    public Server() throws Exception{
        socket = new DatagramSocket(8888);
        running = true;
    }

    public void run(){
        while (running) {
            try{
                //Skapa ett packet och ta emot data ifrån klienten.
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                //Hämta addresser för att veta vart respons paketet ska skickas.
                InetAddress address = packet.getAddress();
                int port = packet.getPort();

                //Sätt ihop ett paket med buf som innehåll och skicka till klienten.
                buf = "Message from the server.".getBytes();
                String received  = new String(packet.getData(), 0, packet.getLength());
                packet = new DatagramPacket(buf, buf.length, address, port);
                socket.send(packet);
                //Stäng av ifall klienten skickar "end" i sitt paket.
                if (received.equals("end")) {
                    running = false;
                    socket.close();
                    break;
                }
            }catch (IOException IOE){
                IOE.printStackTrace();
            }
        }
        socket.close();
    }

    //Skapa ny tråd för att slippa static main bök.
    public static void main(String[] args) {
        try {
            Thread t = new Server();
            t.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
