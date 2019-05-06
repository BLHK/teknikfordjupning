package udp.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.time.LocalDateTime;

public class Server extends Thread{

    private byte[] buf;
    private MulticastSocket socket;
    InetAddress group;

    /**
     * Skapar upp en server för att sätta upp multicastsocket för klienter att gå med i.
     * Skickar ut vad klockan är varje sekund till de klienter som lyssnar på multicastSocketen.
     */
    public Server(){
        try {
            buf = new byte[256];
            socket = new MulticastSocket(8888);
            group = InetAddress.getByName("230.0.0.0");

        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    public void run(){
        try{
            group = InetAddress.getByName("230.0.0.0");
            while(true){
                sleep(1000);
                buf = LocalDateTime.now().toString().getBytes();

                DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 8888);
                socket.send(packet);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Thread t = new Server();
        t.start();
    }
}
